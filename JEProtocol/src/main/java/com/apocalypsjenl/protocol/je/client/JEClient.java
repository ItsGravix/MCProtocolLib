package com.apocalypsjenl.protocol.je.client;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEConnectException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;
import com.apocalypsjenl.protocol.je.exceptions.JEUnknownPacketException;
import com.apocalypsjenl.protocol.je.packet.IPacketListener;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;
import com.apocalypsjenl.protocol.je.packet.JEPacketRegister;
import com.apocalypsjenl.protocol.je.packet.JEProtocolState;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class JEClient {

    private JEProtocolState protocolState;
    private Socket socket;

    private PacketDecoder packetDecoder;
    private PacketEncoder packetEncoder;

    private Executor executor;

    private List<IPacketListener> packetListeners;

    public JEClient() {
        this.protocolState = JEProtocolState.HANDSHAKE;

        this.executor = Executors.newCachedThreadPool();

        this.packetListeners = new ArrayList<>();

        this.listen();
    }

    public void connect(String host) throws JEConnectException {
        this.connect(host, 25565);
    }

    public void connect(String host, int port) throws JEConnectException {
        this.socket = new Socket();

        try {
            this.socket.connect(InetSocketAddress.createUnresolved(host, port));
            this.packetDecoder = new PacketDecoder(this.socket.getInputStream());
            this.packetEncoder = new PacketEncoder(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new JEConnectException(e);
        }
    }

    public void addListener(IPacketListener listener) {
        this.packetListeners.add(listener);
    }

    public void sendPacket(JEPacketBase packet) {
        executor.execute(() -> {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PacketEncoder packetEncoder = new PacketEncoder(outputStream);
                packet.write(packetEncoder);

                this.packetEncoder.writeVarInt(packet.getPacketId());
                this.packetEncoder.write(outputStream.size());
                this.packetEncoder.write(outputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void listen() {
        boolean running = true;
        while (running) {
            try {
                if (this.packetDecoder.available() > 0) {
                    int packetId = this.packetDecoder.readVarInt();
                    int packetLength = this.packetDecoder.readVarInt();
                    byte[] packetBytes = new byte[packetLength];
                    this.packetDecoder.readFully(packetBytes);

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(packetBytes);
                    PacketDecoder packetDecoder = new PacketDecoder(inputStream);

                    JEPacketBase packetBase = JEPacketRegister.getPacket(this.protocolState, packetId);

                    packetBase.read(packetDecoder);

                    this.packetListeners.forEach(p -> p.handlePacket(packetBase));
                }

                sleep(50);
            } catch (InterruptedException | IOException | JEPacketReadException | JEUnknownPacketException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
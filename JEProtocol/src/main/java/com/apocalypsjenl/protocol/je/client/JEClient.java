package com.apocalypsjenl.protocol.je.client;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.*;
import com.apocalypsjenl.protocol.je.packet.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        try {
            this.socket = new Socket(host, port);
            this.packetDecoder = new PacketDecoder(this.socket.getInputStream());
            this.packetEncoder = new PacketEncoder(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new JEConnectException(e);
        }
    }

    public void disconnect() throws JEDisconnectException {
        try {
            //TODO send disconnect packet to server
            this.socket.close();
        } catch (IOException e) {
            throw new JEDisconnectException(e);
        }
    }

    public void addListener(IPacketListener listener) {
        this.packetListeners.add(listener);
    }

    public void sendPacket(JEPacketBase packet) {
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PacketEncoder packetEncoder = new PacketEncoder(outputStream);

            packetEncoder.writeVarInt(packet.getPacketId());
            packet.write(packetEncoder);

            this.packetEncoder.writeVarInt(outputStream.size());
            this.packetEncoder.write(outputStream.toByteArray());
        } catch (JEPacketWriteException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setProtocolState(JEProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public void listen() {
        executor.execute(() -> {
            boolean running = true;
            while (running) {
                try {
                    if (this.socket != null && !this.socket.isClosed() && this.packetDecoder != null && this.packetDecoder.available() > 0) {
                        int packetLength = this.packetDecoder.readVarInt();
                        byte[] packetBytes = new byte[packetLength];
                        this.packetDecoder.readFully(packetBytes);

                        ByteArrayInputStream inputStream = new ByteArrayInputStream(packetBytes);
                        PacketDecoder packetDecoder = new PacketDecoder(inputStream);

                        int packetId = packetDecoder.readVarInt();
                        JEPacketBase packetBase = JEPacketRegister.getPacket(JEProtocolDirection.SERVER, this.protocolState, packetId);
                        packetBase.read(packetDecoder);

                        executor.execute(() -> this.packetListeners.forEach(p -> p.handlePacket(packetBase)));
                    }

                    sleep(50);
                } catch (InterruptedException | IOException | JEPacketReadException | JEUnknownPacketException e) {
                    e.printStackTrace();
                    running = false;
                }
            }
        });
    }
}

package com.apocalypsjenl.protocol.je.packet.handshake;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEInvalidHandshakeStateException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketWriteException;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;
import com.apocalypsjenl.protocol.je.packet.JEProtocolVersion;

import java.io.IOException;

public class Handshake extends JEPacketBase {

    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private State state;

    @Override
    public int getPacketId() {
        return 0x00;
    }

    @Override
    public void read(PacketDecoder decoder) throws JEPacketReadException {
        try {
            this.protocolVersion = decoder.readVarInt();
            this.serverAddress = decoder.readString();
            this.serverPort = decoder.readShort();
            this.state = State.parseState(decoder.readVarInt());
        } catch (JEInvalidHandshakeStateException | IOException e) {
            throw new JEPacketReadException(e);
        }
    }

    @Override
    public void write(PacketEncoder encoder) throws JEPacketWriteException {
        try {
            encoder.writeVarInt(this.protocolVersion);
            encoder.writeString(this.serverAddress);
            encoder.writeShort(this.serverPort);
            encoder.writeVarInt(this.state.getState());
        } catch (IOException e) {
            throw new JEPacketWriteException(e);
        }
    }

    @Override
    public String toString() {
        return "Handshake{" +
                "protocolVersion=" + protocolVersion +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverPort=" + serverPort +
                ", state=" + state +
                '}';
    }

    public Handshake() {
    }

    public Handshake(int protocolVersion, String serverAddress, int serverPort, State state) {
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.state = state;
    }

    public Handshake(JEProtocolVersion protocolVersion, String serverAddress, int serverPort, State state) {
        this.protocolVersion = protocolVersion.getProtocolId();
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.state = state;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        STATUS(1), LOGIN(2);

        private int state;

        State(int state) {
            this.state = state;
        }

        static State parseState(int state) throws JEInvalidHandshakeStateException {
            switch (state) {
                case 1:
                    return LOGIN;
                case 2:
                    return STATUS;
                default:
                    throw new JEInvalidHandshakeStateException("Invalid Handshake state " + state);
            }
        }

        int getState() {
            return this.state;
        }
    }
}

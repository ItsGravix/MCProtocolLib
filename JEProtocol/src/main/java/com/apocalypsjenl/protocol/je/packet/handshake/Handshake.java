package com.apocalypsjenl.protocol.je.packet.handshake;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEInvalidHandshakeStateException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;

import java.io.IOException;

public class Handshake extends JEPacketBase {

    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private State state;

    @Override
    public int getPacketId() {
        return 0x00;
    }

    @Override
    public void read(PacketDecoder decoder) throws JEPacketReadException, IOException {
        this.protocolVersion = decoder.readVarInt();
        this.serverAddress = decoder.readString();
        this.serverPort = decoder.readShort();
        try {
            this.state = State.parseState(decoder.readVarInt());
        } catch (JEInvalidHandshakeStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(PacketEncoder encoder) throws IOException {
        encoder.writeVarInt(this.protocolVersion);
        encoder.writeString(this.serverAddress);
        encoder.writeShort(this.serverPort);
        encoder.writeVarInt(this.state.getState());
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

    public short getServerPort() {
        return serverPort;
    }

    public void setServerPort(short serverPort) {
        this.serverPort = serverPort;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        LOGIN(1), STATUS(2);

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

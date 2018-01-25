package com.apocalypsjenl.protocol.je.packet.status.server;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketWriteException;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;

import java.io.IOException;

public class StatusPong extends JEPacketBase {

    private long payload;

    @Override
    public int getPacketId() {
        return 0x01;
    }

    @Override
    public void read(PacketDecoder decoder) throws JEPacketReadException {
        try {
            this.payload = decoder.readLong();
        } catch (IOException e) {
            throw new JEPacketReadException(e);
        }
    }

    @Override
    public void write(PacketEncoder encoder) throws JEPacketWriteException {
        try {
            encoder.writeLong(this.payload);
        } catch (IOException e) {
            throw new JEPacketWriteException(e);
        }
    }

    @Override
    public String toString() {
        return "StatusPong{" +
                "payload=" + payload +
                '}';
    }

    public StatusPong() {
    }

    public StatusPong(long payload) {
        this.payload = payload;
    }

    public long getPayload() {
        return payload;
    }

    public void setPayload(long payload) {
        this.payload = payload;
    }
}

package com.apocalypsjenl.protocol.je.packet.status.client;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;

public class StatusRequest extends JEPacketBase {

    @Override
    public int getPacketId() {
        return 0x00;
    }

    @Override
    public void read(PacketDecoder decoder) {

    }

    @Override
    public void write(PacketEncoder encoder) {

    }

    @Override
    public String toString() {
        return "StatusRequest{}";
    }

    public StatusRequest() {
    }
}

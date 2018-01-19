package com.apocalypsjenl.protocol.je.packet;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;

import java.io.IOException;

public abstract class JEPacketBase {

    public abstract int getPacketId();

    public abstract void read(PacketDecoder decoder) throws JEPacketReadException, IOException;

    public abstract void write(PacketEncoder encoder) throws IOException;
}

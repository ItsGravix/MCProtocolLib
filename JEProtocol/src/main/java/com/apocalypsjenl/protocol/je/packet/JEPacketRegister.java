package com.apocalypsjenl.protocol.je.packet;

import com.apocalypsjenl.protocol.je.exceptions.JEUnknownPacketException;
import com.apocalypsjenl.protocol.je.packet.handshake.Handshake;

public class JEPacketRegister {

    public static JEPacketBase getPacket(JEProtocolState state, int packetId) throws JEUnknownPacketException {
        switch (state) {
            case HANDSHAKE:
                switch (packetId) {
                    case 0x00:
                        return new Handshake();
                }
                break;
        }

        throw new JEUnknownPacketException("No packet found with id " + packetId + " in state " + state.name());
    }
}

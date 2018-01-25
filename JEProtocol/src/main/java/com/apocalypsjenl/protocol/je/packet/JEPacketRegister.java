package com.apocalypsjenl.protocol.je.packet;

import com.apocalypsjenl.protocol.je.exceptions.JEUnknownPacketException;
import com.apocalypsjenl.protocol.je.packet.handshake.Handshake;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusPing;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusRequest;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusPong;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusResponse;

public class JEPacketRegister {

    public static JEPacketBase getPacket(JEProtocolDirection direction, JEProtocolState state, int packetId) throws JEUnknownPacketException {
        switch (state) {
            case HANDSHAKE:
                switch (packetId) {
                    case 0x00:
                        return new Handshake();
                }
                break;

            case STATUS:
                switch (direction) {
                    case CLIENT:
                        switch (packetId) {
                            case 0x00:
                                return new StatusRequest();
                            case 0x01:
                                return new StatusPing();
                        }
                        break;

                    case SERVER:
                        switch (packetId) {
                            case 0x00:
                                return new StatusResponse();
                            case 0x01:
                                return new StatusPong();
                        }
                        break;
                }
                break;
        }

        throw new JEUnknownPacketException("No packet found with id " + packetId + " in state " + state.name());
    }
}

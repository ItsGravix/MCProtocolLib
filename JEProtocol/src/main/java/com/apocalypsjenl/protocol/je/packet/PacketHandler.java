package com.apocalypsjenl.protocol.je.packet;

import com.apocalypsjenl.protocol.je.client.JEClient;
import com.apocalypsjenl.protocol.je.packet.handshake.Handshake;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusPing;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusRequest;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusPong;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusResponse;

public abstract class PacketHandler implements IPacketListener {

    protected JEClient client;

    public PacketHandler(JEClient client) {
        this.client = client;
    }

    @Override
    public void handlePacket(JEPacketBase packet) {
        if (packet instanceof Handshake) {
            this.handle((Handshake) packet);
        }
        if (packet instanceof StatusRequest) {
            this.handle((StatusRequest) packet);
        }
        if (packet instanceof StatusResponse) {
            this.handle((StatusResponse) packet);
        }
        if (packet instanceof StatusPing) {
            this.handle((StatusPing) packet);
        }
        if (packet instanceof StatusPong) {
            this.handle((StatusPong) packet);
        }
    }

    public void handle(Handshake handshake) {
    }

    public void handle(StatusRequest statusRequest) {
    }

    public void handle(StatusResponse statusResponse) {
    }

    public void handle(StatusPing statusPing) {
    }

    public void handle(StatusPong statusPong) {
    }
}

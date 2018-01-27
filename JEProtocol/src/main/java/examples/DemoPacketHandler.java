package examples;

import com.apocalypsjenl.protocol.je.client.JEClient;
import com.apocalypsjenl.protocol.je.exceptions.JEDisconnectException;
import com.apocalypsjenl.protocol.je.packet.PacketHandler;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusPing;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusPong;
import com.apocalypsjenl.protocol.je.packet.status.server.StatusResponse;

public class DemoPacketHandler extends PacketHandler {

    public DemoPacketHandler(JEClient client) {
        super(client);
    }

    @Override
    public void handle(StatusResponse statusResponse) {
        System.out.println(statusResponse);
        client.sendPacket(new StatusPing(System.currentTimeMillis()));
    }

    @Override
    public void handle(StatusPong statusPong) {
        System.out.println(statusPong);
        try {
            client.disconnect();
            System.exit(0);
        } catch (JEDisconnectException e) {
            e.printStackTrace();
        }
    }
}

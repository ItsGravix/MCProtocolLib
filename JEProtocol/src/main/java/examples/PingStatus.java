package examples;

import com.apocalypsjenl.protocol.je.client.JEClient;
import com.apocalypsjenl.protocol.je.exceptions.JEConnectException;
import com.apocalypsjenl.protocol.je.packet.JEProtocolState;
import com.apocalypsjenl.protocol.je.packet.handshake.Handshake;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusPing;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusRequest;

public class PingStatus {

    public static void main(String[] args) {
        new PingStatus();
    }

    public PingStatus() {
        try {
            JEClient client = new JEClient();
            client.connect("play.happymine.nl", 25565);

            client.addListener(System.out::println);
            client.listen();

            Handshake handshake = new Handshake(340, "play.happymine.nl", 25565, Handshake.State.STATUS);

            client.sendPacket(handshake);
            client.setProtocolState(JEProtocolState.STATUS);
            client.sendPacket(new StatusRequest());
            client.sendPacket(new StatusPing(System.currentTimeMillis()));
        } catch (JEConnectException e) {
            e.printStackTrace();
        }
    }
}

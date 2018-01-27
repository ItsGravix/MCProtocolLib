package examples;

import com.apocalypsjenl.protocol.je.client.JEClient;
import com.apocalypsjenl.protocol.je.exceptions.JEConnectException;
import com.apocalypsjenl.protocol.je.exceptions.JEDisconnectException;
import com.apocalypsjenl.protocol.je.packet.JEProtocolState;
import com.apocalypsjenl.protocol.je.packet.JEProtocolVersion;
import com.apocalypsjenl.protocol.je.packet.handshake.Handshake;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusPing;
import com.apocalypsjenl.protocol.je.packet.status.client.StatusRequest;

import static java.lang.Thread.sleep;

public class PingStatus {

    public static void main(String[] args) {
        new PingStatus();
    }

    public PingStatus() {
        JEClient client = new JEClient();
        try {
            client.connect("play.happymine.nl");

            client.addListener(new DemoPacketHandler(client));
            client.listen();

            Handshake handshake = new Handshake(JEProtocolVersion.v1_12_2, "play.happymine.nl", 25565, Handshake.State.STATUS);

            client.sendPacket(handshake);
            client.setProtocolState(JEProtocolState.STATUS);
            client.sendPacket(new StatusRequest());
        } catch (JEConnectException e) {
            e.printStackTrace();
        }
    }
}

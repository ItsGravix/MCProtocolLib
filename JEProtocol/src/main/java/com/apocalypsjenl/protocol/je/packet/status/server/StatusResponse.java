package com.apocalypsjenl.protocol.je.packet.status.server;

import com.apocalypsjenl.protocol.je.encoders.PacketDecoder;
import com.apocalypsjenl.protocol.je.encoders.PacketEncoder;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;
import com.apocalypsjenl.protocol.je.exceptions.JEPacketWriteException;
import com.apocalypsjenl.protocol.je.packet.JEPacketBase;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class StatusResponse extends JEPacketBase {

    private Gson gson;

    private StatusResponsePayload status;

    @Override
    public int getPacketId() {
        return 0x00;
    }

    @Override
    public void read(PacketDecoder decoder) throws JEPacketReadException {
        this.status = this.gson.fromJson(decoder.readString(), StatusResponsePayload.class);
    }

    @Override
    public void write(PacketEncoder encoder) throws JEPacketWriteException {
        encoder.writeString(this.gson.toJson(this.status));
    }

    public StatusResponse() {
        this.gson = new Gson();
    }

    public StatusResponse(StatusResponsePayload status) {
        this.gson = new Gson();
        this.status = status;
    }

    public StatusResponsePayload getStatus() {
        return status;
    }

    public void setStatus(StatusResponsePayload status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "status=" + status +
                '}';
    }

    public class StatusResponsePayload {

        private StatusVersionPayload version;
        private StatusPlayerPayload players;
        private StatusDescriptionPayload description;
        @SerializedName("favicon")
        private String serverIcon;

        public StatusResponsePayload(StatusVersionPayload version, StatusPlayerPayload players, StatusDescriptionPayload description, String serverIcon) {
            this.version = version;
            this.players = players;
            this.description = description;
            this.serverIcon = serverIcon;
        }

        public StatusVersionPayload getVersion() {
            return version;
        }

        public void setVersion(StatusVersionPayload version) {
            this.version = version;
        }

        public StatusPlayerPayload getPlayers() {
            return players;
        }

        public void setPlayers(StatusPlayerPayload players) {
            this.players = players;
        }

        public StatusDescriptionPayload getDescription() {
            return description;
        }

        public void setDescription(StatusDescriptionPayload description) {
            this.description = description;
        }

        public String getServerIcon() {
            return serverIcon;
        }

        public void setServerIcon(String serverIcon) {
            this.serverIcon = serverIcon;
        }

        @Override
        public String toString() {
            return "StatusResponsePayload{" +
                    "version=" + version +
                    ", players=" + players +
                    ", description=" + description +
                    ", serverIcon='" + serverIcon + '\'' +
                    '}';
        }
    }

    public class StatusVersionPayload {

        @SerializedName("name")
        private String version;
        @SerializedName("protocol")
        private String protocolId;

        public StatusVersionPayload(String version, String protocolId) {
            this.version = version;
            this.protocolId = protocolId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getProtocolId() {
            return protocolId;
        }

        public void setProtocolId(String protocolId) {
            this.protocolId = protocolId;
        }

        @Override
        public String toString() {
            return "StatusVersionPayload{" +
                    "version='" + version + '\'' +
                    ", protocolId='" + protocolId + '\'' +
                    '}';
        }
    }

    public class StatusPlayerPayload {

        @SerializedName("max")
        private int playerLimit;
        @SerializedName("online")
        private int onlinePlayers;
        @SerializedName("sample")
        private List<StatusPlayer> players;

        public StatusPlayerPayload(int playerLimit, int onlinePlayers, List<StatusPlayer> players) {
            this.playerLimit = playerLimit;
            this.onlinePlayers = onlinePlayers;
            this.players = players;
        }

        public int getPlayerLimit() {
            return playerLimit;
        }

        public void setPlayerLimit(int playerLimit) {
            this.playerLimit = playerLimit;
        }

        public int getOnlinePlayers() {
            return onlinePlayers;
        }

        public void setOnlinePlayers(int onlinePlayers) {
            this.onlinePlayers = onlinePlayers;
        }

        public List<StatusPlayer> getPlayers() {
            return players;
        }

        public void setPlayers(List<StatusPlayer> players) {
            this.players = players;
        }

        @Override
        public String toString() {
            return "StatusPlayerPayload{" +
                    "playerLimit=" + playerLimit +
                    ", onlinePlayers=" + onlinePlayers +
                    ", players=" + players +
                    '}';
        }
    }

    public class StatusPlayer {

        private String name;
        @SerializedName("id")
        private UUID uuid;

        public StatusPlayer(String name, UUID uuid) {
            this.name = name;
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public String toString() {
            return "StatusPlayer{" +
                    "name='" + name + '\'' +
                    ", uuid=" + uuid +
                    '}';
        }
    }

    public class StatusDescriptionPayload {

        @SerializedName("text")
        private String motd;

        public StatusDescriptionPayload(String motd) {
            this.motd = motd;
        }

        public String getMotd() {
            return motd;
        }

        public void setMotd(String motd) {
            this.motd = motd;
        }

        @Override
        public String toString() {
            return "StatusDescriptionPayload{" +
                    "motd='" + motd + '\'' +
                    '}';
        }
    }
}

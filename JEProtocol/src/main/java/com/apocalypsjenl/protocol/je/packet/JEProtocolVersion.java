package com.apocalypsjenl.protocol.je.packet;

public enum JEProtocolVersion {

    v1_12_2(340), v_1_12_1(338), v1_12(335), v1_11_1(316), v1_11(315), v1_10(210), v1_9_3(110), v1_9_2(109), v1_9_1(108), v1_9(107), v1_8(47);

    private int protocolId;

    JEProtocolVersion(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getProtocolId() {
        return protocolId;
    }
}

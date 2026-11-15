package fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator;

import lombok.Getter;

@Getter
public enum PublicIdType {
    STUDENT("STD", 3, 4);

    private final String prefix;
    private final int nbPart;
    private final int partLength;

    PublicIdType(String prefix, int nbPart, int partLength) {
        this.prefix = prefix;
        this.nbPart = nbPart;
        this.partLength = partLength;
    }

}

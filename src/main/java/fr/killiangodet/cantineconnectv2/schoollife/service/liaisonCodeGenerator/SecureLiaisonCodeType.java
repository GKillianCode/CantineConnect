package fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator;

import lombok.Getter;

@Getter
public enum SecureLiaisonCodeType {
    LINK("LNK", 5, 4);

    private final String prefix;
    private final int nbPart;
    private final int partLength;

    SecureLiaisonCodeType(String prefix, int nbPart, int partLength) {
        this.prefix = prefix;
        this.nbPart = nbPart;
        this.partLength = partLength;
    }

}

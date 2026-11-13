package fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator;

import fr.killiangodet.cantineconnectv2.common.service.secureIdGenerator.SecureIdGenerator;
import org.springframework.stereotype.Component;

@Component
public class PublicIdGenerator {

    SecureIdGenerator secureIdGenerator;

    public PublicIdGenerator(SecureIdGenerator secureIdGenerator) {
        this.secureIdGenerator = secureIdGenerator;
    }

    public String generate(PublicIdType type) {
        return secureIdGenerator.generate(type.getPrefix(), type.getNbPart(), type.getPartLength());
    }
}

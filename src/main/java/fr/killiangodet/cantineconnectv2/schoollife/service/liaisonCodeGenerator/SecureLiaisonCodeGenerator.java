package fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator;

import fr.killiangodet.cantineconnectv2.common.service.secureIdGenerator.SecureIdGenerator;
import org.springframework.stereotype.Component;

@Component
public class SecureLiaisonCodeGenerator {

    SecureIdGenerator secureIdGenerator;

    SecureLiaisonCodeGenerator(SecureIdGenerator secureIdGenerator) {
        this.secureIdGenerator = secureIdGenerator;
    }

    public String generate(SecureLiaisonCodeType codeType) {
        return secureIdGenerator.generate(codeType.getPrefix(), codeType.getNbPart(), codeType.getPartLength());
    }
}

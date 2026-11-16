package fr.killiangodet.cantineconnectv2.schoollife.annotation.validSecureLiaisonCode;

import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeType;
import jakarta.validation.ConstraintValidator;

public class SecureLiaisonCodeGenerator implements ConstraintValidator<ValidSecureLiaisonCode, String> {

    @Override
    public boolean isValid(String publicId, jakarta.validation.ConstraintValidatorContext context) {
        return SecureLiaisonCodeType.LINK.isValidFormat(publicId);
    }
}

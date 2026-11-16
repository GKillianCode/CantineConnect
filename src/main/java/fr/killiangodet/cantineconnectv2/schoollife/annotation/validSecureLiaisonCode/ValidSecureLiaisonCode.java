package fr.killiangodet.cantineconnectv2.schoollife.annotation.validSecureLiaisonCode;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecureLiaisonCodeGenerator.class)
@Documented
public @interface ValidSecureLiaisonCode {
    String message() default "Invalid public ID";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}

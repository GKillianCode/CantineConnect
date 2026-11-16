package fr.killiangodet.cantineconnectv2.schoollife.payload.request;

import fr.killiangodet.cantineconnectv2.schoollife.annotation.validSecureLiaisonCode.ValidSecureLiaisonCode;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LinkStudentRequest(
        @NotNull(message = "Public ID is required")
        @ValidSecureLiaisonCode
        String publicId,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate
) {
}

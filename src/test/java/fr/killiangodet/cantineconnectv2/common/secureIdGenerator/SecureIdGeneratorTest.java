package fr.killiangodet.cantineconnectv2.common.secureIdGenerator;

import fr.killiangodet.cantineconnectv2.common.service.secureIdGenerator.SecureIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SecureIdGeneratorTest {

    private SecureIdGenerator secureIdGenerator;

    @BeforeEach
    void setUp() {
        this.secureIdGenerator = new SecureIdGenerator();
    }

    @Test
    void generateValidSecureId() {
        String prefix = "TEST";
        int nbParts = 2;
        int partLength = 6;
        String secureId = secureIdGenerator.generate(prefix, nbParts, partLength);
        String[] parts = secureId.substring(prefix.length() + 1).split("-");

        assertThat(secureId).isNotNull();
        assertThat(secureId).startsWith(prefix + "-");
        assertThat(parts).hasSize(nbParts);
        for (String part : parts) {
            assertThat(part).hasSize(partLength);
        }
    }

    @Test
    void generateInvalidSecureIdWithWrongPrefix() {
        int nbParts = 2;
        int partLength = 6;

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        secureIdGenerator.generate("", nbParts, partLength)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Prefix");
    }

    @Test
    void generateInvalidSecureIdWithWrongNbParts() {
        String prefix = "TEST";
        int nbParts = -10;
        int partLength = 6;

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        secureIdGenerator.generate(prefix, nbParts, partLength)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Number of parts");
    }

    @Test
    void generateInvalidSecureIdWithWrongPartLength() {
        String prefix = "TEST";
        int nbParts = 2;
        int partLength = -10;

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        secureIdGenerator.generate(prefix, nbParts, partLength)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Part length");
    }
}

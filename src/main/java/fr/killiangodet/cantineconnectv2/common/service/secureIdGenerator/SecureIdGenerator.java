package fr.killiangodet.cantineconnectv2.common.service.secureIdGenerator;

import org.springframework.stereotype.Component;

@Component
public class SecureIdGenerator implements idGenerator {

    @Override
    public String generate(String prefix, int nbParts, int partLength) {
        final String characters = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        final char separator = '-';
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nbParts; i++) {

            StringBuilder part = new StringBuilder();
            for (int j = 0; j < partLength; j++) {
                int index = (int) (Math.random() * characters.length());
                part.append(characters.charAt(index));
            }
            sb.append(part);

            if (i < nbParts - 1) {
                sb.append(separator);
            }
        }
        return prefix + "-" + sb.toString();
    }

    @Override
    public String generate(String prefix, int nbParts) {
        return generate(prefix, nbParts, 6);
    }
}

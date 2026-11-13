package fr.killiangodet.cantineconnectv2.common.service.secureIdGenerator;

public interface idGenerator {

    /*
     * Generates a unique public ID for a student.
     * @param nbParts the number of parts in the public ID
     * @param partLength the length of each part in the public ID
     * @return a unique public ID
     */
    String generate(String prefix, int nbParts, int partLength);

    /*
     * Generates a unique public ID for a student.
     * @param nbParts the number of parts in the public ID
     * @return a unique public ID
     */
    String generate(String prefix, int nbParts);
}

package fr.killiangodet.cantineconnect.schoollife.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class StudentId {

    private static final String PREFIX = "STD-";
    private final UUID uuid;

    private StudentId(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid, "L'UUID de l'élève ne peut pas être nul.");
    }

    public static StudentId generate() {
        return new StudentId(UUID.randomUUID());
    }

    public static StudentId of(UUID uuid) {
        return new StudentId(uuid);
    }

    public static StudentId of(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide.");
        }

        String rawuuid = uuid.trim();

        if (rawuuid.startsWith(PREFIX)) {
            rawuuid = rawuuid.substring(PREFIX.length());
        }

        try {
            return new StudentId(UUID.fromString(rawuuid));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Format d'identifiant élève invalide : " + uuid, e);
        }
    }

    public UUID getuuid() {
        return uuid;
    }

    public String asFormattedString() {
        return PREFIX + uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentId studentId = (StudentId) o;
        return Objects.equals(uuid, studentId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return asFormattedString();
    }
}

package fr.killiangodet.cantineconnect.schoollife.domain.exception;

import fr.killiangodet.cantineconnect.shared.domain.exception.DomainException;

public class InvalidStudentDataException extends DomainException {

    public InvalidStudentDataException(String messageKey) {
        super(messageKey);
    }
}

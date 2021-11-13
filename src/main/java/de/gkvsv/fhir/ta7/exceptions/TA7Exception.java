package de.gkvsv.fhir.ta7.exceptions;

/**
 * created by mmbarek on 09.11.2021.
 */
public class TA7Exception  extends RuntimeException {
    public TA7Exception() {
    }

    public TA7Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public TA7Exception(String message) {
        super(message);
    }

    public TA7Exception(Throwable cause) {
        super(cause);
    }
}

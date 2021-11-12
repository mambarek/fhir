package de.gkvsv.fhir.ta7.model.enums;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;

/**
 * Kennzeichen Zu-/Abschlag surcharge = Zuschlag; deduction = Abschlag.
 * Das Kennzeichen Zu- oder Abschlag dient als zusätzliche Information zum Betrag, der ein negatives
 * Vorzeichen (Abschlag) haben kann.
 * created by mmbarek on 10.11.2021.
 * TODO "surcharge" und "deducation" wrden galube ich vom FHIR angeboten
 * TODO bitte überprüfen und eventuell dieses Enum löschen
 */
public enum ZuAbschlagKennzeichen {

    SURCHARGE("surcharge"),
    DEDUCTION("deduction");


    ZuAbschlagKennzeichen(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static ZuAbschlagKennzeichen fromCode(String codeString) {
        if (codeString == null || "".equals(codeString)) {
            return null;
        }

        switch (codeString) {
            case "surcharge":
                return SURCHARGE;
            case "deduction":
                return DEDUCTION;
        }

        throw new TA7Exception("Unknown ZuAbschlagKennzeichen code '" + codeString + "'");
    }
}

package de.gkvsv.fhir.ta7.model.enums;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class VerwurfEnum {

    public enum Verwurf implements IFhirEnum {

        // Entspricht den Regelungen
        ENTSPRICHT_NICHT_DEN_REGELN(1),

        // Prüfung nachgelagert durch zentrale Stelle
        PRUEFUNG_NACHGELAGERT_DURCH_ZENTRALE_STELLE(2),

        // Unzulässige Menge Verwurf (Verwurf größer als kleinste Packungseinheit)
        UNZULAESSIGE_MENGE_VERWURF(3),

        // Nicht in HA3 enthalten und damit nicht prüfbar
        NICHT_IN_HA3_ENTHALTEN(4),

        // Apotheke stellt nicht selbst her und es werden andere Stoffe als in Anhang 1 verwendet
        NICHT_SELBST_HERGESTELLT_UND_ANDERE_STOFFE_VERWENDET(5),

        // Zeitspanne zu klein für erneuten Verwurf
        ZEITSPANNE_ZU_KLEIN_FUER_ERNEUTEN_VERWURF(6),

        // Kennzeichen des Herstellenden nach DAV-Vergabe existiert nicht und Verwurf damit nicht prüfbar
        HERSTELLER_KENNZEICHEN_EXISTIERT_NICHT(7);

        private final int code;

        Verwurf(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Verwurf fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "1":
                    return ENTSPRICHT_NICHT_DEN_REGELN;
                case "2":
                    return PRUEFUNG_NACHGELAGERT_DURCH_ZENTRALE_STELLE;
                case "3":
                    return UNZULAESSIGE_MENGE_VERWURF;
                case "4":
                    return NICHT_IN_HA3_ENTHALTEN;
                case "5":
                    return NICHT_SELBST_HERGESTELLT_UND_ANDERE_STOFFE_VERWENDET;
                case "6":
                    return ZEITSPANNE_ZU_KLEIN_FUER_ERNEUTEN_VERWURF;
                case "7":
                    return HERSTELLER_KENNZEICHEN_EXISTIERT_NICHT;
            }

            throw new TA7Exception("Unknown Verwurf code '" + codeString + "'");
        }

        @Override
        public String toCode() {
            return String.valueOf(this.code);
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Verwurf";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case ENTSPRICHT_NICHT_DEN_REGELN:
                    return "Entspricht den Regelungen";
                case PRUEFUNG_NACHGELAGERT_DURCH_ZENTRALE_STELLE:
                    return "Prüfung nachgelagert durch zentrale Stelle";
                case UNZULAESSIGE_MENGE_VERWURF:
                    return "Unzulässige Menge Verwurf (Verwurf größer als kleinste Packungseinheit)";
                case NICHT_IN_HA3_ENTHALTEN:
                    return "Nicht in HA3 enthalten und damit nicht prüfbar";
                case NICHT_SELBST_HERGESTELLT_UND_ANDERE_STOFFE_VERWENDET:
                    return "Apotheke stellt nicht selbst her und es werden andere Stoffe als in Anhang 1 verwendet";
                case ZEITSPANNE_ZU_KLEIN_FUER_ERNEUTEN_VERWURF:
                    return "Zeitspanne zu klein für erneuten Verwurf";
                case HERSTELLER_KENNZEICHEN_EXISTIERT_NICHT:
                    return "Kennzeichen des Herstellenden nach DAV-Vergabe existiert nicht und Verwurf damit nicht prüfbar";
                default: return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }

    }

    public static class VerwurfFactory implements EnumFactory<Verwurf> {

        @Override
        public Verwurf fromCode(String codeString) throws IllegalArgumentException {
            return Verwurf.fromCode(codeString);
        }

        @Override
        public String toCode(Verwurf verwurf) {
            if(verwurf == null) {
                return null;
            }

            return verwurf.toCode();
        }

        @Override
        public String toSystem(Verwurf verwurf) {
            if(verwurf == null) {
                return null;
            }

            return verwurf.getSystem();
        }
    }
}

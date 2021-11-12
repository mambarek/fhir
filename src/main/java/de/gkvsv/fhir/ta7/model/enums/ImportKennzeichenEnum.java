package de.gkvsv.fhir.ta7.model.enums;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 07.11.2021.
 * 0	kein Import 1	Import mit gesetzlichem Preisabstand zum Bezugsarzneimittel (Original) im
 * Sinne des Rahmenvertrages zu § 129 SGB V 2	Import unterhalb des gesetzlichen Preisabstandes
 * zum Bezugsarzneimittel (Original) im Sinne des Rahmenvertrages zu § 129 SGB V 3	Import, zu
 * dem kein Bezugsarzneimittel (Original) existiert Das Feld muss gefüllt werden, wenn das Feld
 * „Kennzeichen nach § 4“ eine PZN eines Fertigarzneimittels beinhaltet
 */
public class ImportKennzeichenEnum {

    public enum ImportKennzeichen {
        KEIN_IMPORT(0),
        MIT_GESTZL_PREISABSTAND(1),
        UNTER_GESTZL_PREIS_ABSTAND(2),
        KEIN_BEZUGS_ARZNEI_MITTEL(3);

        private int code;

        ImportKennzeichen(int code) {
            this.code = code;
        }

        public static ImportKennzeichen fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "0":
                    return KEIN_IMPORT;
                case "1":
                    return MIT_GESTZL_PREISABSTAND;
                case "2":
                    return UNTER_GESTZL_PREIS_ABSTAND;
                case "3":
                    return KEIN_BEZUGS_ARZNEI_MITTEL;
            }

            throw new FHIRException("Unknown ImportKennzeichen code '" + codeString + "'");
        }

        public String toCode() {
            return String.valueOf(this.code);
        }

        public String getSystem() {
            switch (this) {
                case KEIN_IMPORT:
                    return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import";
                case MIT_GESTZL_PREISABSTAND:
                    return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import";
                case UNTER_GESTZL_PREIS_ABSTAND:
                    return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import";
                case KEIN_BEZUGS_ARZNEI_MITTEL:
                    return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import";
                default:
                    return "?";
            }
        }

        public String getDefinition() {
            switch (this) {
                case KEIN_IMPORT:
                    return "kein Import";
                case MIT_GESTZL_PREISABSTAND:
                    return "Import mit gesetzlichem Preisabstand zum Bezugsarzneimittel (Original) im Sinne des Rahmenvertrages zu § 129 SGB V";
                case UNTER_GESTZL_PREIS_ABSTAND:
                    return "Import unterhalb des gesetzlichen Preisabstandes zum Bezugsarzneimittel (Original) im Sinne des Rahmenvertrages zu § 129 SGB V";
                case KEIN_BEZUGS_ARZNEI_MITTEL:
                    return "Import, zu dem kein Bezugsarzneimittel (Original) existiert Das Feld muss gefüllt werden, wenn das Feld „Kennzeichen nach § 4“ eine PZN eines Fertigarzneimittels beinhaltet";
                default:
                    return "?";
            }
        }

        public String getDisplay() {
            switch (this) {
                case KEIN_IMPORT:
                    return "kein Import";
                case MIT_GESTZL_PREISABSTAND:
                    return "Import mit gesetzlichem Preisabstand zum Bezugsarzneimittel (Original) im Sinne des Rahmenvertrages zu § 129 SGB V";
                case UNTER_GESTZL_PREIS_ABSTAND:
                    return "Import unterhalb des gesetzlichen Preisabstandes zum Bezugsarzneimittel (Original) im Sinne des Rahmenvertrages zu § 129 SGB V";
                case KEIN_BEZUGS_ARZNEI_MITTEL:
                    return "Import, zu dem kein Bezugsarzneimittel (Original) existiert Das Feld muss gefüllt werden, wenn das Feld „Kennzeichen nach § 4“ eine PZN eines Fertigarzneimittels beinhaltet";
                default:
                    return "?";
            }
        }
    }

    public static class ImportKennzeichenEnumFactory implements EnumFactory<ImportKennzeichen> {

        @Override
        public ImportKennzeichen fromCode(String codeString) throws IllegalArgumentException {
            return ImportKennzeichen.fromCode(codeString);
        }

        @Override
        public String toCode(ImportKennzeichen kennzeichen) {
            if (kennzeichen == null) {
                return null;
            }
            return kennzeichen.toCode();
        }

        @Override
        public String toSystem(ImportKennzeichen kennzeichen) {
            if (kennzeichen == null) {
                return null;
            }
            return kennzeichen.getSystem();
        }
    }
}

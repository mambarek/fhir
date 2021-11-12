package de.gkvsv.fhir.ta7.model.enums;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class RechnungsartEnum {

    /**
     * Kuerzel
     * AZ = Abrechnungszentrum, APO=Apotheke, KRA=Krankenkasse, ABRECHN=Abrechnung
     */
    public enum Rechnungsart implements IFhirEnum {
        APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA(1),
        ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA(2),
        ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_AZ_IK_DURCH_KRA(3),
        APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ(4),
        APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ(5),
        APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_AZ_DURCH_KRA_AZ(6),
        EINZELRECHNUNG_KRANKENKASSE_EINER_SAMMELABRECHNUNG(7),
        EINZELRECHNUNG_APOTHEKE_EINER_SAMMELABRECHNUNG(8);

        private final int code;

        Rechnungsart(int code) {
            this.code = code;
        }

        @Override
        public String toCode() {
            return String.valueOf(code);
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Rechnungsart";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA:
                    return "Abrechnung der Apotheke und Zahlung über IK der Apotheke durch Krankenkasse";
                case ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA:
                    return "Abrechnung über Abrechnungszentrum der Apotheke und Zahlung über IK der Apotheke durch Krankenkasse";
                case ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_AZ_IK_DURCH_KRA:
                    return "Abrechnung über Abrechnungszentrum der Apotheke und Zahlung über IK des Abrechnungszentrums durch Krankenkasse";
                case APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ:
                    return "Abrechnung der Apotheke  und Zahlung über IK der Apotheke durch Abrechnungszentrum der Krankenkasse";
                case APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ:
                    return "Abrechnung des Abrechnungszentrums der Apotheken und Zahlung über IK  der Apotheke durch Abrechnungszentrum der Krankenkasse";
                case APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_AZ_DURCH_KRA_AZ:
                    return "Abrechnung des Abrechnungszentrums der Apotheken und Zahlung über IK des Abrechnungszentrums der Apotheken durch Abrechnungszentrum der Krankenkasse";
                case EINZELRECHNUNG_KRANKENKASSE_EINER_SAMMELABRECHNUNG:
                    return "Einzelrechnung &quot;Krankenkasse&quot; einer Sammelabrechnung";
                case EINZELRECHNUNG_APOTHEKE_EINER_SAMMELABRECHNUNG:
                    return "Einzelrechnung &quot;Apotheke&quot; einer Sammelabrechnung";
                default:
                    return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }

        public static Rechnungsart fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "1":
                    return APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA;
                case "2":
                    return ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA;
                case "3":
                    return ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_AZ_IK_DURCH_KRA;
                case "4":
                    return APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ;
                case "5":
                    return APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA_AZ;
                case "6":
                    return APO_AZ_ABRECHN_UND_ZAHLUNG_UEBER_APO_AZ_DURCH_KRA_AZ;
                case "7":
                    return EINZELRECHNUNG_KRANKENKASSE_EINER_SAMMELABRECHNUNG;
                case "8":
                    return EINZELRECHNUNG_APOTHEKE_EINER_SAMMELABRECHNUNG;
            }

            throw new FHIRException("Unknown Rechnungsart code '" + codeString + "'");
        }
    }

    public static class RechnungsartFactory implements EnumFactory<Rechnungsart> {

        @Override
        public Rechnungsart fromCode(String codeString) throws IllegalArgumentException {
            return Rechnungsart.fromCode(codeString);
        }

        @Override
        public String toCode(Rechnungsart code) {
            return code.toCode();
        }

        @Override
        public String toSystem(Rechnungsart code) {
            return code.getSystem();
        }
    }
}

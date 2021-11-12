package de.gkvsv.fhir.ta7.model.enums;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class PositionstypEnum {

    public enum Positionstyp implements IFhirEnum {
        NULLPOSITION(0),
        UEBRIGE_POSITION(1);

        private final int code;

        Positionstyp(int code) {
            this.code = code;
        }

        @Override
        public String toCode() {
            return String.valueOf(code);
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Positionstyp";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case NULLPOSITION: return "Nullposition";
                case UEBRIGE_POSITION: return "übrige Position";
                default: return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }

        public static Positionstyp fromCode(String codeString) {
            if (codeString == null || "".equals(codeString))
                return null;

            switch (codeString) {
                case "0": return NULLPOSITION;
                case "1": return UEBRIGE_POSITION;
            }

            throw new FHIRException("Unknown Positionstyp code '"+codeString+"'");
        }
    }

    public static class PositionstypFactory implements EnumFactory<Positionstyp> {

        @Override
        public Positionstyp fromCode(String codeString) throws IllegalArgumentException {
            return Positionstyp.fromCode(codeString);
        }

        @Override
        public String toCode(Positionstyp code) {
            return code.toCode();
        }

        @Override
        public String toSystem(Positionstyp code) {
            return code.getSystem();
        }
    }
}

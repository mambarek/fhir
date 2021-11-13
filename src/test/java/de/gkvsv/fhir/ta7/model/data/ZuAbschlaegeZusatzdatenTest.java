package de.gkvsv.fhir.ta7.model.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 11.11.2021.
 */
class ZuAbschlaegeZusatzdatenTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Test den Betrag in Euro
     * Preis-Format muss 1-12 Stellen inkl. Trenner, eventuelles Minusvorzeichen und 2 Nachkommastellen haben.
     * "expression": "toString().matches('^(-\\d{1,8}|\\d{1,9})\\.\\d{2}$')"
     * "source": "http://fhir.abda.de/eRezeptAbgabedaten/StructureDefinition/DAV-PR-ERP-PreisangabeEUR"
     */
    @Test
    public void testZuAbschlagBetragValidation() {
        ZuAbschlaegeZusatzdaten zusatzdaten = new ZuAbschlaegeZusatzdaten();

        // richtige Preisangabe
        zusatzdaten.setZuAbschlagBetrag("1123.00");

        Set<ConstraintViolation<ZuAbschlaegeZusatzdaten>> validationsResult = validator.validate(
            zusatzdaten);

        assertThat(validationsResult).isEmpty();

        // Fehlerhafter Preisangaben: ohne Nachkommastellen
        zusatzdaten.setZuAbschlagBetrag("1123");
        validationsResult = validator.validate(zusatzdaten);
        assertThat(validationsResult).isNotEmpty();

        // Fehlerhafter Preisangaben: Preistrenner "Komma" statt "Punkt"
        zusatzdaten.setZuAbschlagBetrag("1123,00");
        validationsResult = validator.validate(zusatzdaten);
        assertThat(validationsResult).isNotEmpty();

        // Fehlerhafter Preisangaben: Nachkommastellen > 2
        zusatzdaten.setZuAbschlagBetrag("1123.123");
        validationsResult = validator.validate(zusatzdaten);
        assertThat(validationsResult).isNotEmpty();

        // Fehlerhafter Preisangaben: länge als 12
        zusatzdaten.setZuAbschlagBetrag("1234567890,00");
        validationsResult = validator.validate(zusatzdaten);
        assertThat(validationsResult).isNotEmpty();
    }

}

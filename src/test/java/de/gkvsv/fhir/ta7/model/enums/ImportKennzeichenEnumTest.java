package de.gkvsv.fhir.ta7.model.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 20.11.2021.
 */
class ImportKennzeichenEnumTest {

    @Test
    void testSystem() {
        assertEquals(ImportKennzeichenEnum.SYSTEM_URL, "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import");
        for(int i=0; i< ImportKennzeichen.values().length; i++) {
            assertEquals(ImportKennzeichen.values()[i].getSystem(), ImportKennzeichenEnum.SYSTEM_URL);
        }
    }

    @Test
    void testCodes() {
        assertEquals(ImportKennzeichen.KEIN_IMPORT.toCode(), "0");
        assertEquals(ImportKennzeichen.MIT_GESTZL_PREISABSTAND.toCode(), "1");
        assertEquals(ImportKennzeichen.UNTER_GESTZL_PREIS_ABSTAND.toCode(), "2");
        assertEquals(ImportKennzeichen.KEIN_BEZUGS_ARZNEI_MITTEL.toCode(), "3");
    }

    @Test
    void testFromCode() {
        assertNull(ImportKennzeichen.fromCode(null));
        assertNull(ImportKennzeichen.fromCode(""));
        assertEquals(ImportKennzeichen.fromCode("0"),ImportKennzeichen.KEIN_IMPORT);
        assertEquals(ImportKennzeichen.fromCode("1"),ImportKennzeichen.MIT_GESTZL_PREISABSTAND);
        assertEquals(ImportKennzeichen.fromCode("2"),ImportKennzeichen.UNTER_GESTZL_PREIS_ABSTAND);
        assertEquals(ImportKennzeichen.fromCode("3"),ImportKennzeichen.KEIN_BEZUGS_ARZNEI_MITTEL);

        assertThrows(TA7Exception.class, () -> ImportKennzeichen.fromCode("wrong_code"));
    }
}

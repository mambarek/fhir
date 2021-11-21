package de.gkvsv.fhir.ta7.model.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 20.11.2021.
 */
class ZuAbschlagKeyEnumTest {

    @Test
    void testSystem() {
        assertEquals(ZuAbschlagKeyEnum.SYSTEM_URL, "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey");
        for(int i=0; i<ZuAbschlagKey.values().length; i++) {
            assertEquals(ZuAbschlagKey.values()[i].getSystem(), ZuAbschlagKeyEnum.SYSTEM_URL);
        }
    }

    @Test
    void testCodes() {
        assertEquals(ZuAbschlagKey.APOTHEKEN_ABSCHLAG.getCode(), "R001");
        assertEquals(ZuAbschlagKey.SONSTIGER_ABSCHLAG_RABATT.getCode(), "R003");
        assertEquals(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a.getCode(), "R004");
        assertEquals(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3a.getCode(), "R005");
        assertEquals(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3b.getCode(), "R006");
        assertEquals(ZuAbschlagKey.MEHRKOSTEN_RABATT_ANGABE.getCode(), "R007");
        assertEquals(ZuAbschlagKey.RABATT_ANGABE_NACH_P130a_A8.getCode(), "R008");
        assertEquals(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A2.getCode(), "R009");
    }

    @Test
    void testFromCode() {
        assertNull(ZuAbschlagKey.fromCode(null));
        assertNull(ZuAbschlagKey.fromCode(""));
        assertEquals(ZuAbschlagKey.fromCode("R001"), ZuAbschlagKey.APOTHEKEN_ABSCHLAG);
        assertEquals(ZuAbschlagKey.fromCode("R003"), ZuAbschlagKey.SONSTIGER_ABSCHLAG_RABATT);
        assertEquals(ZuAbschlagKey.fromCode("R004"), ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a);
        assertEquals(ZuAbschlagKey.fromCode("R005"), ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3a);
        assertEquals(ZuAbschlagKey.fromCode("R006"), ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3b);
        assertEquals(ZuAbschlagKey.fromCode("R007"), ZuAbschlagKey.MEHRKOSTEN_RABATT_ANGABE);
        assertEquals(ZuAbschlagKey.fromCode("R008"), ZuAbschlagKey.RABATT_ANGABE_NACH_P130a_A8);
        assertEquals(ZuAbschlagKey.fromCode("R009"), ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A2);

        assertThrows(TA7Exception.class, () -> ZuAbschlagKey.fromCode("wrong_code"));
    }
}

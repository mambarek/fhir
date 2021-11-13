package de.gkvsv.fhir.ta7.model.data;

import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKennzeichen;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * Zu- und Abschläge auf Zusatzdaten
 * created by mmbarek on 10.11.2021.
 */
@Data
public class ZuAbschlaegeZusatzdaten {

    /**
     * Zu- und Abschläge auf Zusatzdaten
     */
    private ZuAbschlagKey zuAbschlaegeZusatzdaten;

    private ZuAbschlagKennzeichen zuAbschlagKennzeichen;

    /**
     * Betrag Zu-/Abschlag in Euro
     * Preis-Format muss 1-12 Stellen inkl. Trenner, eventuelles Minusvorzeichen und 2 Nachkommastellen haben.
     * http://fhir.abda.de/eRezeptAbgabedaten/StructureDefinition/DAV-PR-ERP-PreisangabeEUR
     */
    @Pattern(regexp = "^(-\\d{1,8}|\\d{1,9})\\.\\d{2}$",
    message = "Preis-Format muss 1-12 Stellen inkl. Trenner, eventuelles Minusvorzeichen und 2 Nachkommastellen haben.")
    private String zuAbschlagBetrag;

}

package de.gkvsv.fhir.ta7.model.data;

import de.gkvsv.fhir.ta7.model.enums.VerwurfEnum.Verwurf;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.Data;

/**
 * created by mmbarek on 09.11.2021.
 */
@Data
public class AbrechungsPosition {

    /**
     * Zähler Abrechnungsposition, min=1
     */
    @Positive
    private int zaehlerAbrechnungsposition;

    /**
     * Verwurf geprüft
     * Kennzeichen gemäß Vertrag über die Preis-bildung für Stoffe und Zubereitungen aus Stoffen
     * (Hilfstaxe) Anlage 3 Teil 1 Anhang 3. Dieses Feld ist zu füllen, wenn ein unvermeidbarer
     * Verwurf abgerechnet wird (Faktorkennzeichen = 99).
     */
    private Verwurf verwurfGeprueft;

    /**
     * Zu- und Abschläge auf Zusatzdaten
     */
    private List<ZuAbschlaegeZusatzdaten> zuAbschlaegeZusatzdatenList;
}

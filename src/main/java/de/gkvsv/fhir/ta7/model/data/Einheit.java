package de.gkvsv.fhir.ta7.model.data;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Data;

/**
 * created by mmbarek on 09.11.2021.
 * Extension in {@link ZusatzDatenHerstellung}
 * @author mmbarek
 */
@Data
public class Einheit {

    /**
     * Zähler Einheit, positive int, min = 1
     */
    @Positive
    private int zaehlerEinheit;

    /**
     * Abrechnungspositionen, min = 1
     */
    @NotEmpty
    private List<AbrechungsPosition> abrechungsPositionen;
}

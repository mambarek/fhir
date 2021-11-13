package de.gkvsv.fhir.ta7.model.data;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Zusatzdaten Herstellung
 * Zu-/Abschläge auf parenterale Zubereitungen, wirtschaftlichen Einzelmengen etc.
 * created by mmbarek on 09.11.2021.
 */
@Data
public class ZusatzDatenHerstellung {

    /**
     * Zähler Herstellung, positive int, min = 1, max = 99
     */
    @Min(1)
    @Max(99)
    private int zaehlerHerstellung;

    @NotEmpty
    private List<Einheit> einheiten;
}

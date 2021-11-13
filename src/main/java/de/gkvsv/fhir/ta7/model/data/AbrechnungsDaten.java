package de.gkvsv.fhir.ta7.model.data;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * created by mmbarek on 08.11.2021.
 */
// Rechnung
@Data
public class AbrechnungsDaten {

    @NotNull
    private String identifier;

    /**
     * Rezept-ID
     * Eindeutige Identifikation der Verordnung (Dokumenten-ID), auf die sich die Abrechnungsdaten beziehen
     */
    @NotNull
    @Size(min = 22, max = 22, message = "Die Rezept-ID muss 22 Zeichen lang sein")
    private String rezeptId;

    /**
     * Eindeutige Belegnummer
     * Belegnummer mit Aufbau entsprechend der Technischen Anlage 1 zzgl. vorangestelltes Jahrzehnt
     * (2-stelliges Jahr, 2-stelliger Monat, 8-stellige Zählnummer und IK ohne die ersten beiden
     * Stellen oder dreistelliges Identifikationsmerkmal.
     */
    @NotNull
    @Size(min = 19, max = 19, message = "Die eindeutige Belegnummer muss numerisch und 19 Zeichen lang sein.")
    private String belegNummer;

    private String status;

    // Kennzeichen für Irrläuferrezepte
    // https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer
    private boolean irrlaeufer;

    // https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer
    @Size(max = 100)
    private List<String> zusatzDatenHerstellung;

    // #### issuer ##################
    // enum 1 = Leistungserbringer mit Sitz im Inland; \n2 = Leistungserbringer mit Sitz im Ausland
    // https://simplifier.net/erezeptabrechnungsdaten/gkvsv-cs-erp-leistungserbringer-sitz
    @NotNull
    private String leistungsErbringerSitz;

    // https://simplifier.net/erezeptabrechnungsdaten/gkvsv-cs-erp-leistungserbringertyp
    // enum A=Öffentliche Apotheken, K=Krankenhausapotheken, S=Sonstige Leistungserbringer
    @NotNull
    private String leistungsErbringerTyp;

    @NotNull
    private String apothekenIK;

    // complex
    @NotNull
    private String lineItem;
}

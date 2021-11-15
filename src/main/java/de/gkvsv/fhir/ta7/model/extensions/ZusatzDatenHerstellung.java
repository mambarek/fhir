package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.util.ElementUtil;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.PositiveIntType;

/**
 * created by mmbarek on 12.11.2021.
 */
@Block
@Description("Zu-/Abschläge auf parenterale Zubereitungen, wirtschaftlichen Einzelmengen etc.")
public class ZusatzDatenHerstellung extends BackboneElement {

    private static final long serialVersionUID = 1L;

    /**
     * positiv int >0 and <100
     */
    @Child(name = "zaehlerHerstellung", min = 1)
    @Extension(url="zaehlerHerstellung", definedLocally = false, isModifier = false)
    private PositiveIntType zaehlerHerstellung;

    @Child(name = "einheit", min = 1)
    @Extension(url = "einheit")
    private List<Einheit> einheiten;

    public PositiveIntType getZaehlerHerstellung() {
        if(zaehlerHerstellung == null) {
            zaehlerHerstellung = new PositiveIntType();
        }
        return zaehlerHerstellung;
    }

    public ZusatzDatenHerstellung setZaehlerHerstellung(PositiveIntType zaehlerHerstellung) {
        this.zaehlerHerstellung = zaehlerHerstellung;
        return this;
    }

    public ZusatzDatenHerstellung setZaehlerHerstellung(int zaehlerHerstellung) {
        this.getZaehlerHerstellung().setValue(zaehlerHerstellung);
        return this;
    }

    public List<Einheit> getEinheiten() {
        if(einheiten == null) {
            einheiten = new ArrayList<>();
        }
        return einheiten;
    }

    public ZusatzDatenHerstellung setEinheiten(List<Einheit> einheiten) {
        this.einheiten = einheiten;
        return this;
    }

    @Override
    public ZusatzDatenHerstellung copy() {
        ZusatzDatenHerstellung copy = new ZusatzDatenHerstellung();
        copy.setZaehlerHerstellung(getZaehlerHerstellung());
        copy.setEinheiten(getEinheiten());
        return copy;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.getEinheiten().isEmpty() && this.getZaehlerHerstellung().isEmpty();
    }


    @Block
    public static class Einheit extends BackboneElement {

        @Child(name = "zaehlerEinheit")
        @Extension(url="zaehlerEinheit", definedLocally = false, isModifier = false)
        private IntegerType zaehlerEinheit;

        @Child(name = "abrechnungsposition")
        @Extension(url = "abrechnungspositionen")
        private List<Abrechnungsposition> abrechnungspositionen;


        public List<Abrechnungsposition> getAbrechnungspositionen() {
            if(abrechnungspositionen == null) {
                abrechnungspositionen = new ArrayList<>();
            }
            return abrechnungspositionen;
        }

        public Einheit setAbrechnungspositionen(
            List<Abrechnungsposition> abrechnungspositionen) {
            this.abrechnungspositionen = abrechnungspositionen;
            return this;
        }

        public IntegerType getZaehlerEinheit() {
            if(zaehlerEinheit == null) {
                zaehlerEinheit = new IntegerType();
            }
            return zaehlerEinheit;
        }

        public Einheit setZaehlerEinheit(IntegerType zaehlerEinheit) {
            this.zaehlerEinheit = zaehlerEinheit;
            return this;
        }

        public Einheit setZaehlerEinheit(int zaehlerEinheit) {
            this.getZaehlerEinheit().setValue(zaehlerEinheit);
            return this;
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty() && ElementUtil.isEmpty(zaehlerEinheit);
        }

        @Override
        public Einheit copy() {
            final Einheit einheit = new Einheit();
            einheit.setZaehlerEinheit(getZaehlerEinheit());
            return einheit;
        }
    }

}


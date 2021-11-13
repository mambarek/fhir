package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.PositiveIntType;

/**
 * created by mmbarek on 12.11.2021.
 */
@Block
public class Abrechnungsposition extends BackboneElement {

    @Child(name = "zaehlerAbrechnungsposition")
    @Extension(url = "zaehlerAbrechnungsposition")
    private PositiveIntType zaehlerAbrechnungsposition;

    @Child(name = "zuAbschlaegeZusatzdaten")
    @Extension(url = "zuAbschlaegeZusatzdaten")
    private ZuAbschlaegeZusatzdaten zuAbschlaegeZusatzdaten;

    @Override
    public Abrechnungsposition copy() {
        Abrechnungsposition pos = new Abrechnungsposition();
        pos.setZaehlerAbrechnungsposition(getZaehlerAbrechnungsposition());
        return pos;
    }

    public PositiveIntType getZaehlerAbrechnungsposition() {
        if(zaehlerAbrechnungsposition == null) {
            zaehlerAbrechnungsposition = new PositiveIntType();
        }

        return zaehlerAbrechnungsposition;
    }

    public void setZaehlerAbrechnungsposition(
        PositiveIntType zaehlerAbrechnungsposition) {
        this.zaehlerAbrechnungsposition = zaehlerAbrechnungsposition;
    }

    public void setZaehlerAbrechnungsposition(int value) {
        getZaehlerAbrechnungsposition().setValue(value);
    }

    public ZuAbschlaegeZusatzdaten getZuAbschlaegeZusatzdaten() {
        if(zuAbschlaegeZusatzdaten == null) {
            zuAbschlaegeZusatzdaten = new ZuAbschlaegeZusatzdaten();
        }
        return zuAbschlaegeZusatzdaten;
    }

    public void setZuAbschlaegeZusatzdaten(
        ZuAbschlaegeZusatzdaten zuAbschlaegeZusatzdaten) {
        this.zuAbschlaegeZusatzdaten = zuAbschlaegeZusatzdaten;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.getZuAbschlaegeZusatzdaten().isEmpty() &&
            this.getZaehlerAbrechnungsposition().isEmpty();
    }
}

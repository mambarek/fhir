package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.DatatypeDef;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import de.gkvsv.fhir.ta7.model.data.Einheit;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.IntegerType;

/**
 * created by mmbarek on 06.11.2021.
 */
//@ca.uhn.fhir.model.api.annotation.Extension(url = "http://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_ZusatzdatenHerstellung")
@DatatypeDef(name="Extension", isSpecialization = true)
@Description("Zu-/Abschläge auf parenterale Zubereitungen, wirtschaftlichen Einzelmengen etc.")
public class ZusatzDatenHerstellung_Extension extends Extension {

    private static final long serialVersionUID = 1L;

    /**
     * positiv int >0 and <100
     */
    @Child(name = "zaehlerHerstellung", min = 1)
    @ca.uhn.fhir.model.api.annotation.Extension(url="http://acme.org/fooParent", definedLocally = false, isModifier = false)
    private IntegerType zaehlerHerstellung;

    @Child(name = "einheit", min = 1)
    @ca.uhn.fhir.model.api.annotation.Extension(url="http://acme.org/fooParent2", definedLocally = false, isModifier = false)
    private List<Einheit> einheiten;

    public IntegerType getZaehlerHerstellung() {
        if(zaehlerHerstellung == null) {
            zaehlerHerstellung = new IntegerType();
        }
        return zaehlerHerstellung;
    }

    public void setZaehlerHerstellung(IntegerType zaehlerHerstellung) {
        this.zaehlerHerstellung = zaehlerHerstellung;
    }

    public void setZaehlerHerstellung(int zaehlerHerstellung) {
        this.getZaehlerHerstellung().setValue(zaehlerHerstellung);
    }

    public List<Einheit> getEinheiten() {
        if(einheiten == null) {
            einheiten = new ArrayList<>();
        }
        return einheiten;
    }

    public void setEinheiten(
        List<Einheit> einheiten) {
        this.einheiten = einheiten;
    }

    @Override
    public boolean isEmpty() {
        return false;//super.isEmpty() && this.getEinheiten().isEmpty() && this.getZaehlerHerstellung().isEmpty();
    }

    @Block
    public static class Einheit extends BackboneElement {

        @Child(name = "zaehlerEinheit")
        @ca.uhn.fhir.model.api.annotation.Extension(url="http://acme.org/fooParent3", definedLocally = false, isModifier = false)
        private IntegerType zaehlerEinheit;

        public IntegerType getZaehlerEinheit() {
            if(zaehlerEinheit == null) {
                zaehlerEinheit = new IntegerType();
            }
            return zaehlerEinheit;
        }

        public void setZaehlerEinheit(IntegerType zaehlerEinheit) {
            this.zaehlerEinheit = zaehlerEinheit;
        }

        public void setZaehlerEinheit(int zaehlerEinheit) {
            this.getZaehlerEinheit().setValue(zaehlerEinheit);
        }

        @Override
        public boolean isEmpty() {
            return false;//super.isEmpty();
        }

        @Override
        public Einheit copy() {
            return new Einheit();
        }
    }
}

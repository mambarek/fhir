package de.gkvsv.fhir.ta7.model.enums;

/**
 * created by mmbarek on 12.11.2021.
 */
public interface IFhirEnum {
    String toCode();
    String getSystem();
    String getDefinition();
    String getDisplay();
}

package de.gkvsv.fhir.ta7.validation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.UnknownCodeSystemWarningValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseOperationOutcome;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.utilities.npm.NpmPackage;
import org.hl7.fhir.utilities.npm.NpmPackage.NpmPackageFolder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * created by mmbarek on 18.11.2021.
 */
@Slf4j
class TA7FhirValidatorTest {

    FhirContext fhirContext = FhirContext.forR4();
    TA7FhirValidator ta7FhirValidator;

    //@BeforeEach
    void setUp() {
        ta7FhirValidator = new TA7FhirValidator(fhirContext);
    }

    @Test
    void testCreateAndInitValidator() {
        assertNotNull(ta7FhirValidator);
    }

    @Test
    void validateRealBundle() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/1.1.0/eRezept-Beispiele-main/TA7_Sammelrechnung_Bundle-modified/FT_V1_TA7_Sammelrechnung_Bundle-modified.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }

    @Test
    void validateRealTa7Bundle() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/ref/ta7Bundle.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }

    @Test
    void validateRealInvoice() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/ref/ta7Rechnung.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }

    @Test
    void testNpm() throws IOException {

        Map<String, IBaseResource> structureDefinitions = new HashMap<>();
        Map<String, IBaseResource> codeSystems = new HashMap<>();
        Map<String, IBaseResource> valueSets = new HashMap<>();

        addPackageDefinitions("packages-test/de.gkvsv.erezeptabrechnungsdaten-1.1.0.tgz", structureDefinitions, codeSystems, valueSets);
        addPackageDefinitions("packages-test/de.abda.erezeptabgabedatenbasis-1.1.0.tgz", structureDefinitions, codeSystems, valueSets);
        addPackageDefinitions("packages-test/de.gematik.erezept-workflow.r4-1.1.1.tgz", structureDefinitions, codeSystems, valueSets);

        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(fhirContext);

        ValidationSupportChain validationSupportChain = new ValidationSupportChain();

        validationSupportChain.addValidationSupport(new PrePopulatedValidationSupport(fhirContext, structureDefinitions, valueSets, codeSystems));
        validationSupportChain.addValidationSupport(new DefaultProfileValidationSupport(fhirContext));
        validationSupportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(fhirContext));
        validationSupportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(fhirContext));
        validationSupportChain.addValidationSupport(new SnapshotGeneratingValidationSupport(fhirContext));
        validationSupportChain.addValidationSupport(new UnknownCodeSystemWarningValidationSupport(fhirContext));

        instanceValidator.setValidationSupport(validationSupportChain);

        FhirValidator val = fhirContext.newValidator();
        val.registerValidatorModule(instanceValidator);

        //Path path = Paths.get("src/test/resources/1.1.0/eRezept-Beispiele-main/TA7_Sammelrechnung_Bundle-modified/FT_V1_TA7_Sammelrechnung_Bundle-modified.xml");
        Path path = Paths.get("src/test/resources/1.1.0/eRezept-Beispiele-main/TA7_Sammelrechnung_Bundle-modified/PZN_Nr3_TA7_Sammelrechnung_Bundle-modified.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);

        final ValidationResult validationResult = val.validateWithResult(resourceXML);
        IBaseOperationOutcome oo = validationResult.toOperationOutcome();

        //log.info("Result:\n{}", fhirContext.newXmlParser().setPrettyPrint(true).encodeResourceToString(oo));
        final boolean valid = validateResource(val, resourceXML);
        assertTrue(valid);
    }

    public boolean validateResource(FhirValidator validator, String resourceXML) {
        log.info("Beginn validation --");

        final ValidationResult validationResult = validator.validateWithResult(resourceXML);

        final Map<Boolean, List<SingleValidationMessage>> messageMap = validationResult.getMessages()
            .stream()
            .collect(Collectors.partitioningBy(m -> "ERROR".equals(m.getSeverity().toString())));

        log.info("Es wurden {} WARNING und INFORMATION gefunden", messageMap.get(false).size());
        messageMap.get(false).forEach(m -> {
            log.warn(" Next issue " + m.getSeverity() + " - " + m.getLocationString() + " - "
                + m.getMessage());
        });
        log.info("Es wurden {} ERROR gefunden", messageMap.get(true).size());
        messageMap.get(true).forEach(m -> {
            log.error(" Next issue " + m.getSeverity() + " - " + m.getLocationString() + " - "
                + m.getMessage());
        });

        return validationResult.isSuccessful();
    }

    public void addPackageDefinitions(String packageName, Map<String, IBaseResource> structureDefinitions,
        Map<String, IBaseResource> codeSystems, Map<String, IBaseResource> valueSets)
        throws IOException {
        try (final InputStream inputStream = new ClassPathResource(packageName).getInputStream()) {
            NpmPackage npmPackage = NpmPackage.fromPackage(inputStream);

            final NpmPackageFolder npmPackageFolder = npmPackage.getFolders().get("package");

            final List<String> structureDefinitionFiles = npmPackageFolder.getTypes()
                .get("StructureDefinition");
            for (String fileName : structureDefinitionFiles) {
                String defJson = new String(npmPackageFolder.getContent().get(fileName));
                StructureDefinition res = fhirContext.newJsonParser()
                    .parseResource(StructureDefinition.class, defJson);
                structureDefinitions.put(res.getUrl(), res);
            }

            final List<String> codeSystemFiles = npmPackageFolder.getTypes().get("CodeSystem");
            for (String fileName : codeSystemFiles) {
                String defJson = new String(npmPackageFolder.getContent().get(fileName));
                CodeSystem res = fhirContext.newJsonParser()
                    .parseResource(CodeSystem.class, defJson);
                codeSystems.put(res.getUrl(), res);
            }

            final List<String> valueSetFiles = npmPackageFolder.getTypes().get("ValueSet");
            for (String fileName : valueSetFiles) {
                String defJson = new String(npmPackageFolder.getContent().get(fileName));
                ValueSet res = fhirContext.newJsonParser().parseResource(ValueSet.class, defJson);
                valueSets.put(res.getUrl(), res);
            }
        }
    }
}

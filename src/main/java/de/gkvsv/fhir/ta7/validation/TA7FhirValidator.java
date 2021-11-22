package de.gkvsv.fhir.ta7.validation;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationOptions;
import ca.uhn.fhir.validation.ValidationResult;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.profiles.TA7Rechnung;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.UnknownCodeSystemWarningValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 18.11.2021.
 */
@Slf4j
@Component
public class TA7FhirValidator {

    private final FhirContext fhirContext;
    private final FhirValidator validator;
    private final IParser parser;

    public TA7FhirValidator(FhirContext fhirContext) {
        this.fhirContext = fhirContext;
        parser = fhirContext.newXmlParser();
        validator = fhirContext.newValidator();
        try {
            initValidator();
        } catch (IOException e) {
            log.error("Error initializing TA7Validator --", e);
        }
    }

    private String readFromInputStream(InputStream inputStream)
        throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
            = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private void initValidator() throws IOException {
        log.info("Initialize validator");
        // Create a PrePopulatedValidationSupport which can be used to load custom definitions.
        PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(fhirContext);

/*
        Arrays.stream(Configuration.govsStructureDefinitions).forEach(fileName -> {
            final InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
            try {
                final String resourceXML = readFromInputStream(resourceAsStream);
                final IBaseResource resource = parser.parseResource(resourceXML);
                prePopulatedSupport.addResource(resource);
                //log.info("-- GKV Structure added to Validator: " + fileName);
            } catch (IOException e) {
                log.error("-- Can not initialize Validator");
                log.error(e.getMessage());
            }
        });
*/
        String filesPath = "src/main/resources/gkvsv";
        //String filesPath = "E:/development/FHIR/xml";
        try (Stream<Path> stream = Files.list(Paths.get(filesPath))) {
            stream.forEach(path -> {
                log.debug("Add GKSV packages. parse file: " + path.toFile());
                try {
                    final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
                    final IBaseResource resource = parser.parseResource(resourceXML);
                    prePopulatedSupport.addResource(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            log.info("GKSV structure definitions added successfully --");
        }

        UnknownCodeSystemWarningValidationSupport unknownCodeSystemWarningValidationSupport =
            new UnknownCodeSystemWarningValidationSupport(fhirContext);
        unknownCodeSystemWarningValidationSupport.setAllowNonExistentCodeSystem(true);
        // Create a validation support chain
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
            prePopulatedSupport,

            new DefaultProfileValidationSupport(fhirContext),
            new SnapshotGeneratingValidationSupport(fhirContext),
            new InMemoryTerminologyServerValidationSupport(fhirContext),
            new CommonCodeSystemsTerminologyService(fhirContext),
            unknownCodeSystemWarningValidationSupport
        );
        log.info("Validation chain created --");

        // Create a FhirInstanceValidator and register it to a validator
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(validationSupportChain);
        validator.registerValidatorModule(instanceValidator);

        /*
         * If you want, you can configure settings on the validator to adjust
         * its behaviour during validation
         */
        instanceValidator.setAnyExtensionsAllowed(true);
        log.info("Initialization finished --");
    }

    public boolean validateResource(IBaseResource resource) {
        log.info("Beginn validation --");

        final ValidationResult validationResult = validator.validateWithResult(resource);

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
}

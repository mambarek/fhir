import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.ClasspathUtil;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import de.gkvsv.fhir.ta7.model.TA7FhirResourcesFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JFileChooser;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.NpmPackageValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Invoice.InvoiceStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

/**
 * created by mmbarek on 07.11.2021.
 */
@Slf4j
public class TA7FhirResourcesTest {

    static Path workingDir;
    static FhirContext fhirContext = FhirContext.forR4();
    static IParser parser;
    static FhirValidator validator;
    static TA7FhirResourcesFactory ta7FhirResourcesFactory = new TA7FhirResourcesFactory();

    @BeforeAll
    public static void init() throws IOException {

        workingDir = Path.of("", "src/test/resources");
        parser = fhirContext.newXmlParser();
        parser.setPrettyPrint(true);

        // Create a PrePopulatedValidationSupport which can be used to load custom definitions.
        PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(fhirContext);

        try (Stream<Path> stream = Files.list(Paths.get("src/main/resources/packages/eabrechnung"))) {
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

            log.info("GKSV packages are added successfully --");
        }

        final DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(
            fhirContext);

        SnapshotGeneratingValidationSupport snapshotGenerator =
            new SnapshotGeneratingValidationSupport(fhirContext);

        // Create a validation support chain
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
            prePopulatedSupport,
            defaultSupport,
            snapshotGenerator,
            new InMemoryTerminologyServerValidationSupport(fhirContext),
            new CommonCodeSystemsTerminologyService(fhirContext)
        );
        log.info("Validation chain created --");

        // Create a FhirInstanceValidator and register it to a validator
        validator = fhirContext.newValidator();
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(validationSupportChain);
        validator.registerValidatorModule(instanceValidator);

        /*
         * If you want, you can configure settings on the validator to adjust
         * its behaviour during validation
         */
        instanceValidator.setAnyExtensionsAllowed(true);
        log.info("Initialization is finish --");
    }

    @Test
    public void testCreateBundle() {
        final Bundle ta7Bundle = ta7FhirResourcesFactory.createTA7Bundle("ARZFHR21001", "00001");
        final String bundleXML = parser.encodeResourceToString(ta7Bundle);
        assertNotNull(bundleXML);
        log.debug(bundleXML);
    }

    @Test
    public void testCreateRechnung() throws IOException {
        // daten laden
        final Invoice rechnung = ta7FhirResourcesFactory.createRechnung(InvoiceStatus.ISSUED, false,
            "give_a_rezept_id", "give_a_beleg_nummer");

        // for test set fixed value for comparing
        rechnung.setId("d6924e8b-54ea-47ae-82a9-2f69f4c59094");

        parser.setPrettyPrint(false);
        final String rechnungXML = parser.encodeResourceToString(rechnung);
        assertNotNull(rechnungXML);
        log.debug(rechnungXML);

        final Path path = workingDir.resolve("ref/ta7Rechnung.xml");
        final String refRechnungXML = Files.readString(path, StandardCharsets.UTF_8);
        log.debug(refRechnungXML);

        assertThat(rechnungXML, CompareMatcher.isSimilarTo(refRechnungXML).ignoreWhitespace());
    }

    @Test
    public void testValidation() {
        final Invoice rechnung = ta7FhirResourcesFactory.createRechnung(InvoiceStatus.ISSUED, false,
            "give_a_rezept_id", "give_a_beleg_nummer");

        log.info("Beginn valiadtion --");
        final ValidationResult validationResult = validator.validateWithResult(rechnung);

        validationResult.getMessages().forEach(m -> {
            System.out.println(" Next issue " + m.getSeverity() + " - " + m.getLocationString() + " - " + m.getMessage());
        });
        //validationResult.isSuccessful();

        /*
         * Note: You can also explicitly declare a profile to validate against
         * using the block below.
         */
        // ValidationResult result = validator.validateWithResult(obs, new ValidationOptions().addProfile("http://myprofile.com"));

    }

    @Test
    public void testLoadPackages() throws IOException {
        try (InputStream fis = ClasspathUtil.loadResourceAsStream("classpath:packages/erezeptabrechnungsdaten.zip");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis)) {
            ZipEntry ze;

            Long MILLS_IN_DAY = 86400000L;

            while ((ze = zis.getNextEntry()) != null) {

                System.out.format("File: %s Size: %d Last Modified %s %n",
                    ze.getName(), ze.getSize(),
                    LocalDate.ofEpochDay(ze.getTime() / MILLS_IN_DAY));
            }
        }
    }
}

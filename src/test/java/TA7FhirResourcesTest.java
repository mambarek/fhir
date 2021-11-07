import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.TA7FhirResourcesFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
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
    static TA7FhirResourcesFactory ta7FhirResourcesFactory = new TA7FhirResourcesFactory();

    @BeforeAll
    public static void init() {
        workingDir = Path.of("", "src/test/resources");
        parser = fhirContext.newXmlParser();
        parser.setPrettyPrint(true);
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
}

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Johan Lund
 * @project LTU-tests
 * @date 2023-05-16
 */
public class TranscriptCreationTest implements TestSetup {

    private static final Logger logger = LoggerFactory.getLogger(TranscriptCreationTest.class);
    //@Disabled
    @Test
    public void testTranscriptCreation() throws InterruptedException {
        //try {
        // Click on the menu "Intyg", which will direct us to Ladok.
        Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[4]/a").click();
        Thread.sleep(2000);
        // Switch to the new window for Ladok
        Selenide.switchTo().window(1);
        // Accept cookies
        Selenide.$x("/html/body/ladok-root/ladok-cookie-banner/div/div/div/div/div/div[2]/button[1]").click();
        Thread.sleep(2000);
        // Click on the "Access through your institution" button on Ladok log in.
        Selenide.$("body > ladok-root > div > main > div > ladok-inloggning > div > div > div > div > div > div > div > ladok-student > div:nth-child(2) > a").click();
        Thread.sleep(2000);
        // Clicks on the search bar, makes the search for Luleå University and thereby the log in is completed.
        SelenideElement searchInput = $("#searchinput");
        searchInput.setValue("Luleå university");
        Selenide.$("#ds-search-list > a > li > div > div.text-truncate.label.primary").click();
        Thread.sleep(2000);
        // Click on the "Hamburger menu" in the top right corner.
        Selenide.$("#sticky-header > ladok-top-meny > div > div > div:nth-child(2) > button > fa-icon > svg").click();
        Thread.sleep(2000);
        // Click on the menu "Transcripts and certificates".
        Selenide.$("#sidomeny-ul > li:nth-child(3) > ladok-behorighetsstyrd-nav-link > a").click();
        Thread.sleep(2000);
        // Click on the button "Create" to begin the creation of a transcript.
        Selenide.$("#main > div > ladok-intyg > ladok-skapa-intyg-knapp > div > button").click();
        Thread.sleep(2000);
        // Clicks on the dropdown menu and pick the correct option by indexing the alternatives.
        SelenideElement dropDown = $("#intygstyp");
        dropDown.selectOption(2);
        Thread.sleep(2000);
        Selenide.$("#main > div > ladok-skapa-intyg > ladok-card > div > div > ladok-card-body > div:nth-child(3) > div > form > div:nth-child(3) > div > ladok-skapa-intyg-knapprad > div > button.btn.btn-ladok-brand.text-nowrap.me-lg-3").click();
        Thread.sleep(2000);

        String expectedTranscriptStatus = "Official transcript of records";
        String actualTranscriptStatus = Selenide.$("body").getText();
        assertEquals(expectedTranscriptStatus, actualTranscriptStatus);

        boolean testPassed = actualTranscriptStatus.contains(expectedTranscriptStatus);
        reportTestResultTranscript(testPassed);







        //}
    }
    private void reportTestResultTranscript(boolean testPassed) {
        if (testPassed) {
            logger.info("Test passed successfully.");
        } else {
            logger.error("Test failed.");
        }
    }
}
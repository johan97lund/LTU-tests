import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
/**
 * @author Johan Lund, Jesper Truedsson, Mattias Fridsén
 * @project LTU-tests
 * @date 2023-05-10
 */

public class TranscriptDownloadTest implements TestSetup {

    private static final Logger logger = LoggerFactory.getLogger(TranscriptDownloadTest.class);

    @Test
    public void testTranscriptCreation() throws InterruptedException {
        try {
            Configuration.downloadsFolder = "/Users/johanlund/IdeaProjects/LTU-tests/LTUdownloads";
            Configuration.reportsFolder = "/Users/johanlund/IdeaProjects/LTU-tests/LTUdownloads";

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

            String dateTimeText = $("#main > div > ladok-intyg > ladok-listning-av-skapade-intyg > div > div > ladok-accordion > div > ladok-list-kort:nth-child(1) > div > div.card-header > div > div:nth-child(2)").getText();
            // Extract the date portion using regular expressions
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher matcher = pattern.matcher(dateTimeText);
            String dateString = "";
            if (matcher.find()) {
                dateString = matcher.group();
            }

            // Parse the obtained text into a date object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate actualDate = LocalDate.parse(dateString, formatter);

            // Get the current date
            LocalDate expectedDate = LocalDate.now();

            // Compare the parsed date with the expected value
            if (actualDate.equals(expectedDate)) {
                System.out.println("Transcript created with correct date.");
            } else {
                System.out.println("Transcript date does not match the expected value.");
            }

            // Find and click the element for downloading the transcript
            $("#main > div > ladok-intyg > ladok-listning-av-skapade-intyg > div > div > ladok-accordion > div > ladok-list-kort:nth-child(1) > div > div.card-header > div > div.ladok-list-kort-header-rubrik > a").click();

            File downloadedFile = new File("Intyg.pdf");
            Thread.sleep(2000);

            if (downloadedFile.exists()) {
                System.out.println("File download failed.");
            } else {
                System.out.println("File has been downloaded successfully.");
            }

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException occurred: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

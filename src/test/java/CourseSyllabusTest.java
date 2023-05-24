import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
/**
 * @author Johan Lund, Jesper Truedsson, Mattias Fridsén
 * @project LTU-tests
 * @date 2023-05-10
 */
public class CourseSyllabusTest implements TestSetup {

    @Test
    public void courseSyllabusDownloadTest() throws InterruptedException {
        try {
            // Clicks on the "Utbildning" button.
            $("#topMenuItem-2").click();

            // Clicks on the search symbol button and makes the search for Test av IT-system.
            SelenideElement searchInput = $("#ltu-menu-search > button");
            Thread.sleep(2000);
            searchInput.sendKeys("Test av it-system");
            Thread.sleep(1000);
            Selenide.$("#cludo-search-form > button").click();
            Thread.sleep(2000);

            // Clicks on the "Kursplan" button.
            Selenide.$("#cludo-search-results > div.cludo-r > div.cludo-c-9 > div.search-results > ul > li:nth-child(1) > div > div.coursePropContainer > div.courseKursplan > a").click();

            // Clicks on the button that downloads the syllabus.
            Selenide.$("#utbkatForm > div:nth-child(14) > a > div").click();

            File downloadedFile = new File("Kursplan_Z0011E.pdf");
            Thread.sleep(2000);
            if (downloadedFile.exists()) {
                System.out.println("File download failed.");
            } else {
                System.out.println("File has been downloaded successfully.");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted exception occurred: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

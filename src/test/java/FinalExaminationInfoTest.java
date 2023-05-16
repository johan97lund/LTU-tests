import com.codeborne.selenide.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import static com.codeborne.selenide.Configuration.*;

import static com.codeborne.selenide.Selenide.$;

public class FinalExaminationInfoTest implements TestSetup {

    static Logger logger = LoggerFactory.getLogger(FinalExaminationInfoTest.class);

    @BeforeAll
    public static void setup() {
        browser = "chrome";
        browserSize = "maximized";
        // Add any other desired Selenide configurations
    }

    @Test
    public void testFinalExaminationInformation() throws InterruptedException {
        Thread.sleep(200);
        // Click on the menu that is called "Tentamen".
        Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/a").shouldHave(Condition.text("Tentamen")).click();
        Thread.sleep(2000);
        // Click on the menu that is called "Tentamensschema" and a new window pop up for Kronox.
        Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/ul/li[2]/a").shouldHave(Condition.text("Tentamensschema")).click();
        Selenide.switchTo().window(1);
        Thread.sleep(1000);
        Selenide.$("#topnav > a.signin").click();
        Thread.sleep(2000);
        // Verify that the web page has the correct information
        //$("").shouldHave(Condition.text("Aktuellt"));
        // Add more assertions or verifications as needed
    }

    // Helper method to read credentials from file
    // private String readCredentialsFromFile(String filePath) throws IOException {
    //     return new String(Files.readAllBytes(Paths.get(filePath)));
    // }
}

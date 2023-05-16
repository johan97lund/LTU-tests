import com.codeborne.selenide.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;

public class FinalExamTest {

    static Logger logger = LoggerFactory.getLogger(FinalExamTest.class);

    @BeforeAll
    public static void setup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "maximized";
        // Add any other desired Selenide configurations
    }

    @BeforeEach
    public void beforeEachTest() throws IOException, InterruptedException {
        logger.info("Logback initialized");
        logger.info("Starting the test");

        String password = null;
        String username = null;
        try {
            logger.info("Reading email and password from config.json");
            // Read the email and password from config.json
            Gson gson = new Gson();
            JsonElement config = gson.fromJson(new FileReader("config.json"), JsonElement.class);
            username = config.getAsJsonObject().get("username").getAsString();
            password = config.getAsJsonObject().get("password").getAsString();
            logger.info("Read successfully!");
        } catch (IOException e) {
            // Handle any exceptions that might occur while reading the file
            logger.error("Could not read config.json", e);
            System.exit(1);
        }

        // Open the LTU.Se page
        Selenide.open("https://www.ltu.se/");
        Thread.sleep(5000);
        // Allowing cookies.
        Selenide.$("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
        Thread.sleep(5000);
        // Navigate to the Student tab.
        Selenide.$("#main-nav > div.ltu-nav-right > div > a:nth-child(1)").click();
        Thread.sleep(5000);
        // Navigate to the My LTU tab.
        Selenide.$("#maincontent > div:nth-child(1) > div > div:nth-child(1) > div > div > div.card-footer > a").click();
        Thread.sleep(5000);
        // Fill in the username from the config.json file.
        SelenideElement userID = $("#username");
        userID.setValue(username);
        Thread.sleep(1000);
        // Fill in the password from the config.json file.
        SelenideElement passID = $("#password");
        passID.setValue(password);
        Thread.sleep(1000);

        // Click the "Log in" button.
        Selenide.$("#fm1 > section.row.btn-row > input.btn-submit\n").click();
    }

    @Test
    public void testFinalExaminationInformation() throws InterruptedException {
        Thread.sleep(5000);
        // Verify that the web page has the correct information
        $("").shouldHave(Condition.text("Aktuellt"));
        // Add more assertions or verifications as needed
    }

    // Helper method to read credentials from file
    // private String readCredentialsFromFile(String filePath) throws IOException {
    //     return new String(Files.readAllBytes(Paths.get(filePath)));
    // }
}

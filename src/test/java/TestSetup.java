import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;


/**
 * @author Johan Lund
 * @project LTU-tests
 * @date 2023-05-16
 */
public interface TestSetup {

                    static Logger logger = LoggerFactory.getLogger(TestSetup.class);

    @BeforeEach
    default void beforeEachTest() throws IOException, InterruptedException {

        String password = null;
        String username = null;

        try {
            logger.info("Reading username and password from config.json");
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
        Thread.sleep(2000);
        // Allowing cookies.
        $("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
        Thread.sleep(2000);
        // Navigate to the Student tab.
        $("#main-nav > div.ltu-nav-right > div > a:nth-child(1)").click();
        Thread.sleep(2000);
        // Click on the log in button.
        $("#maincontent > div:nth-child(1) > div > div:nth-child(1) > div > div > div.card-footer > a").click();
        Thread.sleep(2000);
        // Fill in the username from the config.json file.
        SelenideElement userID = $("#username");
        userID.setValue(username);
        Thread.sleep(1000);
        // Fill in the password from the config.json file.
        SelenideElement passID = $("#password");
        passID.setValue(password);
        Thread.sleep(1000);
        // Click the "Log in" button.
        $("#fm1 > section.row.btn-row > input.btn-submit\n").click();
        Thread.sleep(2000);


    }

}

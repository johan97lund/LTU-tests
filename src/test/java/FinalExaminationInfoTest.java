import com.codeborne.selenide.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.*;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinalExaminationInfoTest implements TestSetup {

    private static final Logger logger = LoggerFactory.getLogger(FinalExaminationInfoTest.class);


    @Test
    public void testFinalExaminationInformation() throws InterruptedException, IOException {
        try {
            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Click on the menu that is called "Tentamen".
            Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/a")
                    .shouldHave(Condition.text("Tentamen")).click();

            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Click on the menu that is called "Tentamensschema" and a new window pops up for Kronox.
            Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/ul/li[2]/a")
                    .shouldHave(Condition.text("Tentamensschema")).click();

            // Switch to the new window for Kronox
            Selenide.switchTo().window(1);

            // Add a delay for 1 second
            Thread.sleep(1000);

            // Click on the Log in button in the top right corner.
            Selenide.$("#topnav > a.signin").click();

            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Log in to the Kronox website
            loginKronoxWebsite();

            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Click on the login button element
            Selenide.$x("//*[@id=\"login_button\"]").click();

            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Click on the "Aktivitetsanmälan" menu button element
            Selenide.$("body > div.main > div.header > div > div > ul > li:nth-child(9) > a > em > b").click();

            // Add a delay for 2 seconds
            Thread.sleep(2000);

            // Check if the expected text is present on the page
            String expectedText = "Datum: 2023-05-30";
            String actualText = Selenide.$("body").getText();
            assertTrue(actualText.contains(expectedText));


            boolean testPassed = actualText.contains(expectedText);
            reportTestResult(testPassed);


            // Add a delay for 1 seconds
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle InterruptedException
            logger.error("InterruptedException occurred.", e);
            throw e;
        }
    }

    private void reportTestResult(boolean testPassed) {
        if (testPassed) {
            logger.info("Test passed successfully.");
        } else {
            logger.error("Test failed.");
        }
    }

    private void loginKronoxWebsite() throws InterruptedException {
        String passwordKronox = null;
        String usernameKronox = null;

        try {
            logger.info("Reading username and password from kronoxConfig.json");

            // Read the email and password from kronoxConfig.json
            Gson gson = new Gson();
            JsonElement config = gson.fromJson(new FileReader("kronoxConfig.json"), JsonElement.class);
            usernameKronox = config.getAsJsonObject().get("usernameKronox").getAsString();
            passwordKronox = config.getAsJsonObject().get("passwordKronox").getAsString();

            logger.info("Read successfully!");
        } catch (IOException e) {
            // Handle any exceptions that might occur while reading the file
            logger.error("Could not read kronoxConfig.json", e);
            System.exit(1);
        }



        fillInKronoxUsernameAndPassword(usernameKronox, passwordKronox);
    }

    private void fillInKronoxUsernameAndPassword(String usernameKronox, String passwordKronox) throws InterruptedException {

        try {
        // Fill in the password field
        SelenideElement passID = $("#login_password");
        passID.setValue(passwordKronox);
        Thread.sleep(1000);

        // Fill in the username field
        SelenideElement userID = $("#login_username");
        userID.setValue(usernameKronox);
        Thread.sleep(1000);
    } catch (InterruptedException e) {
            // Handle InterruptedException
            logger.error("InterruptedException occurred while filling in Kronox username and password.", e);
            throw e;
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            logger.error("An error occurred while filling in Kronox username and password.", e);
            throw e;
        }
    }
}

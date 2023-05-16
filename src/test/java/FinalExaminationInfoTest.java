import com.codeborne.selenide.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinalExaminationInfoTest implements TestSetup {

    @Test
    public void testFinalExaminationInformation() throws InterruptedException, IOException {

        Thread.sleep(2000);
        // Click on the menu that is called "Tentamen".
        Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/a").shouldHave(Condition.text("Tentamen")).click();
        Thread.sleep(2000);
        // Click on the menu that is called "Tentamensschema" and a new window pop up for Kronox.
        Selenide.$x("/html/body/div/div[1]/div[4]/div[1]/div[2]/div[2]/ul[2]/li[3]/ul/li[2]/a").shouldHave(Condition.text("Tentamensschema")).click();
        Selenide.switchTo().window(1);
        Thread.sleep(1000);
        // Click on the Log in button in the top right corner.
        Selenide.$("#topnav > a.signin").click();
        Thread.sleep(2000);
        loginKronoxWebsite();
        Thread.sleep(2000);

        Selenide.$x("//*[@id=\"login_button\"]").click();
        Thread.sleep(2000);
        Selenide.$("body > div.main > div.header > div > div > ul > li:nth-child(9) > a > em > b").click();
        Thread.sleep(2000);

        String expectedText = "Datum: 2023-05-30";
        String actualText = Selenide.$("body").getText();
        assertTrue(actualText.contains(expectedText));

        Thread.sleep(10000);


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
        SelenideElement passID = $("#login_password");
        passID.setValue(passwordKronox);
        Thread.sleep(1000);

        SelenideElement userID = $("#login_username");
        userID.setValue(usernameKronox);
        Thread.sleep(1000);

    }
}

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
        Thread.sleep(2000000000);

        loginWithKronoxCredentials();


        }


        // Verify that the web page has the correct information
        //$("").shouldHave(Condition.text("Aktuellt"));
        // Add more assertions or verifications as needed


    private void loginWithKronoxCredentials() throws InterruptedException {
        String kronoxUsername = "kronoxUsername";
        String kronoxPassword = "kronoxPassword";

        loginToWebsite(kronoxUsername, kronoxPassword);
    }

    private void loginToWebsite(String kronoxUsername, String kronoxPassword) throws InterruptedException {
        SelenideElement userID = $("#username");
        userID.setValue(kronoxUsername);
        Thread.sleep(1000);

        SelenideElement passID = $("#password");
        passID.setValue(kronoxPassword);
        Thread.sleep(1000);
    }

    // Helper method to read credentials from file
    // private String readCredentialsFromFile(String filePath) throws IOException {
    //     return new String(Files.readAllBytes(Paths.get(filePath)));
    // }
}

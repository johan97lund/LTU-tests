import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public interface TestSetup {

    static Logger logger = LoggerFactory.getLogger(TestSetup.class);

    @BeforeAll
    static void beforeAllTest() {
        Configuration.browserSize = null; // Disable Selenide's browserSize setting
        Configuration.browser = "chrome";

        // Added Selenium configurations to make the web browser maximized.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Configuration.browserCapabilities = options;
    }

    @BeforeEach
    default void beforeEachTest() throws IOException, InterruptedException {
        loginToWebsite();
    }

    default void loginToWebsite() throws IOException, InterruptedException {
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

        openLTUSePage();
        allowCookies();
        navigateToStudentTab();
        clickLoginButton();
        fillInUsernameAndPassword(username, password);
        clickLogIn();
    }

    private void openLTUSePage() throws InterruptedException {
        Selenide.open("https://www.ltu.se/");
        Thread.sleep(2000);
    }

    private void allowCookies() throws InterruptedException {
        $("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
        Thread.sleep(2000);
    }

    private void navigateToStudentTab() throws InterruptedException {
        $("#main-nav > div.ltu-nav-right > div > a:nth-child(1)").click();
        Thread.sleep(2000);
    }

    private void clickLoginButton() throws InterruptedException {
        $x("/html/body/main/div/div/div[1]/div/div[1]/div/div/div[3]/a").click();
        Thread.sleep(2000);
    }

    private void fillInUsernameAndPassword(String username, String password) throws InterruptedException {
        SelenideElement userID = $("#username");
        userID.setValue(username);
        Thread.sleep(1000);

        SelenideElement passID = $("#password");
        passID.setValue(password);
        Thread.sleep(1000);
    }

    private void clickLogIn() throws InterruptedException {
        $("#fm1 > section.row.btn-row > input.btn-submit").click();
        Thread.sleep(2000);
    }
}
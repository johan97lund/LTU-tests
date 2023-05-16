import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.browserSize;

/**
 * @author Johan Lund
 * @project LTU-tests
 * @date 2023-05-16
 */
public class MainTestClass {

    @BeforeAll
    public static void setup() {
        browserSize = "maximize";
        browser = "chrome";

    }

    @Test
    public void runFinalExaminationInfoTest() {
        JUnitCore.getClass(FinalExaminationInfoTest.class);
    }
}

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @Feature(value = "Поиск информации о фреймворке Allure")
    @Step("Input <Allure report> at Google search")
    protected void openPage() {
        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));
        input.sendKeys("Allure report");
        Allure.addAttachment("ResultSearch", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Attachment(value = "Page-screenshot", type = "image/jpeg")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Проверка, что количества найденных статей с текстом 'Allure' больше 5")
    protected void countContext() throws IOException {
        List<WebElement> count;
        count = driver.findElements(By.xpath("//*[text()=\"Allure\"]"));
        Assertions.assertTrue(5 > count.size());
    }

    @AfterAll
    public static void setDown() {
        if (driver != null) {
            driver.quit();
        }

    }
}
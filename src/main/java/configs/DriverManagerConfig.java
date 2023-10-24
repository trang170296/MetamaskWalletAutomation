package configs;

import constants.ConstantVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static constants.ConstantVariables.*;

public class DriverManagerConfig {
    private static WebDriver driver;
    private static WebDriverWait wait;
    public static void launch(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (ConstantVariables.HEADLESS == true) {
                options.addArguments("--headless=new");
                options.addArguments("window-size=1800,900");
            }
            if (ConstantVariables.METAMASKEXT == true) {
                options.addExtensions(new File("./src/main/resources/metamask-chrome-10.25.0.crx"));
            }
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else {
            throw new IllegalArgumentException(browserName + " is not support!!");
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
    }

    public static WebDriver getDriver() {
        return driver;
    }
}



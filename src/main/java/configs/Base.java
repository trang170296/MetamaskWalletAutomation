package configs;

import constants.ConstantVariables;
import helpers.PropertiesHelpers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.xml.XmlTest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Base {
    private final static long EXPLICIT_TIMEOUT = ConstantVariables.EXPLICIT_TIMEOUT;
    private final static long STEP_TIME = ConstantVariables.STEP_TIME;
    private final static long PAGE_LOAD_TIMEOUT = ConstantVariables.PAGE_LOAD_TIMEOUT;

    static {
        PropertiesHelpers.loadAllFiles();
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logConsole(Object message) {
        System.out.println(message);
    }

    public static WebElement getWebElement(By by) {
        return DriverManagerConfig.getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManagerConfig.getDriver().findElements(by);
    }


    public static Boolean checkElementExist(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        List<WebElement> listElement = getWebElements(by);
        if (listElement.size() > 0) {
            return true;
        }

        return false;
    }

    public static void openURL(String url) {
        DriverManagerConfig.getDriver().get(url);
        sleep(STEP_TIME);
        waitForPageLoaded();
    }

    public static void clickElement(By by) {
        waitForPageLoaded();
        waitForElementClickable(by);
        sleep(STEP_TIME);
        getWebElement(by).click();
    }

    public static void clickElement(By by, int timeout) {
        waitForPageLoaded();
        waitForElementClickable(by, timeout);
        sleep(STEP_TIME);
        getWebElement(by).click();
    }

    public static void setText(By by, String value) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value);
    }

    public static void setTextAndKey(By by, String value, Keys key) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value, key);
    }

    public static String getElementText(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        String text = getWebElement(by).getText();
        return text; // Trả về một giá trị kiểu String
    }

    public static void clearText(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).clear();
    }

    // Wait for Element

    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(),
                    Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(5000));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(), Duration.ofSeconds(timeOut),
                    Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(),
                    Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(), Duration.ofSeconds(timeOut),
                    Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("Element not exist. " + by.toString());

        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(),
                    Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(), Duration.ofSeconds(timeOut),
                    Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    // Vài hàm bổ trợ nâng cao hơn

    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToElement(WebElement element, String type) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();
        js.executeScript("arguments[0].scrollIntoView(" + type + ");", element);
    }

    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    public static void executeJavascript(String script) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();
        js.executeScript(script);
    }

    public static boolean moveToElement(By by) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.moveToElement(getWebElement(by)).release(getWebElement(by)).build().perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param by truyền vào đối tượng element dạng By
     * @return Tô màu viền đỏ cho Element trên website
     */

    public static WebElement highLightElement(By by) {
        // Tô màu border ngoài chính element chỉ định - màu đỏ (có thể đổi màu khác)
        if (DriverManagerConfig.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) DriverManagerConfig.getDriver())
                    .executeScript("arguments[0].style.border='3px solid red'", getWebElement(by));
            sleep(1);
        }
        return getWebElement(by);
    }

    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            // action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement))
                    .release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(DriverManagerConfig.getDriver());
            // Tính từ vị trí click chuột đầu tiên (clickAndHold)
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressENTER() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressESC() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressF11() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for Page Chờ đợi trang tải xong (Javascript) với thời gian thiết lập sẵn
     */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(), Duration.ofSeconds(10000),
                Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) DriverManagerConfig.getDriver())
                .executeScript("return document.readyState").toString().equals("complete");

        // Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        // Wait Javascript until it is Ready!
        if (!jsReady) {
            // Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load (Javascript). (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }
    }

    /**
     * Chờ đợi JQuery tải xong với thời gian thiết lập sẵn
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManagerConfig.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT),
                Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManagerConfig.getDriver();

        // Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) DriverManagerConfig.getDriver())
                    .executeScript("return jQuery.active") == 0);
        };

        // Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

        // Wait JQuery until it is Ready!
        if (!jqueryReady) {
            try {
                // Wait for jQuery to load
                wait.until(jQueryLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for JQuery load. (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }
    }

    public static void changeTab(int tabNumber){
        List<String> allHandles =new ArrayList<>(DriverManagerConfig.getDriver().getWindowHandles());
        for (String handle:allHandles
             ) {
            System.out.println(handle);
        }
        DriverManagerConfig.getDriver().switchTo().window(allHandles.get(tabNumber));

    }

}

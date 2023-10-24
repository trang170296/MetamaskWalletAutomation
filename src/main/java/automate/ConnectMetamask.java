package automate;
import configs.Base;
import configs.DriverManagerConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import static configs.DriverManagerConfig.*;


public class ConnectMetamask extends Base {
    static String[] privateKey = {"useful", "bring", "drop" ,"alcohol" ,"chimney" ,"annual", "insane", "muffin", "three", "glue", "chalk", "ocean"};
    private static WebDriverWait wait;
    public void connectMetamask() throws InterruptedException {
        launch("chrome");
        Thread.sleep(20000);
        Base.changeTab(1);
        Base.waitForPageLoaded();


        By importWalletBtn = By.cssSelector("button[data-testid=onboarding-import-wallet]");
        clickElement(importWalletBtn);

        By agreeBtn = By.cssSelector("button[data-testid=metametrics-i-agree]");
        clickElement(agreeBtn);

        for (int i=0; i< privateKey.length; i++){
            DriverManagerConfig.getDriver().findElement((By.cssSelector(String.format("input[data-testid=import-srp__srp-word-%s]",String.valueOf(i))))).sendKeys(privateKey[i]);
        }

        By importConfirmBtn = By.cssSelector("button[data-testid=import-srp-confirm]");
        clickElement(importConfirmBtn);

        By newPWTextbox = By.cssSelector("input[data-testid=create-password-new]");
        setText(newPWTextbox,"Hoangabc");

        By newPWConfirmTextbox = By.cssSelector("input[data-testid=create-password-confirm]");
        setText(newPWConfirmTextbox,"Hoangabc");

        executeJavascript("document.querySelector('.check-box').click();");

        By createPWImportBtn = By.cssSelector("button[data-testid=create-password-import]");
        clickElement(createPWImportBtn);

        By completeDoneBtn = By.cssSelector("button[data-testid=onboarding-complete-done]");
        clickElement(completeDoneBtn);

        By pinExtensionNextBtn = By.cssSelector("button[data-testid=pin-extension-next]");
        clickElement(pinExtensionNextBtn);

        By pinExtensionDoneBtn = By.cssSelector("button[data-testid=pin-extension-done]");
        clickElement(pinExtensionDoneBtn);
    }

    public static void main(String[] args) throws InterruptedException {
        ConnectMetamask connectMetamask = new ConnectMetamask();
        connectMetamask.connectMetamask();
    }

}

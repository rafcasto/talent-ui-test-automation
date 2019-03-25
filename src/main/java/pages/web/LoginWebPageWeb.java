package pages.web;


import dto.UserDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.WebBasePage;
import pages.ILoginPage;

import java.util.ArrayList;
import java.util.List;

public class LoginWebPageWeb extends WebBasePage implements ILoginPage {

    private static String userNameId = "email";
    private static String passwordId = "password";
    private static String loginBtnId = "login";
    private static By helpBlock = By.className("help-block");
    private static By warringMessage = By.className("alert-warning");
    private static By downloadApp = By.id("downloadApp");
    private static By appBox = By.className("app-box");
    private static By learnMoreButton = By.id("learnMore");
    private static By corporateSideMenu = By.id("sideBarMenuLinkhome");

    public LoginWebPageWeb(WebDriver driver){
        super(driver);
    }

    @Override
    public void logInWith(UserDTO user) {
        if(!user.getUserName().isEmpty())
        sendKeys(By.id(userNameId),user.getUserName());
        if(!user.getPassword().isEmpty())
        sendKeys(By.id(passwordId),user.getPassword());
        driver.findElement(By.id(loginBtnId)).click();
    }

    @Override
    public void clickOnForgotPassword() {

    }

    @Override
    public void clickOnDownloadTheAppButton() {
        driver.findElement(downloadApp).click();
    }

    @Override
    public String getCurrentURl() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return getURl();
    }

    @Override
    public void clickOnLearnMoreButton() {
        driver.findElement(learnMoreButton).click();

        //waitForElement(corporateSideMenu);
    }

    @Override
    public List<String> getElementsOnDownloadAppModal() {
        List<String> linksOnModalWindown = new ArrayList<>();
        List<WebElement> elementsOnModalWindown = new ArrayList<>();
        waitForElement(appBox);
        elementsOnModalWindown = driver.findElements(appBox);
        for(WebElement link: elementsOnModalWindown){
            linksOnModalWindown.add((link.getText()));
        }

        return linksOnModalWindown;
    }

    @Override
    public String getLoginText() {
        return null;
    }

    @Override
    public String getWarningMessage() {
        waitForElement(warringMessage);
        return driver.findElement(warringMessage).getText();
    }

    @Override
    public List<String> getListOfDisplayedErrors() {
         List<String> errorBlock = new ArrayList<>();

         List<WebElement> helperBlocks
                 = driver.findElements(helpBlock);

        for (WebElement helperBlock:helperBlocks) {
            errorBlock.add(helperBlock.getText());
        }
        return errorBlock;
    }


}

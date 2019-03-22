package pages.web;


import dto.UserDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.BasePage;
import pages.IDashboardPage;

import java.util.ArrayList;
import java.util.List;

public class DashboardWebPage extends BasePage implements IDashboardPage {

    private static String userNameId = "email";
    private static String passwordId = "password";
    private static String loginBtnId = "login";
    private static By helpBlock = By.className("help-block");
    private static By warringMessage = By.className("alert-warning");
    public DashboardWebPage(WebDriver driver){
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

    }

    @Override
    public void clickOnLearnMoreButton() {

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

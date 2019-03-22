package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public WebDriver driver;
   public BasePage(WebDriver driver){
        this.driver = driver;
    }

   public void sendKeys(By locator,String text){
       Actions actions = new Actions(driver);
       WebElement textElement = driver.findElement(locator);
       actions
               .sendKeys(textElement,text).build().perform();
   }


   public void waitForElement(By locator){
       WebDriverWait wait = new WebDriverWait(driver,30 );
       wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
   }
}

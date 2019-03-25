package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WebBasePage {
    public WebDriver driver;
   public WebBasePage(WebDriver driver){
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

   public String getURl(){

       String currentUrl = null;
       try{
           currentUrl = driver.getCurrentUrl();
       }catch (TimeoutException ex){
           driver.navigate().refresh();
           currentUrl = driver.getCurrentUrl();
       }
       return  currentUrl;
   }
}

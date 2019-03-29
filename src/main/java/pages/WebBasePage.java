package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WebBasePage {
    public WebDriver driver;
   public WebBasePage(WebDriver driver){
        this.driver = driver;
    }

   public void sendKeys(By locator,String text){


       WebElement textElement = driver.findElement(locator);
       privateSendText(textElement,text);
   }

   public void sendKeys(WebElement element,String text){
       privateSendText(element,text);
   }

   private void privateSendText(WebElement element,String text){
       Actions actions = new Actions(driver);
       actions
               .sendKeys(element,text).build().perform();
   }


   public void waitForElement(By locator){
       try{
         privateWaitForElement(locator);
           }

       catch (TimeoutException ex){
        privateWaitForElement(locator);
       }

   }

   private void privateWaitForElement(By locator){
       ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
       boolean moreThanOneTab = tabs.size() > 1;
       if(moreThanOneTab){
           driver.navigate().refresh();
           driver.switchTo().window(tabs.get(1));
       }
       WebDriverWait wait = new WebDriverWait(driver,30 );
       wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
   }

   public void waitForElementToBeClick(By locator){
       WebDriverWait wait = new WebDriverWait(driver,30 );
       waitForElement(locator);
       wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
   }

   public void seclectFromDropdown(By locator,String value){
       waitForElement(locator);
       //driver.findElement(createNewDropdown).click();
       WebElement dropdownCreateFile = driver.findElement(locator);
       Select createFile = new Select(dropdownCreateFile);

       try{
           createFile.selectByValue(value);
       }catch (Exception ex){
           //log exception
       }

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

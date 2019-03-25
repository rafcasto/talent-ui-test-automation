package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.IDashboardPage;
import pages.WebBasePage;

import java.util.ArrayList;
import java.util.List;

public class DashboardWebPage extends WebBasePage implements IDashboardPage {

    private static By menuFeatureOptionsContainer =  By.className("carousel-indicators");
    private static By sideAdminPanels = By.className("talent-home-sidebar-panels");
    private static By sideAdminHeaders = By.className("talent-home-panel-heading");
    public DashboardWebPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> getListOfAvailableFeatures() {
        List<String> listOfOptionsAsStrings = new ArrayList<>();

        waitForElement(menuFeatureOptionsContainer);
        List<WebElement> listOfOptions = driver.findElement(menuFeatureOptionsContainer).findElements(By.tagName("li"));
        for(WebElement li:listOfOptions){
            listOfOptionsAsStrings.add(li.getText());
        }
        return listOfOptionsAsStrings;
    }

    @Override
    public List<String> getHeaderOnAdminSidePanels() {
        List<String> listOfHeaders = new ArrayList<>();

        waitForElement(sideAdminPanels);
        List<WebElement> sideMenus = driver.findElements(sideAdminPanels);
        for(WebElement sideMenu:sideMenus){
            List<WebElement> headers = sideMenu.findElements(sideAdminHeaders);
            for(WebElement header:headers){
                listOfHeaders.add(header.getText());
            }
        }


        return listOfHeaders;
    }

    @Override
    public void navigateToMyVideosAndFiels() {

    }

    @Override
    public void createAndShareNew(String file) {

    }
}

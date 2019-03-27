package pages.web;

import dto.NotificationDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.IDashboardPage;
import pages.WebBasePage;

import java.util.ArrayList;
import java.util.List;

public class DashboardWebPage extends WebBasePage implements IDashboardPage {
    private static int NAVIGATIONMENU = 1;
    private static int BELLICON = 4;
    private static By menuFeatureOptionsContainer =  By.className("carousel-indicators");
    private static By sideAdminPanels = By.className("talent-home-sidebar-panels");
    private static By sideAdminHeaders = By.className("talent-home-panel-heading");
    private static By myVideosAndFiles = By.linkText("My Videos & Files");
    private static By createNewDropdown = By.id("createNewDropdown");
    private static By notecodable = By.className("note-editable");
    private static  By shareButton = By.id("shareDocument");
    private static  By shareNote = By.id("shareNote");
    private static By shareAndNotifyButton = By.id("share");
    private static By userMenu = By.id("user-menu");
    private static By logOut = By.id("logout");
    private static By closeModal = By.linkText("Close");
    private static  By notificationContainer = By.id("notification-menu");
    private static By notificationInfo = By.className("notification-info");
    private static By navigationContainer =            By.className("right-nav-bottom");
    private static By saveButton = By.xpath("//button[text()='Save']");
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
        waitForElement(myVideosAndFiles);
        driver.findElement(myVideosAndFiles).click();
    }

    @Override
    public void createNewDocument(String file) {
      seclectFromDropdown(createNewDropdown,file);
    }

    @Override
    public void writeAndShareDocument(String documentBody) {
      sendKeys(notecodable,documentBody);
            waitForElement(saveButton);

       driver.findElement(saveButton).click();
         waitForElement(shareButton);
      driver.findElement(shareButton).click();
    }

    @Override
    public void shareWithModalBox(String role,String message) {


        WebElement shareWIthSearchBox =
                driver.findElement(By.id("s2id_shareWith")).
                        findElement(By.className("select2-choices")).
                        findElement(By.className("select2-search-field"));

        sendKeys(shareWIthSearchBox,role);
        driver.findElement(By.className("select2-result-sub")).click();
        sendKeys(shareNote,message);
        driver.findElement(shareAndNotifyButton).click();
        driver.findElement(closeModal).click();
    }

    @Override
    public void logOut() {
        waitForElementToBeClick(By.className("bootstrap-growl"));
        driver.findElement(userMenu).click();
        driver.findElement(logOut).click();
    }

    @Override
    public int getNumberOfNotifications() {
        waitForElement(notificationContainer);
        WebElement notificationBell = driver.findElement(notificationContainer);
        String notificationNumber =  notificationBell.findElement(By.className("notification-counter")).getText();
        if(notificationNumber == null){
            return 0;
        }else{
            return Integer.valueOf(notificationNumber);
        }



    }

    @Override
    public List<NotificationDto> getListOfNotifications() {
        List<NotificationDto> notifications = new ArrayList<>();
        waitForElement(navigationContainer);
        WebElement bellIcon =       driver.findElements(navigationContainer).get(NAVIGATIONMENU).findElements(By.className("small-link")).get(BELLICON);
       bellIcon.click();

       List<WebElement> listOfElements = bellIcon.findElements(notificationInfo);
        for (WebElement notification:listOfElements
             ) {
            NotificationDto notificationDTO = new NotificationDto();
            notificationDTO.setSender(notification.findElement(By.tagName("b")).getText());
            notificationDTO.setDate(notification.findElement(By.className("timeago")).getAttribute("title"));
            notificationDTO.setDocumentType(notification.findElement(By.className("notification-details")).getText());
            notifications.add(notificationDTO);
        }
        return notifications;
    }

    @Override
    public String readLatestDocumentsBy(String filterOptions,String searchText) {
           driver.findElement(By.id("videosMenuLink")).click();

              driver.findElement(By.linkText("Files Shared with Me")).click();
                      driver.findElement(By.xpath("//a[@title='Select which field to filter on']")).click();
                    driver.findElement(By.partialLinkText(filterOptions)).click();
                             sendKeys(By.className("videos-and-documents-filter-wrapper"),searchText);
               driver.findElement(By.className("document-tile-container")).findElements(By.className("document-tile")).get(0).click();
                         ;
        return   driver.findElement(By.className("text-document-display-wrapper")).getText();
    }


}

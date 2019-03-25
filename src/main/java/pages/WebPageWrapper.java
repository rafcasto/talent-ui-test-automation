package pages;

import org.openqa.selenium.WebDriver;
import pages.web.DashboardWebPage;
import pages.web.LoginWebPageWeb;

public class WebPageWrapper  implements IWebPageWrapper{
    private WebDriver localDriver;
    private ILoginPage loginPage;
    private IDashboardPage dashboardPage;
    public WebPageWrapper(WebDriver driver){
        localDriver = driver;
    }

    public ILoginPage loginPage(){
        if(loginPage == null){
            loginPage = new LoginWebPageWeb(localDriver);
        }
        return loginPage;
    }

    @Override
    public IDashboardPage dashboardPage() {
        if(dashboardPage == null){
            dashboardPage = new DashboardWebPage(localDriver);
        }
        return dashboardPage;
    }
}

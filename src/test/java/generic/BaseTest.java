package generic;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.Context;
import utils.PropertiesMapper;


public class BaseTest {

    public static final ThreadLocal<Context>  context = new ThreadLocal<>();


    public WebDriver getDriver(){
        return context.get().getDriver();
    }

    public Context getContext(){
        return context.get();

    }

    @BeforeMethod()
    public void setUp() {
        context.set(new Context());
        String talentURl = this.context.get().GetValueOnEnvironmentProperties(PropertiesMapper.URL);
        context.get().getDriver().get(talentURl);

    }

    @AfterMethod
    public void removeSessions() {
        getDriver().close();
        if (getDriver() != null) {
            getDriver().quit();
        }

    }




}

package generic;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.Context;
import utils.PropertiesMapper;

import java.util.List;


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

    public void verifyStrings(List<String> expected, List<String> actual){
        boolean notSameSize = !(expected.size() == actual.size());
        if(notSameSize){
            Assert.assertFalse(notSameSize,"List of expected feature menu does not match" + "expected " + expected.toString() +" but found " + actual.toString());
        }

        for(int element = 0; element <= expected.size() -1;element++){
            Assert.assertEquals(actual.get(element),expected.get(element));
        }

    }




}

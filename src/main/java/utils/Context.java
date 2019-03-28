package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.platform.win32.WinBase;
import dto.UserDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import pages.ILoginPage;
import pages.IWebPageWrapper;
import pages.WebPageWrapper;
import pages.web.LoginWebPageWeb;

import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Context {
    private HashMap<PropertiesMapper, String> EnviromentProperties;
   // private ILoginPage dashboardPage;
    private List<UserDTO> listOfUsers;
    final static Log logger = LogFactory.getLog(Context.class);
    private IWebPageWrapper pageHandler;
    private WebDriver driver;

    public Context() {
        setEnvironmentProperties();
        startDriver();
    }


    public IWebPageWrapper getPageHandler(){
        if(pageHandler == null){
            pageHandler = new WebPageWrapper(getDriver());
        }
        return pageHandler;
    }
  /*  public ILoginPage dashboardPage(){
        if(dashboardPage == null){
            dashboardPage = new LoginWebPageWeb(getDriver());
        }
        return dashboardPage;
    }*/

    //Need to improve code refactoring
    public UserDTO findUser(String role){
        UserDTO user = new UserDTO();
        if(listOfUsers == null || listOfUsers.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            try{
                InputStream inJson = Context.class.getResourceAsStream("/Users.json");
                listOfUsers = Arrays.asList(mapper.readValue(inJson,UserDTO[].class));

            }catch (Exception ex){
                logger.error("could not load users" + ex.getStackTrace());
            }


        }
        user = listOfUsers.stream().filter(customer ->
                role.equalsIgnoreCase(customer.getRole())).findAny().orElse(null);
        return user;
    }



    public WebDriver getDriver(){
        return driver;
    }


    public WebDriver startDriver() {
        WebDriver driver = null;
        String seleniumHub = this.GetValueOnEnvironmentProperties(PropertiesMapper.SELENIUMHUB);
        String seleniumPort = this.GetValueOnEnvironmentProperties(PropertiesMapper.SELENIUMPORT);
        String executionMode = this.GetValueOnEnvironmentProperties(PropertiesMapper.EXECUTIONMODE);
        String hubURL = String.format("%1$s:%2$s/wd/hub", seleniumHub, seleniumPort);
        try {
            if(!executionMode.isEmpty() && executionMode.equalsIgnoreCase("soucelab")){
                String soucelabURl = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_URL);
                hubURL = soucelabURl;
            }
            driver = (new RemoteWebDriver(new URL(hubURL), getDesiredCapabilities()));
        } catch (MalformedURLException ex) {
            logger.error("error on initializing web driver" + ex.getMessage() + " " + Arrays.toString(ex.getStackTrace()));
        } catch (UnreachableBrowserException ex) {
            logger.warn("No remote server was found starting local browser");
           driver =  startLocalBrowser();
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        maximizeWindow();
        this.driver = driver;
        return  driver;

    }

    private void maximizeWindow(){
        try{
            driver.manage().window().maximize();
        }catch (Exception ex){
            logger.warn("Windown is already maximize");
        }
    }


    private WebDriver startLocalBrowser() {
        WebDriver driver = null;
        switch (getDesiredCapabilities().getBrowserName()) {
            case BrowserType.CHROME:
                driver = new ChromeDriver();
                break;
            case BrowserType.FIREFOX:
                driver = new FirefoxDriver();
                break;
            case BrowserType.SAFARI:
                driver =  new SafariDriver();
                break;
            case BrowserType.IE:
                driver = new InternetExplorerDriver();
                break;
            default:
                logger.error("No valid browser found " + getDesiredCapabilities().getBrowserName());
        }
        return  driver;

    }

    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities cap = new DesiredCapabilities();
        String userName = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_USERNAME);
        String soucelabKey = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_ACCESSKEY);
        String soucelabPlatform = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_PLATFORM);
        String soucelabVersion = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_VERSION);
        String soucelabBuild = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_BUILD);
        String soucelabName = this.GetValueOnEnvironmentProperties(PropertiesMapper.SOUCELAB_NAME);
        String browser = this.GetValueOnEnvironmentProperties(PropertiesMapper.BROWSERNAME);

        String executionMode = this.GetValueOnEnvironmentProperties(PropertiesMapper.EXECUTIONMODE);
        if (browser == null) {
            logger.error("browser field is empty in the properties file");

        }

        boolean notAvalidBrowser = !(
                BrowserType.CHROME.equalsIgnoreCase(browser) ||
                        BrowserType.FIREFOX.equalsIgnoreCase(browser) ||
                        BrowserType.IE.equalsIgnoreCase(browser) ||
                        BrowserType.SAFARI.equalsIgnoreCase(browser)
        );

        if (notAvalidBrowser) {
            logger.error(String.format("browser with name %1$s not valid", String.valueOf(DesiredCapabilities.chrome())));
        }
        cap.setCapability("browserName", browser);
        if(!executionMode.isEmpty() && executionMode.equalsIgnoreCase("soucelab")){
            cap.setCapability("username", userName);
            cap.setCapability("accessKey", soucelabKey);
           // cap.setCapability("browserName", browser);
            cap.setCapability("platform", soucelabPlatform);
            cap.setCapability("version", soucelabVersion);
            cap.setCapability("build", soucelabBuild);
            cap.setCapability("name", soucelabName);
        }
        return cap;


    }

    public String GetValueOnEnvironmentProperties(PropertiesMapper propertyKey) {
        String property = this.EnviromentProperties.get(propertyKey);
        boolean propertyDoesNotExist = property == null;
        if (propertyDoesNotExist) {
            //Throw some exception
        }
        return property;
    }

    private void setEnvironmentProperties() {
        Properties environmentProperties = getEnvironmentProperties();
        EnviromentProperties = new HashMap<>();
        boolean environmentPropertiesIsNull = environmentProperties == null;
        if (environmentPropertiesIsNull) {
            //throw some exception
        }
        EnviromentProperties.put(PropertiesMapper.URL, environmentProperties.getProperty("url"));
        EnviromentProperties.put(PropertiesMapper.SELENIUMHUB, environmentProperties.getProperty("selenium.hub"));
        EnviromentProperties.put(PropertiesMapper.SELENIUMPORT, environmentProperties.getProperty("selenium.port"));
        EnviromentProperties.put(PropertiesMapper.BROWSERNAME, environmentProperties.getProperty("browsername"));
        EnviromentProperties.put(PropertiesMapper.EXECUTIONMODE, environmentProperties.getProperty("executionmode"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_USERNAME, environmentProperties.getProperty("soucelab.username"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_ACCESSKEY, environmentProperties.getProperty("soucelab.accessKey"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_PLATFORM, environmentProperties.getProperty("soucelab.platform"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_VERSION, environmentProperties.getProperty("soucelab.version"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_BUILD, environmentProperties.getProperty("soucelab.build"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_NAME, environmentProperties.getProperty("soucelab.name"));
        EnviromentProperties.put(PropertiesMapper.SOUCELAB_URL, environmentProperties.getProperty("soucelab.url"));
    }

    private Properties getEnvironmentProperties() {
        Properties environmentProperties = null;
        try {
            Properties configurationProperties = getPropertiesFrom(Commons.CONFIG);
            String environment = configurationProperties.getProperty("environment");
            if (environment.equalsIgnoreCase(EnvironmentsMapper.QA.toString())) {
                environmentProperties = getPropertiesFrom(Commons.QAENVIRONMENT);
            } else if (environment.equalsIgnoreCase(EnvironmentsMapper.DEV.toString())) {
                environmentProperties = getPropertiesFrom(Commons.DEVENVIRONMENT);
            } else {
                throw new Exception("Environment not found");
            }
        } catch (Exception ex) {
            // throw new Exception(""); refactor later to handle any exception
        }

        return environmentProperties;
    }

    private Properties getPropertiesFrom(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream(propertiesFile);

        boolean propertyFileNotFound = (inputStream == null);
        if (propertyFileNotFound) {
            throw new FileNotFoundException("trying to access " + propertiesFile + " but file no found in current path");
        }

        properties.load(inputStream);
        return properties;
    }
}
package dashboard;

import dto.NotificationDto;
import dto.UserDTO;
import generic.BaseTest;

import org.testng.Assert;

import org.testng.SkipException;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;

public class LoginTest extends BaseTest {

    @Test(testName = "UI", groups = {"error", "login"})
    public void ErrorMessageShowsWhenLoginWithoutCredentials() {

        String expectedEmailHelperMessage = "Please enter your email address";
        String expectedPasswordHelperMessage = "Please enter your password";
        List<String> actualErrorMessages = new ArrayList<>();
        UserDTO user = getContext().findUser("admin");
        user.setUserName("");
        user.setPassword("");
        getContext().getPageHandler().loginPage().logInWith(user);
        actualErrorMessages = getContext().getPageHandler().loginPage().getListOfDisplayedErrors();
        boolean actualErrorListIsEmpty = (actualErrorMessages == null || actualErrorMessages.isEmpty());
        Assert.assertFalse(actualErrorListIsEmpty, "No errors Are displayed on the login page");
        Assert.assertTrue(actualErrorMessages.contains(expectedEmailHelperMessage), "no message was found expected " + expectedEmailHelperMessage);
        Assert.assertTrue(actualErrorMessages.contains(expectedPasswordHelperMessage), "no message was found expected " + expectedPasswordHelperMessage);


    }


    @Test(testName = "UI", groups = {"error", "login"})
    public void ErrorMessageShowsWhenEnteringAnInvalidEmail() {
        String expectedEmailHelperMessage = "Please enter a valid email address";
        List<String> actualErrorMessages = new ArrayList<>();
        UserDTO user = getContext().findUser("admin");
        user.setUserName("InvalidEmail");
        getContext().getPageHandler().loginPage().logInWith(user);
        actualErrorMessages = getContext().getPageHandler().loginPage().getListOfDisplayedErrors();
        boolean actualErrorListIsEmpty = (actualErrorMessages == null || actualErrorMessages.isEmpty());
        Assert.assertFalse(actualErrorListIsEmpty, "No errors Are displayed on the login page");
        Assert.assertTrue(actualErrorMessages.contains(expectedEmailHelperMessage), "no message was found expected " + expectedEmailHelperMessage);

    }

    @Test(testName = "UI", groups = {"error", "login"})
    public void ErrorMessageShowsWhenEnteringAnInvalidPassword() {
        String expectedWarningMessage = "Email or Password is incorrect";
        String actualWarningMessages = "";
        UserDTO user = getContext().findUser("admin");
        user.setPassword("wrongPassword");
        getContext().getPageHandler().loginPage().logInWith(user);
        actualWarningMessages = getContext().getPageHandler().loginPage().getWarningMessage();
        Assert.assertEquals(actualWarningMessages, expectedWarningMessage, "Not the same message");
    }

    @Test(testName = "UI", groups = {"default", "login","admin"})
    public void AdminIsAbleToLoginWIthValidCredentials() {
        List<String> expectedHeaders = new ArrayList<>();
        expectedHeaders.add("Management");
        expectedHeaders.add("Licenses");
        expectedHeaders.add("Recent Interactions");
        expectedHeaders.add("Rising Star");

        UserDTO user = getContext().findUser("admin");
        getContext().getPageHandler().loginPage().logInWith(user);
        List<String> actualHeaders = getContext().getPageHandler().dashboardPage().getHeaderOnAdminSidePanels();
       verifyStrings(expectedHeaders,actualHeaders);
    }

    @Test(testName = "UI", groups = {"default", "login","coach"})
    public void CoachIsAbleToLoginWIthValidCredentials() {
        //“Uploads” “Shared” “Feedback” and “Activity”
        List<String> expectedFeatureOPtions = new ArrayList<String>();
        expectedFeatureOPtions.add("Uploads");
        expectedFeatureOPtions.add("Shared");
        expectedFeatureOPtions.add("Feedback");
        expectedFeatureOPtions.add("Activity");
        UserDTO user = getContext().findUser("coach");
        getContext().getPageHandler().loginPage().logInWith(user);
        List<String> actualFeatures = getContext().getPageHandler().dashboardPage().getListOfAvailableFeatures();
        verifyStrings(expectedFeatureOPtions,actualFeatures);


    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void TeacherIsAbleToLoginWIthValidCredentials() {
        List<String> expectedFeatureOPtions = new ArrayList<String>();
        expectedFeatureOPtions.add("Uploads");
        expectedFeatureOPtions.add("Shared");
        expectedFeatureOPtions.add("Feedback");
        expectedFeatureOPtions.add("Goals");
        UserDTO user = getContext().findUser("teacher");
        getContext().getPageHandler().loginPage().logInWith(user);
        List<String> actualFeatures = getContext().getPageHandler().dashboardPage().getListOfAvailableFeatures();
        verifyStrings(expectedFeatureOPtions,actualFeatures);
    }





    @Test(testName = "UI", groups = {"default", "login"})
    public void ModalWindowOpenWhenUserClickOnDownloadLink() {

        List<String> expectedLInks = new ArrayList<>();
        expectedLInks.add("TORSH Talent for iOS");
        expectedLInks.add("TORSH Talent for Android");
        getContext().getPageHandler().loginPage().clickOnDownloadTheAppButton();
        List<String> actualLinks = getContext().getPageHandler().loginPage().getElementsOnDownloadAppModal();
        verifyStrings(expectedLInks,actualLinks);

    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void CorporatePageIsOpenWhenUserClickOnLearnMore() {
        String expectedUrl = "https://www.torsh.co/";
        getContext().getPageHandler().loginPage().clickOnLearnMoreButton();
        String actualURl = getContext().getPageHandler().loginPage().getCurrentURl();
        Assert.assertEquals(expectedUrl,actualURl);
    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void TeacherCreatesAndShareAdocumentToCoach() {
        int lastNotificationRecieved = 0;
        String teachersDocument = "Random Text";
        UserDTO user = getContext().findUser("teacher");
        getContext().getPageHandler().loginPage().logInWith(user);
        getContext().getPageHandler().dashboardPage().navigateToMyVideosAndFiels();
        getContext().getPageHandler().dashboardPage().createNewDocument("document");
        getContext().getPageHandler().dashboardPage().writeAndShareDocument(teachersDocument);
        getContext().getPageHandler().dashboardPage().shareWithModalBox("Coach","please review my Document");
        getContext().getPageHandler().dashboardPage().logOut();

         user = getContext().findUser("coach");
        getContext().getPageHandler().loginPage().logInWith(user);
        List<NotificationDto> notifications  = getContext().getPageHandler().dashboardPage().getListOfNotifications();
        boolean notificationIsEmpty = notifications == null || notifications.isEmpty();
        if(notificationIsEmpty){
            Assert.assertFalse(notificationIsEmpty,"No notification were found");
        }
        //I can use the API to fectch the name of the teacher, at the moment this value is hardcoded
        //we can also validate date of the message and content type
        Assert.assertEquals(notifications.get(lastNotificationRecieved).getSender(),"Talent Teacher");
        Assert.assertEquals(notifications.get(lastNotificationRecieved).getDocumentType(),"shared a document with you");

        String coachReadsDocument = getContext().getPageHandler().dashboardPage().readLatestDocumentsBy("User Name","Talent Teacher");
        Assert.assertEquals(coachReadsDocument,teachersDocument)rafael;

    }



    //getDriver().findElement(By.xpath("@title='Select which field to filter on'"))
}
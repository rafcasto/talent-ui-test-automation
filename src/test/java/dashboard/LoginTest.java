package dashboard;

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
        getContext().dashboardPage().logInWith(user);
        actualErrorMessages = getContext().dashboardPage().getListOfDisplayedErrors();
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
        getContext().dashboardPage().logInWith(user);
        actualErrorMessages = getContext().dashboardPage().getListOfDisplayedErrors();
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
        getContext().dashboardPage().logInWith(user);
        actualWarningMessages = getContext().dashboardPage().getWarningMessage();
        Assert.assertEquals(actualWarningMessages, expectedWarningMessage, "Not the same message");
    }

    @Test(testName = "UI", groups = {"default", "login"})
    public void UserIsAbleToLoginWIthValidCredentials() {
        UserDTO user = getContext().findUser("admin");
        getContext().dashboardPage().logInWith(user);
    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void TeacherIsAbleToLoginWIthValidCredentials() {
        UserDTO user = getContext().findUser("admin");
        getContext().dashboardPage().logInWith(user);
        throw new SkipException("Test in progress");
    }



    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void ModalWindowOpenWhenUserClickOnDownloadLink() {
        int iosLink = 0;
        int androidLink = 1;

        String expectedIosLink = "TORSH Talent for iOS";
        String expectedAndroidLink = "TORSH Talent for Android";
        getContext().dashboardPage().clickOnDownloadTheAppButton();
        List<String> actualLinks = getContext().dashboardPage().getElementsOnDownloadAppModal();
        boolean noLinks = actualLinks == null && actualLinks.size() <= 1;
        if(noLinks){
            Assert.assertFalse(noLinks,"No links or amount of links is not as expected ");
        }
        String actualIosLink = actualLinks.get(iosLink);
        String actualAndroidLink = actualLinks.get(androidLink);
        Assert.assertEquals(actualIosLink,expectedIosLink);
        Assert.assertEquals(actualAndroidLink,expectedAndroidLink);

    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void CorporatePageIsOpenWhenUserClickOnLearnMore() {
        String expectedUrl = "https://www.torsh.co/";
        getContext().dashboardPage().clickOnLearnMoreButton();
        String actualURl = getContext().dashboardPage().getCurrentURl();
        Assert.assertEquals(expectedUrl,actualURl);
    }

    @Test(testName = "UI", groups = {"default", "login", "teacher"})
    public void TeacherCreatesAndShareAdocumentToCoach() {
        throw new SkipException("Test in progress");
    }

    @Test(testName = "UI", groups = {"default", "login", "coach"})
    public void CoachRecievesDocumentSharedByTeacher() {
        throw new SkipException("Test in progress");
    }



}
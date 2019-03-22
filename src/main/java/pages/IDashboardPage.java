package pages;

import dto.UserDTO;

import java.util.List;

public interface IDashboardPage {
    void logInWith(UserDTO user);
    void clickOnForgotPassword();
    void clickOnDownloadTheAppButton();
    void clickOnLearnMoreButton();
    String getLoginText();
    String getWarningMessage();
    List<String> getListOfDisplayedErrors();
}

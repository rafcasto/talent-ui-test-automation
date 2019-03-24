package pages;

import dto.UserDTO;

import java.util.List;

public interface IDashboardPage {
    void logInWith(UserDTO user);
    void clickOnForgotPassword();
    void clickOnDownloadTheAppButton();
    String getCurrentURl();
    void clickOnLearnMoreButton();
    List<String> getElementsOnDownloadAppModal();
    String getLoginText();
    String getWarningMessage();
    List<String> getListOfDisplayedErrors();
}

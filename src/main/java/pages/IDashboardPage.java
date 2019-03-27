package pages;

import dto.NotificationDto;

import java.util.List;

public interface IDashboardPage {
    List<String> getListOfAvailableFeatures();
    List<String> getHeaderOnAdminSidePanels();
    void navigateToMyVideosAndFiels();
    void createNewDocument(String file);
    void writeAndShareDocument(String documentBody);
    void shareWithModalBox(String role,String message);
    void logOut();
    int getNumberOfNotifications();
    List<NotificationDto> getListOfNotifications();
    String readLatestDocumentsBy(String filterOption,String searchText);

}

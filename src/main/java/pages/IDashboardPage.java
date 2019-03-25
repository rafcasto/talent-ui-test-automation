package pages;

import java.util.List;

public interface IDashboardPage {
    List<String> getListOfAvailableFeatures();
    List<String> getHeaderOnAdminSidePanels();
    void navigateToMyVideosAndFiels();
    void createAndShareNew(String file);

}

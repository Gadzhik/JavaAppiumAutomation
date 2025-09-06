package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

// класс для навигации по приложению
public class NavigationUI extends MainPageObject
{

    private static final String
            MY_LISTS_LINK = "id:org.wikipedia:id/snackbar_action";

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    // метод для навигации
    public void clickMyLists()
    {
        // нажимаем кнопку View List
        this.waitForElementAndClick(MY_LISTS_LINK,
                "Cannot click to 'View List' button",
                5
        );
    }

}

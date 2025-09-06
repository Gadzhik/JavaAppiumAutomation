package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

// для работы со статьями
public class ArticlePageObject extends MainPageObject
{
    private static final String
        TITLE = "xpath://android.view.View[@content-desc='Object-oriented programming language']",
        FOOTER_ELEMENT = "xpath://*[@content-desc='View article in browser']",
        OPTIONS_SAVE_BUTTON = "xpath://android.widget.TextView[@content-desc='Save']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:org.wikipedia:id/snackbar_action",
        OPTIONS_NAME_OF_THE_LIST = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "id:android:id/button1";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // метод для ожидания названия(title) статьи
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15);
    }

    // метод в котором мы получаем название статьи
    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("contentDescription");
    }

    // добавляем метод для свайпа
    // если оставить TITLE = "//android.view.View[@content-desc='']", то свайп работает для теста testSwipeArticle
    public void swipeToFooter()
    {
        this.swipeUpToFindElement(FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    // метод для добавления статьи в список
    public void addArticleToMyList(String name_of_folder)
    {
        // save button
        this.waitForElementAndClick(OPTIONS_SAVE_BUTTON,
                "Cannot find button to Save article",
                5
        );

        // кликаем по пункту меню Add to list
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option 'Add to list'",
                5
        );

        // очищаем поле с текстом
        this.waitForElementAndClear(OPTIONS_NAME_OF_THE_LIST,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(OPTIONS_NAME_OF_THE_LIST,
                name_of_folder,
                "Cannot find 'Name of this list' field",
                5
        );

        // click to OK button
        this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                "Cannot find 'OK' button",
                5
        );
    }

    // пропустим пока что добавление метода для закрытия статьи. t - 4.00
}

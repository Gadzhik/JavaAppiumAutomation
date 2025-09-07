package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;

// для работы со статьями
abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_SAVE_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_NAME_OF_THE_LIST,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON;

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
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("contentDescription");
        } else {
            return title_element.getAttribute("name");
        }

    }

    // добавляем метод для свайпа
    // если оставить TITLE = "//android.view.View[@content-desc='']", то свайп работает для теста testSwipeArticle
    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
            "Cannot find the end of article",
            40);
        }
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

    public void addArticlesToMySaved()
    {
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link,",
                5
        );
    }

    // пропустим пока что добавление метода для закрытия статьи. t - 4.00
}

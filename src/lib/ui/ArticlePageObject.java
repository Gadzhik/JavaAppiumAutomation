package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

// для работы со статьями
public class ArticlePageObject extends MainPageObject
{
    private static final String
        TITLE = "//android.view.View[@content-desc='Object-oriented programming language']",
        FOOTER_ELEMENT = "//*[@content-desc='View article in browser']";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // метод для ожидания названия(title) статьи
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(By.xpath(TITLE), "Cannot find article title on page", 15);
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
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }
}

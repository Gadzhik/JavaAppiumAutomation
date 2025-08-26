import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Java (programming language)");
    }

    @Test
    public void testSearchWordsInArticles() {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_word = "Python";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_word,
                "Cannot find search_text_input element",
                15
        );


        List<WebElement> search_Results = driver.findElementsById("org.wikipedia:id/page_list_item_title");

        Assert.assertFalse("There is no results in the search", search_Results.isEmpty());

        for (WebElement result : search_Results) {
            String resultText = result.getText();
            Assert.assertTrue("Result does not contain the search word: " + resultText,
                    resultText.toLowerCase().contains(search_word.toLowerCase()));
        }
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

//        Assert.assertTrue("Less then two articles presented",
//                driver.findElements(By.id("org.wikipedia:id/page_list_item_title")).size() > 1);

    }

    // сравниваем название статьи с ожидаемым и отдаем ошибку, если оно не совпадает
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title!",
                "Object-oriented programming language",
                article_title
        );
    }

    // добавляем тест для проверки свайпа - НЕ РАБОТАЕТ 24.08.2025
    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();

    }

    @Test
    public void testHasElement() {
        WebElement skipButton = driver.findElementByXPath("//*[contains(@text, 'Skip')]");
        skipButton.click();

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia",
                5
        );

    }

    // домашка по 3му модулю - Ex2: Создание метода
    @Test
    public void testSearchFieldText() {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.assertElementHasText(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' text",
                "Search Wikipedia",
                5
        );
    }

    // Module 4. 03. Save first article - overlay, swipe left, variable
    @Test
    public void testSaveFirstArticleToMyList()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        // используем первые 4 метода для перехода к нужной статье
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Search wikipedia' article",
                5
        );

        // наверное стоит добавить нажатие на Header, потому, что не получается найти кнопку Got it в дереве ресурсов
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/view_page_header_image"),
                "Cannot click to header image",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.TextView[@content-desc='Save']"),
                "Cannot find button to Save article",
                5
        );

        // кликаем по пункту меню Add to list
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find option 'Add to list'",
                5
        );

        // очищаем поле с текстом
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        // добавляем новый список для чтения
        String name_of_folder = "Programming list";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find 'Name of this list' field",
                5
        );

        // click to OK button
        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find 'OK' button",
                5
        );

        // нажимаем кнопку View List
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot click to 'View List' button",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find saved article"
        );

        // убеждаемся, что статья отсутствует
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

    }

    // 05. Assert - basic - проверяем, что количество элементов в поиске > 0
    @Test
    public void testAmountOfNotEmptySearch()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String search_line = "Linkin Park Discography";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find 'LP input' input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                10
        );

        // узнаем найденное количество элементов, используем метод getAmountOfElements
        int amount_of_search_results = MainPageObject.getAmountOfElements(
                By.xpath(search_result_locator)
        );

        // убеждаемся, что колич получ элем больше 0
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );

    };

    // 06. Assert - assertion error
    @Test
    public void testAmountOfEmptySearch()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String search_line = "df33ffgg55hh66";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find 'df33ffgg55hh66 input' input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        String empty_result_label = "//*[@text='No results']";

        // ищем лейбл на странице
        MainPageObject.waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                10
        );

        // если в тесте возникнет ошибка, то мы попадем в этот ассерт
        MainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }

    // 07. Rotation - basics
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find 'Java input' input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[3]"),
                "Cannot find 'Java' article" + search_line,
                15
        );

        // записываем аттрибут получаемый из метода в переменную
        String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "text",
                "Cannot find title of article",
                15
        );

        // поворот экрана
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // снова получаем значение названия статьи
        String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']]"),
                "text",
                "Cannot find title of article",
                15
        );

        // сравниваем значения до и после ротации
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
        String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    // Less 4. 08. Background - basics
    @Test
    public void testCheckSearchArticleInBackground()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Search wikipedia' article",
                5
        );

        driver.runAppInBackground(Duration.ofSeconds(2));

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find article after returning from background",
                5
        );
    }

    // HW - Ex5: Тест: Сохранение двух статей либо Онбординг
    @Test
    public void testSaveTwoArticleToMyList()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Search wikipedia' article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/view_page_header_image"),
                "Cannot click to header image",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc='Save']"),
                "Cannot find button to Save article",
                5
        );

        // кликаем по пункту меню Add to list
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find option 'Add to list'",
                5
        );

        // очищаем поле с текстом
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        // добавляем новый список для чтения
        String name_of_folder = "Programming list";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find 'Name of this list' field",
                5
        );

        // click to OK button
        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find 'OK' button",
                5
        );

        // добавляем вторую статью в лист
        // клик по полю Search Wikipedia
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar_button_search'][@text='Search Wikipedia']"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Python",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Python (programming language)']"),
                "Cannot find 'Python' article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Python (programming language)']"),
                "Cannot find Python article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc='Save']"),
                "Cannot find button to Save article",
                5
        );

        // кликаем по пункту меню Add to list
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find option 'Add to list'",
                5
        );

        // выбираем уже добавленный список
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Programming list']"),
                "Cannot find 'Programming list' folder",
                5
        );

        // нажимаем кнопку View List
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot click to 'View List' button",
                5
        );

        // удаляем статью
        MainPageObject.swipeElementToLeft(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find saved article"
        );

        WebElement title_element = MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Python (programming language)']"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title!",
                "Python (programming language)",
                article_title
        );

    }

    // HW - Ex6: Тест: assert title
    @Test
    public void testAssertElementPresent()
    {
        MainPageObject.waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Java' article",
                5
        );

        MainPageObject.assertElementPresent(
                By.xpath("//*[@content-desc='Java (programming language)']"),
                "Cannot find article title"
        );
    }

}

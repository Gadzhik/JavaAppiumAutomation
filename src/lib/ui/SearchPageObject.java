package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

// тут будут методы которые будут использоваться для поиска
abstract public class SearchPageObject extends MainPageObject
{
    // задаем константы для xpath
    protected static String
            SKIP_BUTTON,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    // берем драйвер из MainPageObject
    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // метод для преобразования строки(substring) для метода waitForSearchResult
    /* Section for TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        // пишем, что({SUBSTRING}) на что(substring) мы будем заменять
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* Section for TEMPLATES METHODS */

    public void initSkipButtonClick()
    {
        this.waitForElementAndClickSkip(SKIP_BUTTON, "Cannot find 'SKIP' button", 5);
    }

    // метод который инициализирует процесс поиска
    public void initSearchInput()
    {
        this.waitForElementAndClickSkip(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        // убеждаемся, что клик произошел
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    // ждем появления кнопки Х в поиске
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find 'Search Close Button'!", 5);
    }

    // проверяем отсутствие кнопки Х в поиске по покончанию теста
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "'Search Close Button' is still present!", 5);
    }

    // клик по кнопке 'Search Close Button'
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click 'Search Close Button' !", 5);
    }

    // метод для ввода значения в строку
    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    // используем для ArticlePageObject
    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find search and click result with substring " + substring, 10);
    }

    // метод для получения колич статей - тест testAmountOfNotEmptySearch
    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                10
        );
        // узнаем найденное количество элементов, используем метод getAmountOfElements
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    // метод для подтверждения, что на странице нет никаких результатов - тест testAmountOfEmptySearch
    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element~~~", 10);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.waitForElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed no to find any result!!!", 10);
    }

}

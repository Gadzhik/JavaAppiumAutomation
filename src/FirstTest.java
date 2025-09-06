import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;
import java.util.List;

public class FirstTest extends CoreTestCase {

    // TODO - удалить после рефактора домашек
    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }
// TODO - переделать домашки под уроки рефактор
//    @Test
//    public void testSearchWordsInArticles() {
//        MainPageObject.waitForElementAndClickSkip(
//                By.xpath("//*[contains(@text, 'Skip')]"),
//                "Cannot find 'Skip' button",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia'",
//                5
//        );
//
//        String search_word = "Python";
//        MainPageObject.waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                search_word,
//                "Cannot find search_text_input element",
//                15
//        );
//
//
//        List<WebElement> search_Results = driver.findElementsById("org.wikipedia:id/page_list_item_title");
//
//        assertFalse("There is no results in the search", search_Results.isEmpty());
//
//        for (WebElement result : search_Results) {
//            String resultText = result.getText();
//            assertTrue("Result does not contain the search word: " + resultText,
//                    resultText.toLowerCase().contains(search_word.toLowerCase()));
//        }
//    }
//
//    @Test
//    public void testHasElement() {
//        WebElement skipButton = driver.findElementByXPath("//*[contains(@text, 'Skip')]");
//        skipButton.click();
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Cannot find Search Wikipedia",
//                5
//        );
//
//    }
//
//    // домашка по 3му модулю - Ex2: Создание метода
//    @Test
//    public void testSearchFieldText() {
//        MainPageObject.waitForElementAndClickSkip(
//                By.xpath("//*[contains(@text, 'Skip')]"),
//                "Cannot find 'Skip' button",
//                5
//        );
//
//        MainPageObject.assertElementHasText(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Cannot find 'Search Wikipedia' text",
//                "Search Wikipedia",
//                5
//        );
//    }
//
//    // HW - Ex5: Тест: Сохранение двух статей либо Онбординг
//    @Test
//    public void testSaveTwoArticleToMyList()
//    {
//        MainPageObject.waitForElementAndClickSkip(
//                By.xpath("//*[contains(@text, 'Skip')]"),
//                "Cannot find 'Skip' button",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Cannot find 'Search wikipedia' input",
//                5
//        );
//
//        MainPageObject.waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Java",
//                "Cannot find search input",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
//                "Cannot find 'Search wikipedia' article",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/view_page_header_image"),
//                "Cannot click to header image",
//                5
//        );
//
//        MainPageObject.waitForElementPresent(
//                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
//                "Cannot find article title",
//                15
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//android.widget.TextView[@content-desc='Save']"),
//                "Cannot find button to Save article",
//                5
//        );
//
//        // кликаем по пункту меню Add to list
//        MainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/snackbar_action"),
//                "Cannot find option 'Add to list'",
//                5
//        );
//
//        // очищаем поле с текстом
//        MainPageObject.waitForElementAndClear(
//                By.id("org.wikipedia:id/text_input"),
//                "Cannot find input to set name of articles folder",
//                5
//        );
//
//        // добавляем новый список для чтения
//        String name_of_folder = "Programming list";
//
//        MainPageObject.waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/text_input"),
//                name_of_folder,
//                "Cannot find 'Name of this list' field",
//                5
//        );
//
//        // click to OK button
//        MainPageObject.waitForElementAndClick(
//                By.id("android:id/button1"),
//                "Cannot find 'OK' button",
//                5
//        );
//
//        // добавляем вторую статью в лист
//        // клик по полю Search Wikipedia
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar_button_search'][@text='Search Wikipedia']"),
//                "Cannot find 'Search wikipedia' input",
//                5
//        );
//
//        MainPageObject.waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Python",
//                "Cannot find search input",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Python (programming language)']"),
//                "Cannot find 'Python' article",
//                5
//        );
//
//        MainPageObject.waitForElementPresent(
//                By.xpath("//android.view.View[@content-desc='Python (programming language)']"),
//                "Cannot find Python article title",
//                15
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//android.widget.TextView[@content-desc='Save']"),
//                "Cannot find button to Save article",
//                5
//        );
//
//        // кликаем по пункту меню Add to list
//        MainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/snackbar_action"),
//                "Cannot find option 'Add to list'",
//                5
//        );
//
//        // выбираем уже добавленный список
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Programming list']"),
//                "Cannot find 'Programming list' folder",
//                5
//        );
//
//        // нажимаем кнопку View List
//        MainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/snackbar_action"),
//                "Cannot click to 'View List' button",
//                5
//        );
//
//        // удаляем статью
//        MainPageObject.swipeElementToLeft(
//                By.id("org.wikipedia:id/page_list_item_title"),
//                "Cannot find saved article"
//        );
//
//        WebElement title_element = MainPageObject.waitForElementPresent(
//                By.xpath("//*[@text='Python (programming language)']"),
//                "Cannot find article title",
//                15
//        );
//
//        String article_title = title_element.getAttribute("text");
//
//        assertEquals(
//                "We see unexpected title!",
//                "Python (programming language)",
//                article_title
//        );
//
//    }
//
//    // HW - Ex6: Тест: assert title
//    @Test
//    public void testAssertElementPresent()
//    {
//        MainPageObject.waitForElementAndClickSkip(
//                By.xpath("//*[contains(@text, 'Skip')]"),
//                "Cannot find 'Skip' button",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Cannot find 'Search wikipedia' input",
//                5
//        );
//
//        MainPageObject.waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Java",
//                "Cannot find search input",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
//                "Cannot find 'Java' article",
//                5
//        );
//
//        MainPageObject.assertElementPresent(
//                By.xpath("//*[@content-desc='Java (programming language)']"),
//                "Cannot find article title"
//        );
//    }

}

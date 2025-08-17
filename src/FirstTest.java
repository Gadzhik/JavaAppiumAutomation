import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;
    // Чтобы тест работал нам нужно добавить 3 метода. Это - setUp, tearDown, firstTest

    // В методе Setup - мы будем устанавливать все необходимые параметры для того чтобы запустить Аппиум драйвер и поднять наше приложение на устройстве.
    @Before
    public void setUp() throws Exception {
        // задаем capabilities, которые необходимы Аппиуму для работы
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        // включаем андроид-драйвер и передаем ему все capabilities и путь из которого он должен запускаться
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    // tearDown - драйвер будет выключаться
    @After
    public void tearDown() {
        driver.quit();
    }

    // firstTest - тут будет распологаться код теста
    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    // Ex4*: Тест: проверка слов в поиске
    //  Написать тест, который делает поиск по какому-то слову. Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.
    //  Внимание, прокручивать результаты выдачи поиска не надо. Это мы научимся делать на следующих занятиях. Пока надо работать только с теми результатами поиска, который видны сразу, без прокрутки.

    @Test
    public void testSearchWordsInArticles() {
        waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_word = "Python";
        waitForElementAndSendKeys(
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
        waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Python",
                "Cannot find search input",
                5
        );
        Assert.assertTrue("Less then two articles presented",
                driver.findElements(By.id("org.wikipedia:id/page_list_item_title")).size() > 1);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find element",
                5
        );

        // метод закоментирован потому, что после очистки поля кнопка Х пропадает
//        waitForElementAndClear(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Cannot find search field",
//                5
//        );

        // добавляем тоже самое для кнопи X - отмена поиска
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Cannot find X to cancel search",
//                5
//        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_result_list"),
                "Search results are still presented on the page",
                5
        );
    }

    // сравниваем название статьи с ожидаемым и отдаем ошибку, если оно не совпадает
    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        // метод для клика по статье, которую мы нашли в поиске
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        // ждем пока статья подгрузится и записываем заголовок статьи в переменную
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        // используем title_element для получения аттрибута текста из заголовка (получаем название статьи для использования в ассерте - ниже)
        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    // добавляем тест для проверки свайпа
    @Test
    public void testSwipeArticle() {
        waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium article for ElementAndClick' input",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@content-desc='Appium']"),
                "Cannot find article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@content-desc='View article in browser']"),
                "Cannot find the end of the article",
                10
        );

    }

    @Test
    public void testHasElement() {
        WebElement skipButton = driver.findElementByXPath("//*[contains(@text, 'SKIP')]");
        skipButton.click();

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia",
                5
        );

    }

//    Ex2: Создание метода
//        Необходимо написать функцию, которая проверяет наличие ожидаемого текста у элемента. Предлагается назвать ее assertElementHasText. На вход эта функция должна             принимать локатор элемент, ожидаемый текст и текст ошибки, который будет написан в случае, если элемент по этому локатору не содержит текст, который мы ожидаем.
//        Также, необходимо написать тест, который проверяет, что поле ввода для поиска статьи содержит текст (в разных версиях приложения это могут быть тексты                    "Search..." или "Search Wikipedia", правильным вариантом следует считать тот, которые есть сейчас). Очевидно, что тест должен использовать написанный ранее             метод.
//        Результат выполнения задания нужно отдельным коммитом положить в git. В качестве ответа прислать ссылку на коммит. Если вам потребовалось несколько коммитов для          выполнения одного задания - присылайте ссылки на все эти коммиты с комментариями.

    // домашка по 3му модулю - Ex2: Создание метода
    @Test
    public void testSearchFieldText() {
        waitForElementAndClickSkip(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find 'Skip' button",
                5
        );

        assertElementHasText(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' text",
                "Search Wikipedia",
                5
        );
    }

   // отдельный метод для Wait, при помощи которого будем искать элемент по Xpath и ожидать его появления
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

        // передаем сообщение об ошибке и добавляем +\n для того, чтобы оно каждый раз начиналось с новой строки
        wait.withMessage(error_message + "\n");

        // возвращаем возникающий элемент - by
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    // добавляем перегрузку метода
    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    // for Skip button
    private WebElement waitForElementAndClickSkip(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // проверяем, что элемент Х не присутствует на странице
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    // добавляем метод для очистки поля
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    // пишем метод для свайпа снизу экрана вверх
    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);

        // определяем размер экрана и получаем параметры девайса
        Dimension size = driver.manage().window().getSize();

        // задаем некоторые переменные. 1. Начальная перем по оси Х. 2. Конечная перем по оси Х. 3. Начальная перем по оси У. 4. Конечная перем по оси У. Т.к палец будет двигаться снизу вверх, то меняться будет только ось У.
        int x = size.width / 2;
        // получ нач точку, кот находится в 80% экрана, внизу
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        // добавляем действие - свайп. Нажимаем на экран, ждем некоторое время и перемещаем свайп вверх. Выбираем точку внизу экрана (где то по середине), после этого провести свайпом вверх экрана в точку которая находится в середине (по горизонтальной оси), после этого отпускаем "палец"
        // perform() - отправляет всю указанную последовательность действий на выполнение
        // press() - координаты для нажатия
        // moveTo() - координаты для движения
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    // Module 4. Lesson 02. Swipe till element found - swipeQuick, counter
    // метод для быстрого свайпа
    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    // домашка по 3му модулю - Ex2: Создание метода
    private WebElement assertElementHasText(By by, String error_message, String text, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.getAttribute("text");
        return element;
    }

    // Метод для проверки, что нужного элем нет на странице, прокручивать страницу вниз и сделать проверку еще раз и так до тех пор пока он не достигнет футера
    // max_swipes - задаем максимальное количество необходимых свайпов, при привышении этого количества, цикл будет останавливаться
    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        // функция для поиска всех элементов на странице
        // при вызове метода запускается цикл, кот постоянно ищет элементы, кот передаются этому методу в переменную by. Если он их находит, то цикл завершается. Если не находит, то постоянно свайпает вверх

        // already_swiped - количество реальных свайпов
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (already_swiped > max_swipes)
            {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }
}

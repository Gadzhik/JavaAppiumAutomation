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

public class FirstTest
{
    private AppiumDriver driver;
    // Чтобы тест работал нам нужно добавить 3 метода. Это - setUp, tearDown, firstTest

    // В методе Setup - мы будем устанавливать все необходимые параметры для того чтобы запустить Аппиум драйвер и поднять наше приложение на устройстве.
    @Before
    public void setUp() throws Exception
    {
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
    public void tearDown()
    {
        driver.quit();
    }
    // firstTest - тут будет распологаться код теста
    @Test
    public void firstTest()
    {
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

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        // добавляем тоже самое для кнопи X - отмена поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    // сравниваем название статьи с ожидаемым и отдаем ошибку, если оно не совпадает
    @Test
    public void testCompareArticleTitle()
    {
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
    public void testSwipeArticle()
    {
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
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']//*[@text='Java (programming language)']"),
                "Cannot find 'Search wikipedia for ElementAndClick' input",
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        // добавляем метод для свайпа
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);

    }

    @Test
    public void testHasElement()
    {
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
    public void testSearchFieldText()
    {
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
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        // передаем сообщение об ошибке и добавляем +\n для того, чтобы оно каждый раз начиналось с новой строки
        wait.withMessage(error_message + "\n");
        // возвращаем возникающий элемент - by
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

        // добавляем перегрузку метода
    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    // for Skip button
    private WebElement waitForElementAndClickSkip(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // проверяем, что элемент Х не присутствует на странице
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    // добавляем метод для очистки поля
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    // пишем метод для свайпа снизу экрана вверх
    protected void swipeUp(int timeOfSwipe)
    {
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

    // домашка по 3му модулю - Ex2: Создание метода
    private WebElement assertElementHasText(By by, String error_message, String text, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.getAttribute("text");
        return element;
    }

}

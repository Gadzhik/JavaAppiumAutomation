package lib.ui;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lib.Platform;

public class MainPageObject
{
    // класс будет содержать методы к которым будут обращаться тесты

    // инициализируем драйвер
    protected AppiumDriver driver;

    // конструктор класса в который передаем драйвер
    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }


    // отдельный метод для Wait, при помощи которого будем искать элемент по Xpath и ожидать его появления
    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

        // передаем сообщение об ошибке и добавляем +\n для того, чтобы оно каждый раз начиналось с новой строки
        wait.withMessage(error_message + "\n");

        // возвращаем возникающий элемент - by
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    // добавляем перегрузку метода
    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }

    // for Skip button
    public WebElement waitForElementAndClickSkip(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // проверяем, что элемент Х не присутствует на странице
    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    // добавляем метод для очистки поля
    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }
/* МЕТОД СВАЙПА ДЛЯ СТАРЫХ БИБЛИОТЕК*/

//    // пишем метод для свайпа снизу экрана вверх
//    public void swipeUp(int timeOfSwipe) {
//        TouchAction action = new TouchAction(driver);
//
//        // определяем размер экрана и получаем параметры девайса
//        Dimension size = driver
//                .manage()
//                .window()
//                .getSize();
//
//        // задаем некоторые переменные.
//        // 1. Начальная перем по оси Х.
//        // 2. Конечная перем по оси Х.
//        // 3. Начальная перем по оси У.
//        // 4. Конечная перем по оси У. Т.к палец будет двигаться снизу вверх, то меняться будет только ось У.
//        int x = size.width / 2;
//
//        // получ нач точку, кот находится в 80% экрана, внизу
//        int start_y = (int) (size.height * 0.8);
//        int end_y = (int) (size.height * 0.2);
//
//        // добавляем действие - свайп. Нажимаем на экран, ждем некоторое время и перемещаем свайп вверх. Выбираем точку внизу экрана (где то по середине), после этого провести свайпом вверх экрана в точку которая находится в середине (по горизонтальной оси), после этого отпускаем "палец"
//        // perform() - отправляет всю указанную последовательность действий на выполнение
//        // press() - координаты для нажатия
//        // moveTo() - координаты для движения
//        action
//                .press(PointOption.point(x, start_y)
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
//                .moveTo(PointOption.point(x, end_y))
//                .release()
//                .perform();
//    }

    /* МЕТОД СВАЙПА ДЛЯ НОВЫХ БИБЛИОТЕК - java-client-7.0.0...*/

    public void swipeUp(int timeOfSwipe)
    {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.70);
        int endY = (int) (size.height * 0.30);
        int centerX = size.width / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1)

                //Двигаем палец на начальную позицию
                .addAction(finger.createPointerMove(Duration.ofSeconds(0),
                        PointerInput.Origin.viewport(), centerX, startY))
                //Палец прикасается к экрану
                .addAction(finger.createPointerDown(0))

                //Палец двигается к конечной точке
                .addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), centerX, endY))

                //Убираем палец с экрана
                .addAction(finger.createPointerUp(0));

        //Выполняем действия
        driver.perform(Arrays.asList(swipe));
    }

    // Module 4. Lesson 02. Swipe till element found - swipeQuick, counter
    // метод для быстрого свайпа
    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    // домашка по 3му модулю - Ex2: Создание метода
    public WebElement assertElementHasText(String locator, String error_message, String text, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.getAttribute("text");
        return element;
    }

    // Метод для проверки, что нужного элем нет на странице, прокручивать страницу вниз и сделать проверку еще раз и так до тех пор пока он не достигнет футера
    // max_swipes - задаем максимальное количество необходимых свайпов, при привышении этого количества, цикл будет останавливаться
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes)
    {
        // функция для поиска всех элементов на странице
        // при вызове метода запускается цикл, кот постоянно ищет элементы, кот передаются этому методу в переменную by. Если он их находит, то цикл завершается. Если не находит, то постоянно свайпает вверх

        By by = this.getLocatorByString(locator);
        // already_swiped - количество реальных свайпов
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (already_swiped > max_swipes)
            {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    // метод который использует isElementLocatedOnTheScreen
    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator))
        {
            if(already_swiped > max_swipes){
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    // метод для определения есть элем на странице или нет
    public Boolean isElementLocatedOnTheScreen(String locator)
    {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 15).getLocation().getY();
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y > screen_size_by_y;
    }

    // метод для удаления статьи при помощи свайпа - уже устарел. Новый метод находится прямо под старым.
//    protected void swipeElementToLeft(By by, String error_message)
//    {
//        // находим элемент на странице
//        WebElement element = waitForElementPresent(
//                by,
//                error_message,
//                10);
//
//        // делаем свайп по элементу. Нужно обнаружить элемент, найти координаты по х и у и сдвинуть влево
//
//        // данная ф-я запишет в переменную самую левую точку нашего элемента
//        int left_x = element.getLocation().getX();
//
//        int right_x = left_x + element.getSize().getWidth();
//        int upper_y = element.getLocation().getY();
//        int lower_y = upper_y + element.getSize().getHeight();
//
//        // находим середину элемента для свайпа
//        int middle_y = (upper_y + lower_y) / 2;
//
//        TouchAction action = new TouchAction(driver);
//
//        action.press(right_x, middle_y);
//        action.waitAction(400);
//        action.moveTo(left_x, middle_y);
//        action.release();
//        action.perform();
//    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message)
    {
        // TODO поправить для IOS
        WebElement element = this.waitForElementPresent(locator + "/..", error_message);
        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x + width) - 3;
        int point_to_click_y = middle_y;

        TouchAction action = new TouchAction(driver);
        System.out.println("ERRRRRROOOOOORRRRR --->>> clickElementToTheRightUpperCorner");
//        action.tap(point_to_click_x, point_to_click_y).perform();
    }

    public void swipeElementToLeft(String locator, String error_message) {

        // Находим элемент на экране, ожидая его появления в течение 10 секунд.
        WebElement element = waitForElementPresent(locator, error_message, 10);

        // Получаем координаты элемента на экране.
        Point location = element.getLocation();
        // Получаем размеры элемента (ширину и высоту).
        Dimension size = element.getSize();

        // Координата по оси X левой границы элемента.
        int left_x = location.getX();
        // Координата по оси X правой границы элемента.
        int right_x = left_x + size.getWidth();
        // Координата по оси Y верхней границы элемента.
        int upper_y = location.getY();
        // Координата по оси Y нижней границы элемента.
        int lower_y = upper_y + size.getHeight();
        // Координата по оси Y средней линии элемента.
        int middle_y = upper_y + (size.getHeight() / 2);

        // Начальная координата по оси X для свайпа (чуть левее правого края элемента).
        int start_x = right_x - 20;
        // Конечная координата по оси X для свайпа (чуть правее левого края элемента).
        int end_x = left_x + 20;
        // Начальная координата по оси Y для свайпа (по центру элемента).
        int start_y = middle_y;
        // Конечная координата по оси Y для свайпа (также по центру элемента).
        int end_y = middle_y;

        // Выполняем свайп с начальной точки до конечной с заданной продолжительностью.
        if (Platform.getInstance().isAndroid()) {
            this.swipe(
                    new Point(start_x, start_y),
                    new Point(end_x, end_y),
                    Duration.ofMillis(550)  // Устанавливаем продолжительность свайпа 550 миллисекунд.
            );
        } else {
            int offset_x = (-1 * element.getSize().getWidth());
//            swipe (new Point(offset_x, 0);
            // TODO -> Доделать for IOS
            System.out.println("Доделать");
        }

    }

    public void swipe(Point start, Point end, Duration duration) {

        // Создаем объект, представляющий палец для выполнения свайпа.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        // Создаем последовательность действий для выполнения свайпа.
        Sequence swipe = new Sequence(finger, 1);

        // Добавляем действие для перемещения пальца к начальной точке.
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        // Добавляем действие для нажатия на экран в начальной точке.
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Добавляем действие для перемещения пальца из начальной точки в конечную в течение заданного времени.
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        // Добавляем действие для отпускания пальца от экрана в конечной точке.
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Выполняем последовательность действий (свайп).
        this.driver.perform(Arrays.asList(swipe));
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    // убеждаемся, что в ходе поиска не нашлось ни одной статьи
    public void assertElementNotPresent(String locator, String error_message)
    {
        // получаем количество элементов в поиске по переданному xpath
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    // получаем заголовок статьи для сравнения с заголовком после ротации
    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    // HW - Ex6: Тест: assert title
    public void assertElementPresent(By by, String error_message)
    {
        if (driver.findElements(by).isEmpty())
        {
            throw new AssertionError(error_message);
        }
    }

    // метод для определения типов локаторов. Урок 06. Смена локаторов
    // получаем строку типа id:someid, кот предается на вход методу и поотм делится двое по : и т.д ...
    private By getLocatorByString(String locator_with_type)
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        // сюда передается первая часть разделенного локатора
        String by_type = exploded_locator[0];
        // сюда передается вторая часть разделенного локатора
        String locator = exploded_locator[1];

        // логика для разделения локаторов
        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }
}

package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class IOSTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723";

    @Override
    protected void setUp() throws Exception {
        // используем метод setUp из Junit
        super.setUp();

        // задаем capabilities, которые необходимы Аппиуму для работы
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone SE");
        capabilities.setCapability("appium:platformVersion", "11.3");
//        capabilities.setCapability("appium:automationName", "UiAutomator2");
//        capabilities.setCapability("appium:appPackage", "org.wikipedia");
//        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        // включаем андроид-драйвер и передаем ему все capabilities и путь из которого он должен запускаться
        driver = new IOSDriver(new URL(AppiumURL), capabilities);
        // HW - Ex7*: Поворот экрана
        this.rotateScreenPortrait();
    }

    // tearDown - драйвер будет выключаться

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        // используем метод tearDown из Junit
        super.tearDown();
    }

    // методы для поворота экрана
    protected void rotateScreenPortrait()
    {
        // поворот экрана
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape()
    {
        // поворот экрана
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    // метод для отправки приложения в бекграунд
    protected void backgroundApp(int seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(2));
    }
}

package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723";

    // Чтобы тест работал нам нужно добавить 3 метода. Это - setUp, tearDown, firstTest

    // В методе Setup - мы будем устанавливать все необходимые параметры для того чтобы запустить Аппиум драйвер и поднять наше приложение на устройстве.

    @Override
    protected void setUp() throws Exception {
        // используем метод setUp из Junit
        super.setUp();

        // задаем capabilities, которые необходимы Аппиуму для работы
        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

        // включаем андроид-драйвер и передаем ему все capabilities и путь из которого он должен запускаться
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);

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

    // метод для использования ENV VAR (PLATFORM=android)
    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        // записываем в переменную значение добавленой ENV VAR (PLATFORM=android)
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("appium:platformName", "Android");
            capabilities.setCapability("appium:deviceName", "emulator-5554");
            capabilities.setCapability("appium:platformVersion", "8.1.0");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:appPackage", "org.wikipedia");
            capabilities.setCapability("appium:appActivity", ".main.MainActivity");
            capabilities.setCapability("appium:app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)) {
        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone SE");
        capabilities.setCapability("appium:platformVersion", "11.3");
        capabilities.setCapability("appium:app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
    } else {
        throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }
        return capabilities;
    }
}

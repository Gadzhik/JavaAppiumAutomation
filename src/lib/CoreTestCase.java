package lib;
import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {
        // используем метод setUp из Junit
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIOSApp();
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

    // скипаем welcome на ИОС
    private void skipWelcomePageForIOSApp()
    {
        if(Platform.getInstance().isIOS()) {
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }
    }

}

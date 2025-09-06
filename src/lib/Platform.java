package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class Platform
{
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static String APPIUM_URL = "http://127.0.0.1:4723";

    // добавляем инстанс и приватный конструктор
    private static Platform instance;

    private Platform() {}

    // используем getInstance для вызова Platform
    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid())
        {
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS())
        {
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
        throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:platformVersion", "8.1.0");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone SE");
        capabilities.setCapability("appium:platformVersion", "11.3");
        capabilities.setCapability("appium:app", "C:\\Users\\gadzhi\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        return capabilities;
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }
}

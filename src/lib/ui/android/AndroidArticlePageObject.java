package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject
{
    static {
                TITLE = "xpath://android.view.View[@content-desc='Object-oriented programming language']";
                FOOTER_ELEMENT = "xpath://*[@content-desc='View article in browser']";
                OPTIONS_SAVE_BUTTON = "xpath://android.widget.TextView[@content-desc='Save']";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:org.wikipedia:id/snackbar_action";
                OPTIONS_NAME_OF_THE_LIST = "id:org.wikipedia:id/text_input";
                MY_LIST_OK_BUTTON = "id:android:id/button1";
    }

    public AndroidArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }
}















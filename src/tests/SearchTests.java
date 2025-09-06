package tests;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    // 05. Assert - basic - проверяем, что количество элементов в поиске > 0
    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results =SearchPageObject.getAmountOfFoundArticles();

        // убеждаемся, что колич получ элем больше 0
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    };

    // 06. Assert - assertion error
    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        String search_line = "df33ffgg55hh66";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

//        Assert.assertTrue("Less then two articles presented",
//                driver.findElements(By.id("org.wikipedia:id/page_list_item_title")).size() > 1);

    }

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSkipButtonClick();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Java (programming language)");
    }
}

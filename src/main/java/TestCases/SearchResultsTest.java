package TestCases;

import Page.SearchPage;
import Utils.TestUtils;
import org.junit.*;


/*
    Test class including all 3 testcases
 */
public class SearchResultsTest extends TestUtils {
    static SearchPage searchPage;

/*
    sets up the driver and initializing search page object
 */
    @BeforeClass
    public static void SetUp(){
        initialization();
        searchPage = new SearchPage();
    }

/*
    To ensure search is done before each testcase
 */
    @Before
    public void Search(){
        WaitForPageLoad();
        searchPage.SearchForKeyword();
    }

/*
    quiting the driver after testing is done
 */
    @AfterClass
    public static void TearDown(){
        driver.quit();
    }

/*
    1. Retrieves the searched keyword from config file
    2. Retrieves the text of both results
    3. Asserts if the keyword is in both results
 */
    @Test
    public void VerifyFirstResults() {

        String keyword = properties.getProperty("searchKeyWord");
        WaitForPageLoad();

        String firstResult = searchPage.GetFirstSearchResult();
        String secondResult = searchPage.GetSecondSearchResult();

        Assert.assertTrue("Keyword is not found in either result",
                firstResult.contains(keyword) && secondResult.contains(keyword));
    }

/*
    1. Moves to second page of the results
    2. Retrieves the actual count of the page items and the specified amount in the config file
    3. Asserts that both numbers are equal
 */
    @Test
    public void VerifySecondPageResults(){
        searchPage.GoToPage2();

        int actualPageCount = searchPage.CountOfPageElements();
        int expectedCount = Integer.parseInt(properties.getProperty("itemsPerPage"));

        Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

    }

/*
    1. Moves to third page of the results
    2. Retrieves the actual count of the page items and the saved number of results for the second page
    3. Asserts that both numbers are equal
 */
    @Test
    public void VerifyThirdPageResults(){
        searchPage.GoToPage3();

        int actualPageCount = searchPage.CountOfPageElements();
        int expectedCount = searchPage.GetSecondPageCount();

        Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

    }

}

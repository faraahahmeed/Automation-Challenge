package TestCases;

import Page.SearchPage;
import Utils.TestUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.io.IOException;


/*
    Test class including all 3 testcases
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchResultsTest extends TestUtils {
    static SearchPage searchPage;
    String currentMethodName;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void OpenReport(){
        startReport();
    }

    @AfterClass
    public static void CloseReport(){
        endReport();
    }
/*
    Sets up the driver and initializing search page object
    To ensure search is done before each testcase
 */
    @Before
    public void SetUp(){
        initialization();
        searchPage = new SearchPage();
        WaitForPageLoad();
        searchPage.SearchForKeyword();
        currentMethodName = testName.getMethodName();
        setTest(getExtent().createTest("Test: " + currentMethodName));
    }

/*
    quiting the driver after testcase is done
 */
    @After
    public void TearDown(){
        driver.quit();
    }

/*
    1. Retrieves the searched keyword from config file
    2. Retrieves the text of both results
    3. Asserts if the keyword is in both results
 */
    @Test
    public void Test1_VerifyFirstResults() {
        try {
            String keyword = properties.getProperty("searchKeyWord");
            WaitForPageLoad();

            String firstResult = searchPage.GetFirstSearchResult();
            String secondResult = searchPage.GetSecondSearchResult();

            Assert.assertTrue("Keyword is not found in either result",
                    firstResult.contains(keyword) && secondResult.contains(keyword));
            getTest().pass("Test passed");
        } catch (Throwable t) {
            try {
                TakeScreenshotAtEndOfTest(currentMethodName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getTest().fail("Test failed: " + t.getMessage());
            throw t;
        }
    }

/*
    1. Moves to second page of the results
    2. Retrieves the actual count of the page items and the specified amount in the config file
    3. Asserts that both numbers are equal
 */
    @Test
    public void Test2_VerifySecondPageResults(){
        try {
            searchPage.GoToPage2();

            int actualPageCount = searchPage.CountOfPageElements();
            int expectedCount = Integer.parseInt(properties.getProperty("itemsPerPage"));

            Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

            getTest().pass("Test passed");
        } catch (Throwable t) {
            try {
                TakeScreenshotAtEndOfTest(currentMethodName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getTest().fail("Test failed: " + t.getMessage());
            throw t;
        }
    }

/*
    1. Moves to third page of the results
    2. Retrieves the actual count of the page items and the saved number of results for the second page
    3. Asserts that both numbers are equal
 */
    @Test
    public void Test3_VerifyThirdPageResults(){

        try {
            searchPage.GoToPage3();

            int actualPageCount = searchPage.CountOfPageElements();
            int expectedCount = searchPage.GetSecondPageCount();

            Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

            getTest().pass("Test passed");
        } catch (Throwable t) {
            try {
                TakeScreenshotAtEndOfTest(currentMethodName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getTest().fail("Test failed: " + t.getMessage());
            throw t;
        }
    }

}

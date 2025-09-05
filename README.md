Testing framework used to verify the presence of specific text in search results and validate that search pages include the same number of results.
In **Config** file, specify the URL, desired browser, search keyword and number of items per page.
**Locators** class separates used locators from testing logic.
**SearchPage** class includes all interactions with the search engine.
**SearchResultsTest** class holds all 3 testcases.
**TestUtils** class includes all helping functions and initiatlizations used in the framework.

A new directory "screenshots" would be created under the main project folder during run time if the test failed with the screenshot named by the test name + date and time of failure.

Limitation:
  Automated testcases may trigger captcha check for the URL causing false errors.

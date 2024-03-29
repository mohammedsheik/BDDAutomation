package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

import static com.aventstack.extentreports.Status.FAIL;
import static com.aventstack.extentreports.Status.PASS;

public class BasePage {


    protected static final String LIST_CONTAINS_STRING = "//li[contains(string() , '%s')]";

    protected static final String INPUT_WITH_TITLE = "//input[@title='%s']";

    protected static final String ELEMENT_WITH_ARIALABEL = "//*[@aria-label='%s']";

    protected static final String INPUT_WITH_PLACEHOLDER = "//input[@placeholder='%s']";

    protected static final String BUTTON_WITH_TITLE = "//button[@title='%s']";

    protected static final String DIV_TEXT = "//div[text()='%s']";

    protected static final String ELEMENT_WITH_CLASS = "//*[@class='%s']";

    protected static final String ELEMENT_WITH_CLASS_INDEX = "(//*[@class='%s'])[%s]";

    protected static final String IFRAME_WITH_TITLE = "//iframe[@title='%s']";

    protected static final String ELEMENT_CONTAINS_CLASS = "//*[contains(@class,'%s')]";

    protected static final String BUTTON_WITH_INDEX = "(//button[text()='%s'])['%s']";

    protected static final String ELEMENT_CONTAINS_CLASS_WITH_INDEX = ELEMENT_CONTAINS_CLASS + "[%s]";

    protected static final String ELEMENT_CONTAINS_ARIALABEL = "//*[contains(@aria-label,'%s')]";

    protected static final String SELECT_WITH_ARIALABEL = "//select[@aria-label='%s']";

    protected static final String H3 = "//h3";

    protected static final String TEXT = "//*[text()='%s']";

    protected static final String FOLLOWING_INPUTBOX = "//*[text()='%s']/following::input[1]";

    protected static final String FOLLOWING_INPUTBOX = "//*[text()='%s']/following::input[1]";


    protected static final String EM_WITH_INDEX = "(//em)[%s]";

    protected static final String FOLLOWING_SIBLING = "//*[normalize-space(text())='%s']/following-sibling::*";

    protected static final String SPAN = "//span";

    protected WebDriver driver;
    protected SoftAssert softAssert;

    protected BasePage(WebDriver driver , SoftAssert softAssert){
        this.driver =  driver;
        this.softAssert = softAssert;
    }

    public static synchronized By getLocator(String locator) {
        return By.xpath(locator);
    }

    public static synchronized By getLocator(String locator , String value1) {
        return By.xpath(String.format(locator, value1));
    }

    public static synchronized By getLocator(String locator , String value1 , String value2) {
        return By.xpath(String.format(locator, value1 , value2));
    }


    public void verifyElementIsVisible(By locator , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public String verifyTextWhenVisible(By locator , String expectedText , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualText = driver.findElement(locator).getText();
            if(!actualText.equals(expectedText)){
                softAssert.assertTrue(false , "Text is not matching<br>Actual Text :"+actualText+"<br>Expected Text :"+expectedText);
                logAndScreenShotOnFailure("Expected Text not found<br>Actual Text :"+actualText+"<br>Expected Text :"+expectedText);
            }
            return actualText;
        }catch(Exception e) {
            softAssert.assertTrue(false , "\n\n" + e.getMessage()+"\n"+e.getStackTrace());
            return null;
        }
    }

    public void verifyAttributeWhenVisible(By locator , String attribute , String expected , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actual = driver.findElement(locator).getAttribute(attribute);
            softAssert.assertEquals(actual , expected , "Attribute value is not matching");
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void enterValueWhenVisible(By locator , String text , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void clickTheElementWhenVisible(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            driver.findElement(locator).click();
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void clickTheElementWhenVisibleUsingJS(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].click();",element);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void scrollToElement(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);",element);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public boolean verifyElementIsEnabled(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
             if(element.isEnabled())
                 return true;
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
        return false;
    }

    public  WebElement getWebElement(By locator , int timeOut){
        try {
            FluentWait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NotFoundException.class);

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public List<WebElement> getWebElements(By locator , int timeOut){
        try {
            FluentWait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NotFoundException.class);

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElements(locator);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public  void selectByVisibleText(By locator , String option , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
            Select select = new Select(element);
            select.selectByVisibleText(option);
        }catch(Exception e) {softAssert.assertTrue(false , e.getMessage());
        }
    }


    public  String geTextWhenVisible(By locator ,  int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).getText();
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public  boolean elementExistsNoReporting(By locator ,  int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public void logAndScreenShotOnFailure(String message){
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        String screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        testStep.log(FAIL, message , MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
    }

}

package com.iscweb.sputnik.uitest.runner;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Convenience methods used throughout different tests
 */
public class WebDriverUtils {

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    private WebDriverUtils() {
    }

    public static void quitBrowser() {
        getDriver().quit();
        setDriver(null);
        webDriverWait = null;
    }

    public static void click(WebElement element) {
        getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public static void javaScriptClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void scroll(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollAndClick(WebElement element) {
        scroll(element);
        click(element);
    }

    public static void clickElementFromList(WebElement element) {
        getWebDriverWait().until(movingIsFinished(element)).click();
    }

    public static void clickLoginButton() {
        WebElement loginButton = findElementByXpath("//button[@aria-label='LOG IN']");
        click(loginButton);
    }

    public static WebElement findElementByXpath(String xPath) {
        return getDriver().findElement(By.xpath(xPath));
    }

    public static List<WebElement> findElementsByXpath(String xPath) {
        return getDriver().findElements(By.xpath(xPath));
    }

    public static <T> void waitUntil(ExpectedCondition<T> condition) {
        getWebDriverWait().until(condition);
    }

    public static void sendKeys(By by, String text) {
        getDriver().findElement(by).sendKeys(text);
    }

    public static void fill(WebElement element, String text) {
        getWebDriverWait().until(ExpectedConditions.visibilityOf(element));

        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }
        element.sendKeys(text);
        getWebDriverWait().until(d -> element.getAttribute("value").equals(text));
    }

    public static WebElement findRow(String xpath) {
        WebElement foundRow = null;

        boolean loadingNextPage = false;
        do {
            // TODO Replace with proper wait condition
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> rows = findElementsByXpath(xpath);
            if (!rows.isEmpty()) {
                foundRow = rows.get(0);
            } else {
                WebElement nextPageButton = findElementByXpath("//button[@title = 'Next page']");

                if (nextPageButton.isEnabled()) {
                    loadingNextPage = true;
                    scroll(nextPageButton);
                    click(nextPageButton);
                } else {
                    loadingNextPage = false;
                }
            }
        } while (foundRow == null && loadingNextPage);

        return foundRow;
    }

    public static WebElement findAndAssertRow(String xpath) {
        WebElement foundRow = findRow(xpath);

        if (foundRow == null) {
            Assert.fail("record was not found while expected to be present.");
        }
        return foundRow;
    }

    public static void validateTableDisplayed(String entity, String headerName) {
        Assert.assertEquals(findElementByXpath(
                "//div/nav/ol/li[5]/p[contains(text(), " + entity + ")]").getText(), entity);
        Assert.assertTrue(findElementByXpath(
                "//th[contains(text(), " + headerName + ")]").isDisplayed());
        Assert.assertTrue(findElementByXpath(
                "//button/span[1][contains(text(),'Add')]").isDisplayed());
    }

    private static class MovingExpectedCondition implements ExpectedCondition<WebElement> {

        private By locator;
        private WebElement element = null;
        private Point location = null;

        public MovingExpectedCondition(WebElement element) {
            this.element = element;
        }

        public MovingExpectedCondition(By locator) {
            this.locator = locator;
        }

        @Override
        public WebElement apply(WebDriver driver) {
            if (element == null) {
                try {
                    element = driver.findElement(locator);
                } catch (NoSuchElementException e) {
                    return null;
                }
            }
            if (element.isDisplayed()) {
                Point location = element.getLocation();
                if (location.equals(this.location)) {
                    return element;
                }
                this.location = location;
            }
            return null;
        }
    }

    public static ExpectedCondition<WebElement> movingIsFinished(WebElement element) {
        return new MovingExpectedCondition(element);
    }

    public static ExpectedCondition<WebElement> movingIsFinished(By locator) {
        return new MovingExpectedCondition(locator);
    }

    public static void setDriver(WebDriver driverToSet) {
        driver = driverToSet;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver;
    }

    public static WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(getDriver(), 10);
        }
        return webDriverWait;
    }

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
}

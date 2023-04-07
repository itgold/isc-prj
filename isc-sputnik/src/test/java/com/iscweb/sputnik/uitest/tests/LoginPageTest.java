package com.iscweb.sputnik.uitest.tests;

import com.iscweb.sputnik.uitest.config.Config;
import com.iscweb.sputnik.uitest.runner.BaseTest;
import com.iscweb.sputnik.uitest.runner.WebDriverUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.iscweb.sputnik.uitest.runner.WebDriverUtils.findElementByXpath;

public class LoginPageTest extends BaseTest {

    // For each user, verify that can log in and get to the page with corresponding permissions
    @Test
    public void validCredentials() { 

        List<String> userNames = new ArrayList<>();

        userNames.add("system-admin@iscweb.io");
        userNames.add("system-district-admin@iscweb.io");
        userNames.add("system-analyst@iscweb.io");
        userNames.add("system-guard@iscweb.io");
        // userNames.add("system-guest@iscweb.io"); // TODO this user not working now, IWD-526

        for (String name : userNames) {

            WebDriverUtils.getDriver().get(getTargetHost());

            WebElement userName = findElementByXpath("//input[@name = 'username']");
            WebDriverUtils.fill(userName, name);

            WebElement password = findElementByXpath("//input[@name = 'password']");
            WebDriverUtils.fill(password, Config.getPassword());

            WebDriverUtils.clickLoginButton();

            // check that home page is loaded
            WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//span[contains(text(),'DASHBOARDS')]")));

            if (name.equals("system-admin@iscweb.io")) {

                // reload menu elements after login
                findMenuElements();
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getSchoolsAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getDistrictsAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getCamerasAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getDoorsAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getDronesAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getSpeakersAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getZonesAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getTagsAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getExternalUsersAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getZonesAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getUsersAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getSafetiesAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getUtilitiesAdmin()));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getRadiosAdmin()));


                // after logging in to home page - 'map doshboard' is selected and
                // as a result not clickable, so we  click on 'drones admin' first
                // to make 'map dashboard' clickable
                getDronesAdmin().click();
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(getMapDashboard()));

                // users other than admin should not have access to admin items
            } else {
                WebDriverUtils.waitUntil(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath("//span[contains(text(),'Administration')]")));
                WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable
                        (By.xpath("//a[contains(@href,'/ui/dashboards/map')]")));
            }

            logout();
        }
    }

    // Verify message for invalid login
    @Test
    public void invalidCredentials() {

        WebDriverUtils.getDriver().get(getTargetHost());

        // enter invalid password and valid user name
        WebElement userName = findElementByXpath("//input[@name = 'username']");
        WebDriverUtils.fill(userName, Config.getUserName());

        WebElement password = findElementByXpath("//input[@name = 'password']");
        WebDriverUtils.fill(password, "test123");

        WebDriverUtils.clickLoginButton();
        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Invalid username or password')]")));

        // enter invalid user name and valid password
        WebDriverUtils.getDriver().get(getTargetHost());

        userName = findElementByXpath("//input[@name = 'username']");
        WebDriverUtils.fill(userName, "system-admi@iscweb.io");

        password = findElementByXpath("//input[@name = 'password']");
        WebDriverUtils.fill(password, Config.getPassword());

        WebDriverUtils.clickLoginButton();
        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Invalid username or password')]")));
    }

    // Verify the message for forgot password link
    @Test
    public void forgotPasswordLink() {

        WebDriverUtils.getDriver().get(getTargetHost());

        WebElement forgotPassword = findElementByXpath("//button/span[text() = 'Forgot password?']");
        WebDriverUtils.click(forgotPassword);
        Assert.assertEquals(findElementByXpath("//p").getText(),
                "To reset password please contact system administrator over the email: support@iscweb.io");
        WebElement loginButton = findElementByXpath("//button[@aria-label = 'LOG IN']");
        Assert.assertFalse(loginButton.isEnabled());
    }

    // Verify that clicking back button doesn't bring you back to the system once you log out
    @Test
    public void browseBack() {

        WebDriverUtils.getDriver().get(getTargetHost());

        WebElement userName = findElementByXpath("//input[@name = 'username']");
        WebDriverUtils.fill(userName, Config.getUserName());

        WebElement password = findElementByXpath("//input[@name = 'password']");
        WebDriverUtils.fill(password, Config.getPassword());

        WebDriverUtils.clickLoginButton();
        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[contains(text(),'DASHBOARDS')]")));

        logout();

        WebDriverUtils.getDriver().navigate().back();

        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[@aria-label = 'LOG IN']")));
    }

    // Verify that http response status is 200 after entering correct credentials and Login button,
    // but technically if response is NOT 200 then valid credentials test would fail anyway
    @Test
    public void checkStatusCode() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(getTargetHost() + "/api/auth/login");

        String payload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}",
                Config.getUserName(),
                Config.getPassword());
        httpPost.setEntity(new StringEntity(payload));

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assert (response.getCode() == 200);
        client.close();
    }

    // in these tests we don't need login from @BeforeMethod and logout from @AfterMethod from BaseTest
    @Override
    @BeforeMethod
    protected void setUpAll() {
    }

    @Override
    @AfterMethod
    protected void setDownAll() {
    }
}

package com.iscweb.sputnik.uitest.runner;

import com.iscweb.sputnik.uitest.common.EntityType;
import com.iscweb.sputnik.uitest.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.URL;

/**
 * Some support methods to be used in extending classes
 */
public abstract class BaseTest {

    // maybe we will have Configuration class later and target will be only one of the properties
    private static String targetHost;

    //shortcut to menu items that clicked a lot in tests
    private WebElement mapDashboard;
    private WebElement districtsAdmin;
    private WebElement schoolsAdmin;
    private WebElement regionsAdmin;
    private WebElement zonesAdmin;
    private WebElement doorsAdmin;
    private WebElement camerasAdmin;
    private WebElement speakersAdmin;
    private WebElement dronesAdmin;
    private WebElement tagsAdmin;
    private WebElement usersAdmin;
    private WebElement externalUsersAdmin;
    private WebElement radiosAdmin;
    private WebElement safetiesAdmin;
    private WebElement utilitiesAdmin;

    private final EntityCleanUpManager entitiesCleanUpManager = new EntityCleanUpManager();

    static {
        WebDriverManager.chromedriver().setup();

        targetHost = Config.getHost();
    }

    //for keeping track of created entities to delete them after test is done
    protected void registerForCleanup(String entityName, EntityType entityType) {
        entitiesCleanUpManager.registerEntity(entityName, entityType);
    }

    protected void findMenuElements() {
        mapDashboard = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/dashboards/map')]");
        schoolsAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/schoolsList')]");
        camerasAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/cameras')]");
        speakersAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/speakers')]");
        doorsAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/doors')]");
        dronesAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/drones')]");
        usersAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/users')]");
        districtsAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/districts')]");
        tagsAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/tags')]");
        regionsAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/regions')]");
        zonesAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/zones')]");
        externalUsersAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/externalUsers')]");
        safetiesAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/safeties')]");
        utilitiesAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/utilities')]");
        radiosAdmin = WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/admin/radios')]");
    }

    @BeforeClass
    protected void beforeClass() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--headless");

        try {
            if (Config.getIsRemoteDriver()) {
                RemoteWebDriver driver = new RemoteWebDriver(
                        new URL(Config.getRemoteDriver()),
                        chromeOptions
                );
                System.out.println("init RemoteWebDriver");
                WebDriverUtils.setDriver(driver);
            } else {
                // For use with ChromeDriver:
                ChromeDriver driver = new ChromeDriver(chromeOptions);
                System.out.println("init ChromeDriver");
                WebDriverUtils.setDriver(driver);
            }

            System.out.println("Browser opened");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @BeforeMethod
    protected void setUpAll() {
        String hostUrl = targetHost;
        login(hostUrl);

        findMenuElements();
    }

    @AfterMethod
    protected void setDownAll() {
        try {
            entitiesCleanUpManager.cleanUpCreatedEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //LoggerUtils.log("Logging out");
        logout();
    }

    @AfterClass
    protected void afterClass() {
        WebDriverUtils.quitBrowser();
        System.out.println("Browser closed");
        //LoggerUtils.log("Browser closed");
    }

    protected void logout() {
        WebElement profileButton = WebDriverUtils.findElementByXpath("//img[@src = 'assets/images/avatars/profile.jpg']");
        WebDriverUtils.click(profileButton);

        WebElement logoutButton = WebDriverUtils.findElementByXpath("//span[text() = 'Logout']");
        WebDriverUtils.click(logoutButton);
    }

    private void login(String url) {
        WebDriverUtils.getDriver().get(url);

        WebElement loginButton = WebDriverUtils.findElementByXpath("//button[@aria-label = 'LOG IN']");
        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOf(loginButton));

        //if there are hardcoded credential remove them and enter valid ones
        WebElement userName = WebDriverUtils.findElementByXpath("//input[@name = 'username']");
        while (!userName.getAttribute("value").equals("")) {
            userName.sendKeys(Keys.BACK_SPACE);
        }
        userName.sendKeys(Config.getUserName());

        WebElement password = WebDriverUtils.findElementByXpath("//input[@name = 'password']");
        while (!password.getAttribute("value").equals("")) {
            password.sendKeys(Keys.BACK_SPACE);
        }
        password.sendKeys(Config.getPassword());
        loginButton.click();

        WebDriverUtils.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = '/ui/admin/schoolsList']")));

        Assert.assertEquals(WebDriverUtils.findElementByXpath("//p[contains(text(),'720 Security')]").getText(), "720 Security");
    }

    public static String getTargetHost() {

        return targetHost;
    }

    public WebElement getSchoolsAdmin() {
        return schoolsAdmin;
    }

    public WebElement getCamerasAdmin() {
        return camerasAdmin;
    }

    public WebElement getSpeakersAdmin() {
        return speakersAdmin;
    }

    public WebElement getDoorsAdmin() {
        return doorsAdmin;
    }

    public WebElement getDronesAdmin() {
        return dronesAdmin;
    }

    public WebElement getUsersAdmin() {
        return usersAdmin;
    }

    public WebElement getDistrictsAdmin() {
        return districtsAdmin;
    }

    public WebElement getMapDashboard() {
        return mapDashboard;
    }

    public WebElement getTagsAdmin() {
        return tagsAdmin;
    }

    public WebElement getRegionsAdmin() {
        return regionsAdmin;
    }

    public WebElement getZonesAdmin() {
        return zonesAdmin;
    }

    public WebElement getExternalUsersAdmin() { return externalUsersAdmin; }

    public WebElement getSafetiesAdmin() {
        return safetiesAdmin;
    }

    public WebElement getUtilitiesAdmin() {
        return utilitiesAdmin;
    }

    public WebElement getRadiosAdmin() {
        return radiosAdmin;
    }

}

package com.iscweb.sputnik.uitest.tests;

import com.iscweb.sputnik.uitest.common.EntityType;
import com.iscweb.sputnik.uitest.runner.BaseTest;
import com.iscweb.sputnik.uitest.runner.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.iscweb.sputnik.uitest.common.EntityType.*;
import static com.iscweb.sputnik.uitest.runner.WebDriverUtils.*;

/**
 * District entity isn't part of this test because it is not deletable
 */
public class EntityCrudTest extends BaseTest {

    private final String SCHOOL_NAME = "test_school_new";

    /**
     * Create a test_school entity if not in db yet, to use in dependent tests
     */
    @Test
    public void createAndReadTestSchool() {

        WebDriverUtils.click(getSchoolsAdmin());

        validateTableDisplayed("Schools", "Address");

        WebElement foundSchoolRow = findRow("//tr/td[text() = '" + SCHOOL_NAME + "']/ancestor::tr");

        if (foundSchoolRow == null) {

            WebElement addButton = findElementByXpath("//button/span[1][contains(text(),'Add')]");
            WebDriverUtils.click(addButton);

            sendKeys(By.xpath("//input[@name='name']"), SCHOOL_NAME);
            sendKeys(By.xpath("//input[@name='contactEmail']"), "email");
            sendKeys(By.xpath("//input[@name='address']"), "address");
            sendKeys(By.xpath("//input[@name='city']"), "city");
            sendKeys(By.xpath("//input[@name='state']"), "state");
            sendKeys(By.xpath("//input[@name='zipCode']"), "55555");
            sendKeys(By.xpath("//input[@name='country']"), "USA");

            WebElement formAddButton = findElementByXpath("//button[2]/span[1][contains(text(),'Add')]");
            WebDriverUtils.click(formAddButton);
        }

        //check if test_school appears in table
        String[] schoolValues = {SCHOOL_NAME, null, null, null, null, null};
        readEntity(SCHOOL_NAME, schoolValues);
    }

    /**
     * Create a door entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudDoor() {

        WebDriverUtils.click(getDoorsAdmin());

        validateTableDisplayed("Doors", "Region");

        String doorName = WebDriverUtils.createUUID();
        createEntity("externalId", doorName, DOOR);

        String[] doorValues = {doorName, null, "ACTIVATED", null, null, null};
        readEntity(doorName, doorValues);

        updateEntity(doorName, doorValues, DOOR);

        String[] updatedDoorValues = {doorName, null, "DEACTIVATED", null, null, null};
        deleteEntity(doorName, updatedDoorValues, DOOR);
    }

    /**
     * Create a camera entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudCamera() {

        WebDriverUtils.click(getCamerasAdmin());

        validateTableDisplayed("Cameras", "Region");

        String cameraName = WebDriverUtils.createUUID();
        createEntity("externalId", cameraName, CAMERA);

        String[] cameraValues = {cameraName, null, "ACTIVATED", null, null, null};
        readEntity(cameraName, cameraValues);

        updateEntity(cameraName, cameraValues, CAMERA);

        String[] updatedCameraValues = {cameraName, null, "DEACTIVATED", null, null, null};
        deleteEntity(cameraName, updatedCameraValues, CAMERA);
    }

    /**
     * Create a speaker entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudSpeaker() {

        WebDriverUtils.click(getSpeakersAdmin());

        validateTableDisplayed("Speakers", "Region");

        String speakerName = WebDriverUtils.createUUID();
        createEntity("externalId", speakerName, SPEAKER);

        String[] speakerValues = {speakerName, null, "ACTIVATED", null, null, null};
        readEntity(speakerName, speakerValues);

        updateEntity(speakerName, speakerValues, SPEAKER);

        String[] updatedSpeakerValues = {speakerName, null, "DEACTIVATED", null, null, null};
        deleteEntity(speakerName, updatedSpeakerValues, SPEAKER);
    }

    /**
     * Create a drone entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudDrone() {

        WebDriverUtils.click(getDronesAdmin());

        validateTableDisplayed("Drones", "Region");

        String droneName = WebDriverUtils.createUUID();
        createEntity("externalId", droneName, DRONE);

        String[] droneValues = {droneName, "TETHERED", "ACTIVATED", null, null, null};
        readEntity(droneName, droneValues);

        updateEntity(droneName, droneValues, DRONE);

        String[] updatedDroneValues = {droneName, "TETHERED", "DEACTIVATED", null, null, null};
        deleteEntity(droneName, updatedDroneValues, DRONE);
    }

    /**
     * Create a utility entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudUtility() {

        WebDriverUtils.click(getUtilitiesAdmin());

        validateTableDisplayed("Utilities", "Region");

        String utilityName = WebDriverUtils.createUUID();
        createEntity("externalId", utilityName, UTILITY);

        String[] utilityValues = {utilityName, "VENTILATION_SHUTOFF", "ACTIVATED", null, null, null};
        readEntity(utilityName, utilityValues);

        updateEntity(utilityName, utilityValues, UTILITY);

        String[] updatedUtilityValues = {utilityName, "VENTILATION_SHUTOFF", "DEACTIVATED", null, null, null};
        deleteEntity(utilityName, updatedUtilityValues, UTILITY);
    }

    /**
     * Create a safety entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudSafety() {

        WebDriverUtils.click(getSafetiesAdmin());

        validateTableDisplayed("Safeties", "Region");

        String safetyName = WebDriverUtils.createUUID();
        createEntity("externalId", safetyName, SAFETY);

        String[] safetyValues = {safetyName, "FIRE_ALARM", "ACTIVATED", null, null, null};
        readEntity(safetyName, safetyValues);

        updateEntity(safetyName, safetyValues, SAFETY);

        String[] updatedSafetyValues = {safetyName, "FIRE_ALARM", "DEACTIVATED", null, null, null};
        deleteEntity(safetyName, updatedSafetyValues, SAFETY);
    }

    /**
     * Create a radio entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudRadio() {

        WebDriverUtils.click(getRadiosAdmin());

        validateTableDisplayed("Radios", "Region");

        String radioName = WebDriverUtils.createUUID();
        createEntity("externalId", radioName, RADIO);

        String[] radioValues = {radioName, "ACTIVATED", null, null, null};
        readEntity(radioName, radioValues);

        updateEntity(radioName, radioValues, RADIO);

        String[] updatedRadioValues = {radioName, "DEACTIVATED", null, null, null};
        deleteEntity(radioName, updatedRadioValues, RADIO);
    }

    /**
     * Create a user entity, verify creation, edit entity and delete entity
     */
    @Test
    public void crudUser() {

        WebDriverUtils.click(getUsersAdmin());

        validateTableDisplayed("Users", "Email");

        String userLastName = "test_last";
        WebElement addButton = findElementByXpath("//button/span[1][contains(text(),'Add')]");
        WebDriverUtils.click(addButton);
        sendKeys(By.xpath("//input[@name='lastName']"), userLastName);
        sendKeys(By.xpath("//input[@name='firstName']"), "test_first");
        sendKeys(By.xpath("//input[@name='email']"), "test@iscweb.io");

        WebElement formAddButton = findElementByXpath("//button[2]/span[1][contains(text(),'Add')]");
        WebDriverUtils.click(formAddButton);

        registerForCleanup(userLastName, EntityType.USER);

        String[] userValues = {userLastName, "test_first", "ACTIVATED", "test@iscweb.io", null, null};
        readEntity(userLastName, userValues);

        updateEntity(userLastName, userValues, USER);

        String[] updatedUserValues = {userLastName, "test_first", "DEACTIVATED", "test@iscweb.io", null, null};
        deleteEntity(userLastName, updatedUserValues, USER);
    }

    /**
     * Create a region entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudRegion() {

        WebDriverUtils.click(getRegionsAdmin());

        Assert.assertTrue(findElementByXpath(
                "//button/span[1][contains(text(),'Add')]").isDisplayed());

        String regionName = WebDriverUtils.createUUID();
        createEntity("name", regionName, REGION);

        String[] regionValues = {regionName, null, null, null, null};
        readEntity(regionName, regionValues);

        updateEntity(regionName, regionValues, REGION);

        WebElement foundRegionRow = findAndAssertRow("//tr/td[text() = '" + regionName + "']/ancestor::tr");

        // pressing delete button of that row
        WebElement deleteButton = foundRegionRow.findElement(By.xpath("td[5]/button[2]"));
        WebDriverUtils.javaScriptClick(deleteButton);
        WebElement deleteConfirmation = findElementByXpath("//button[2]/span[1][contains(text(), 'Yes, Delete')]");
        WebDriverUtils.click(deleteConfirmation);
        WebDriverUtils.waitUntil(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//tr/td[text() = '" + regionName + "']/ancestor::tr"), 0));

        List<WebElement> updatedRows = findElementsByXpath("//tr/td[text() = '" + regionName + "']/ancestor::tr");
        Assert.assertEquals(updatedRows.size(), 0);
    }

    /**
     * Create a zone entity, verify creation, edit entity and delete entity
     */
    @Test(dependsOnMethods = "createAndReadTestSchool")
    public void crudZone() {

        WebDriverUtils.click(getZonesAdmin());

        Assert.assertTrue(findElementByXpath(
                "//button/span[1][contains(text(),'New Zone')]").isDisplayed());

        String zoneName = WebDriverUtils.createUUID();
        createEntity("name", zoneName, ZONE);

        String[] zoneValues = {zoneName, null, null, null};
        readEntity(zoneName, zoneValues);

        updateEntity(zoneName, zoneValues, ZONE);

        String[] updatedZoneValues = {zoneName, null, null, null};
        deleteEntity(zoneName, updatedZoneValues, ZONE);
    }

    /**
     * Create a tag entity, verify creation and delete entity (not checking update for now)
     */
    @Test
    public void crudTag() {

        WebDriverUtils.click(getTagsAdmin());

        validateTableDisplayed("Tags", "Tag");

        String tagName = WebDriverUtils.createUUID();
        createEntity("name", tagName, TAG);

        String[] tagValues = {tagName, null};
        readEntity(tagName, tagValues);

        //delete entity
        assertRecordValues("//tr/td[text() = '" + tagName + "']/ancestor::tr", tagValues);

        WebElement foundTagRow = findAndAssertRow("//tr/td[text() = '" + tagName + "']/ancestor::tr");

        // pressing delete button of that row
        WebElement deleteButton = foundTagRow.findElement(By.xpath("td[2]/button[2]"));
        WebDriverUtils.javaScriptClick(deleteButton);
        WebElement deleteConfirmation = findElementByXpath("//button[2]/span[1][contains(text(), 'Yes, Delete')]");
        WebDriverUtils.click(deleteConfirmation);
        WebDriverUtils.waitUntil(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//tr/td[text() = '" + tagName + "']/ancestor::tr"), 0));

        List<WebElement> updatedRows = findElementsByXpath("//tr/td[text() = '" + tagName + "']/ancestor::tr");
        Assert.assertEquals(updatedRows.size(), 0);
    }

    private void assertRecordValues(String xpath, String[] values) {

        WebElement foundRow = findAndAssertRow(xpath);

        List<WebElement> columns = foundRow.findElements(By.tagName("td"));

        Assert.assertEquals(columns.size(), values.length);

        for (int i = 0; i < columns.size(); i++) {
            if (values[i] != null) {
                WebDriverUtils.scroll(foundRow);
                Assert.assertEquals(columns.get(i).getText(), values[i]);
            }
        }
    }

    private void assignEntityToSchool(EntityType entityType) {

        WebElement schoolField = findElementByXpath("//label[contains(text(), 'School')]/parent::div");
        WebDriverUtils.click(schoolField);

        WebElement testSchool = findElementByXpath("//div/ul/li[contains(text(),'" + SCHOOL_NAME + "')]");
        WebDriverUtils.click(testSchool);

        /*if (entityType != ZONE) {
            WebElement parentRegion = findElementByXpath("//div/span[contains(text(), 'Parent Region')]/following-sibling::button");
            WebDriverUtils.click(parentRegion);

            WebElement parentSchoolRegion = findElementByXpath("/html/body/div[3]/div[3]/ul/li/div/div[1]");
            WebDriverUtils.click(parentSchoolRegion);

            WebElement closeButton = findElementByXpath("//button/span[1][contains(text(), 'Close')]");
            WebDriverUtils.click(closeButton);
        }*/
    }

    private void createEntity(String fieldName, String entityName, EntityType entityType) {

        if (entityType == ZONE) {
            WebElement newZoneButton = findElementByXpath("//button/span[1][contains(text(),'New Zone')]");
            WebDriverUtils.click(newZoneButton);
        } else {
            WebElement addButton = findElementByXpath("//button/span[1][contains(text(),'Add')]");
            WebDriverUtils.click(addButton);
        }

        //waiting for Add form to open
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form-dialog-title']")));

        if (entityType == DOOR || entityType == CAMERA || entityType == DRONE || entityType == SPEAKER || entityType == UTILITY || entityType == SAFETY || entityType == RADIO) {
            sendKeys(By.xpath("//input[@name='" + fieldName + "']"), entityName);
        }

        if (entityType == RADIO) {
            sendKeys(By.xpath("//textarea[@name='name']"), entityName);
        } else {
            sendKeys(By.xpath("//input[@name='name']"), entityName);
        }

        if (entityType != TAG) {
            assignEntityToSchool(entityType);
        }

        if (entityType == ZONE) {
            //Actions action = new Actions(WebDriverUtils.getDriver());
            //action.moveByOffset(0, 0).click().build().perform();
            WebElement formSaveButton = findElementByXpath("//button[2]/span[1][contains(text(),'Save')]");
            WebDriverUtils.click(formSaveButton);
        } else if (entityType == TAG) {
            WebElement formAddButton = findElementByXpath("//button[2]/span[1][contains(text(),'Add')]");
            WebDriverUtils.click(formAddButton);
        } else {
            //Actions action = new Actions(WebDriverUtils.getDriver());
            //action.moveByOffset(0, 0).click().build().perform();
            WebElement formAddButton = findElementByXpath("//button[2]/span[1][contains(text(),'Add')]");
            WebDriverUtils.javaScriptClick(formAddButton);
        }

        registerForCleanup(entityName, entityType);
    }

    private void readEntity(String entityName, String[] values) {

        assertRecordValues("//tr/td[text() = '" + entityName + "']/ancestor::tr", values);
    }

    private void updateEntity(String entityName, String[] values, EntityType entityType) {

        String rowXPath = "//tr/td[text() = '" + entityName + "']/parent::tr";
        assertRecordValues(rowXPath, values);

        WebElement foundRow = findAndAssertRow(rowXPath);

        //pressing Edit button of that row
        WebElement updateButton = foundRow.findElement(By.xpath("td/button[1]"));
        WebDriverUtils.javaScriptClick(updateButton);

        //waiting for Update form to open
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form-dialog-title']")));

        if (entityType == ZONE) {
            WebElement statusList = findElementByXpath("//label[contains(text(), 'Status')]/parent::div");
            WebDriverUtils.click(statusList);
        } else if (entityType == REGION) {
            WebElement typeList = findElementByXpath("//label[contains(text(), 'Type')]/parent::div");
            WebDriverUtils.click(typeList);
        } else {
            WebElement statusList = findElementByXpath("//label[contains(text(), 'status')]/parent::div");
            WebDriverUtils.click(statusList);
        }

        //update activated status to deactivated or region type UKNOWN to FLOOR
        WebElement deactivatedLabel = findElementByXpath("//*[@id='menu-']/div[3]/ul/li[2]");
        WebDriverUtils.click(deactivatedLabel);

        if (entityType == ZONE) {
            WebElement clickSaveEntityButton = findElementByXpath("//button[2]/span[1][contains(text(),'Save')]");
            WebDriverUtils.click(clickSaveEntityButton);
        } else {
            WebElement clickUpdateEntityButton = findElementByXpath("//button[2]/span[1][contains(text(),'Update')]");
            WebDriverUtils.click(clickUpdateEntityButton);
        }
    }

    private void deleteEntity(String entityName, String[] values, EntityType entityType) {

        assertRecordValues("//tr/td[text() = '" + entityName + "']/ancestor::tr", values);

        WebElement foundRow = findAndAssertRow("//tr/td[text() = '" + entityName + "']/ancestor::tr");

        if (entityType == DOOR || entityType == CAMERA || entityType == DRONE || entityType == SPEAKER || entityType == USER || entityType == UTILITY || entityType == SAFETY) {
            // pressing delete button of that row
            WebElement deleteButton = foundRow.findElement(By.xpath("td[6]/button[@title = 'Delete']"));
            WebDriverUtils.javaScriptClick(deleteButton);
        } else if (entityType == ZONE) {
            WebElement deleteButton = foundRow.findElement(By.xpath("td[4]/button[@title = 'Delete']"));
            WebDriverUtils.javaScriptClick(deleteButton);
        } else {
            WebElement deleteButton = foundRow.findElement(By.xpath("td[5]/button[@title = 'Delete']"));
            WebDriverUtils.javaScriptClick(deleteButton);
        }

            WebElement deleteConfirmation = findElementByXpath("//button[2]/span[1][contains(text(), 'Yes, Delete')]");
            WebDriverUtils.click(deleteConfirmation);

        WebDriverUtils.waitUntil(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//tr/td[text() = '" + entityName + "']/ancestor::tr"), 0));

        List<WebElement> updatedRows = findElementsByXpath("//tr/td[text() = '" + entityName + "']/ancestor::tr");
        Assert.assertEquals(updatedRows.size(), 0);
    }
}

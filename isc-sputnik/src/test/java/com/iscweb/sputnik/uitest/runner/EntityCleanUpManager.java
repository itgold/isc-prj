package com.iscweb.sputnik.uitest.runner;

import com.iscweb.sputnik.uitest.common.EntityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.iscweb.sputnik.uitest.common.EntityType.*;

/**
 * Keeps track of created entities (if registered explicitly) and deleting them after tests are done
 * Note: since it's allowed to have regions with the same name - this will only delete first found region with the name provided
 */
public class EntityCleanUpManager {

    private List<Entity> createdEntities = new ArrayList<>();

    public void registerEntity(String entityName, EntityType type) {
        createdEntities.add(new Entity(entityName, type));
    }

    public void cleanUpCreatedEntities() {
        if (!createdEntities.isEmpty()) {
            for (Entity createdEntity : createdEntities) {
                EntityCleanUpManager.deleteEntity(createdEntity);
            }
            // reset list
            createdEntities = new ArrayList<>();
        }
    }

    public static void deleteEntity(Entity entityToDelete) {
        //making sure we have clean app page (no db or other errors thrown that blocking next step)
        WebDriverUtils.getDriver().navigate().to(BaseTest.getTargetHost());
        WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable
                (By.xpath("//a[contains(@href,'/ui/dashboards/map')]")));

        //clicking on map link to make sure that clicking on admin link in next step is possible(link is clickable)
        //WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href,'/ui/dashboards/map')]"));

        switch (entityToDelete.getType()) {
            case DOOR:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/doors')]"));
                break;
            case CAMERA:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/cameras')]"));
                break;
            case DRONE:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/drones')]"));
                break;
            case USER:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/users')]"));
                break;
            case TAG:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/tags')]"));
                break;
            case REGION:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/regions')]"));
                break;
            case ZONE:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/zones')]"));
                break;
            case SPEAKER:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/speakers')]"));
                break;
            case SAFETY:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/safeties')]"));
                break;
            case UTILITY:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/utilities')]"));
                break;
            case RADIO:
                WebDriverUtils.click(WebDriverUtils.findElementByXpath("//a[contains(@href, '/ui/admin/radios')]"));
                break;
            default:
                throw new IllegalStateException("Entity type clean-up is not supported: " + entityToDelete.getType());
        }

        WebElement foundRow = WebDriverUtils.findRow("//tr/td[text() = '" + entityToDelete.getName() + "']/ancestor::tr");

        if (foundRow != null) {
            // pressing delete button of that row
            if (entityToDelete.getType() == REGION || entityToDelete.getType() == CAMERA || entityToDelete.getType() == SPEAKER) {
                WebElement deleteButton = foundRow.findElement(By.xpath("td[5]/button[2]"));
                WebDriverUtils.click(deleteButton);
            } else if (entityToDelete.getType() == TAG) {
                WebElement deleteButton = foundRow.findElement(By.xpath("td[2]/button[2]"));
                WebDriverUtils.click(deleteButton);
            } else if (entityToDelete.getType() == ZONE) {
                WebElement deleteButton = foundRow.findElement(By.xpath("td[4]/button[2]"));
                WebDriverUtils.click(deleteButton);
            } else {
                WebElement deleteButton = foundRow.findElement(By.xpath("td[6]/button[2]"));
                WebDriverUtils.click(deleteButton);
            }

            WebElement deleteConfirmation = WebDriverUtils.findElementByXpath("//button[2]/span[1][contains(text(), 'Yes, Delete')]");
            WebDriverUtils.click(deleteConfirmation);

            WebDriverUtils.waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//tr/td[contains(text(), '" +
                    entityToDelete.getName() + "')]/ancestor::tr"), 0));

            List<WebElement> updatedRows = WebDriverUtils.findElementsByXpath("//tr/td[contains(text(), '" +
                    entityToDelete.getName() + "')]/ancestor::tr");
            Assert.assertEquals(updatedRows.size(), 0);
        }
    }

    private class Entity {
        private final String name;
        private final EntityType type;

        public Entity(String name, EntityType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public EntityType getType() {
            return type;
        }
    }
}

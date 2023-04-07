package com.iscweb.persistence;

import com.google.common.collect.Sets;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.metadata.AlertSeverity;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.common.model.metadata.DoorType;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.common.model.metadata.DroneType;
import com.iscweb.common.model.metadata.IndexStatus;
import com.iscweb.common.model.metadata.IntegrationStatus;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import com.iscweb.common.model.metadata.SchoolStatus;
import com.iscweb.common.model.metadata.SpeakerStatus;
import com.iscweb.common.model.metadata.SpeakerType;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.persistence.model.jpa.CameraJpa;
import com.iscweb.persistence.model.jpa.CameraTagJpa;
import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.DoorTagJpa;
import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.persistence.model.jpa.IndexJpa;
import com.iscweb.persistence.model.jpa.IntegrationIndexJpa;
import com.iscweb.persistence.model.jpa.IntegrationJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.RoleJpa;
import com.iscweb.persistence.model.jpa.SchoolDistrictIndexJpa;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.model.jpa.SchoolIndexJpa;
import com.iscweb.persistence.model.jpa.SchoolJpa;
import com.iscweb.persistence.model.jpa.SpeakerJpa;
import com.iscweb.persistence.model.jpa.SpeakerTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.UserJpa;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class JpaGenerator {

//  TODO(eric): Do we want randomly generated properties to be realistic at all?
//  private static final int MIN_POLYGON_VERTICES = 4;
//  private static final int MAX_POLYGON_VERTICES = 12;
  private static final Random random = new Random();

  public static AlertJpa randomAlert() {
    AlertJpa alert = new AlertJpa();
    fillTrackedEntityProperties(alert);

    AlertSeverity severity = randomEnum(AlertSeverity.values());

    alert.setDeviceId(UUID.randomUUID().toString());
    alert.setDeviceType(EntityType.CAMERA);
    alert.setSeverity(severity);

    return alert;
  }

  public static RegionJpa randomRegion() {
    RegionJpa region = new RegionJpa();

    fillTrackedEntityProperties(region);

    region.setGeoBoundaries(randomPolygon());
    region.setName(StringUtils.randomAlphanumeric());
    region.setType(randomEnum(RegionType.values()));
    region.setStatus(randomEnum(RegionStatus.values()));

    return region;
  }

  public static SchoolJpa randomSchool() {
    SchoolJpa school = new SchoolJpa();
    fillTrackedEntityProperties(school);

    SchoolDistrictJpa schoolDistrict = randomSchoolDistrict();
    String name = StringUtils.randomAlphanumeric();
    String email = StringUtils.randomAlphanumeric();
    String address = StringUtils.randomAlphanumeric();

    String city = schoolDistrict.getCity();
    String state = schoolDistrict.getState();
    String zip = schoolDistrict.getZipCode();
    String country = schoolDistrict.getCountry();
    RegionJpa region = randomRegion();
    SchoolStatus status = randomEnum(SchoolStatus.values());

    school.setName(name);
    school.setContactEmail(email);
    school.setAddress(address);
    school.setCity(city);
    school.setState(state);
    school.setZipCode(zip);
    school.setCountry(country);
    school.setSchoolDistrict(schoolDistrict);
    school.setRegion(region);
    school.setStatus(status);

    return school;
  }

  public static SchoolDistrictJpa randomSchoolDistrict() {
    SchoolDistrictJpa district = new SchoolDistrictJpa();
    fillTrackedEntityProperties(district);

    String name = StringUtils.randomAlphanumeric();
    String email = StringUtils.randomAlphanumeric();
    String address = StringUtils.randomAlphanumeric();
    String city = StringUtils.randomAlphanumeric();
    String state = StringUtils.randomAlphanumeric();
    String zip = StringUtils.randomAlphanumeric();
    String country = StringUtils.randomAlphanumeric();
    RegionJpa region = randomRegion();
    SchoolDistrictStatus status = randomEnum(SchoolDistrictStatus.values());

    district.setName(name);
    district.setContactEmail(email);
    district.setAddress(address);
    district.setCity(city);
    district.setState(state);
    district.setZipCode(zip);
    district.setCountry(country);
    district.setRegion(region);
    district.setStatus(status);

    return district;
  }

  public static CameraJpa randomCamera() {
    CameraJpa camera = new CameraJpa();
    fillSchoolEntityProperties(camera);

    String externalId = UUID.randomUUID().toString();
    String name = StringUtils.randomAlphanumeric();
    CameraStatus status = randomEnum(CameraStatus.values());
    CameraType type = randomEnum(CameraType.values());

    camera.setExternalId(externalId);
    camera.setName(name);
    camera.setStatus(status);
    camera.setType(type);

    return camera;
  }

  public static CameraTagJpa randomCameraTag() {
    CameraTagJpa cameraTag = new CameraTagJpa();
    fillTrackedEntityProperties(cameraTag);

    CameraJpa camera = randomCamera();
    TagJpa tag = randomTag();

    cameraTag.setEntity(camera);
    cameraTag.setTag(tag);

    return cameraTag;
  }

  public static SpeakerJpa randomSpeaker() {
    SpeakerJpa speakerJpa = new SpeakerJpa();
    fillSchoolEntityProperties(speakerJpa);

    String externalId = UUID.randomUUID().toString();
    SpeakerStatus status = randomEnum(SpeakerStatus.values());
    SpeakerType type = randomEnum(SpeakerType.values());

    String name = StringUtils.randomAlphanumeric();

    speakerJpa.setExternalId(externalId);
    speakerJpa.setStatus(status);
    speakerJpa.setType(type);
    speakerJpa.setName(name);

    return speakerJpa;
  }

  public static SpeakerTagJpa randomSpeakerTag() {
    SpeakerTagJpa speakerTag = new SpeakerTagJpa();
    fillTrackedEntityProperties(speakerTag);

    SpeakerJpa speaker = randomSpeaker();
    TagJpa tag = randomTag();

    speakerTag.setEntity(speaker);
    speakerTag.setTag(tag);

    return speakerTag;
  }

  public static DoorJpa randomDoor() {
    DoorJpa door = new DoorJpa();
    fillSchoolEntityProperties(door);

    String externalId = StringUtils.randomAlphanumeric();
    String name = StringUtils.randomAlphanumeric();
    String description = StringUtils.randomAlphanumeric();
    DoorStatus status = randomEnum(DoorStatus.values());
    DoorType type = randomEnum(DoorType.values());

    DoorConnectionStatus connectionStatus = randomEnum(DoorConnectionStatus.values());
    DoorOnlineStatus onlineStatus = randomEnum(DoorOnlineStatus.values());
    DoorBatteryStatus batteryStatus = randomEnum(DoorBatteryStatus.values());
    DoorTamperStatus tamperStatus = randomEnum(DoorTamperStatus.values());
    DoorOpeningMode openingMode = randomEnum(DoorOpeningMode.values());
    Integer batteryLevel = random.nextInt();
    Boolean updateRequired = random.nextBoolean();

    door.setExternalId(externalId);
    door.setName(name);
    door.setDescription(description);
    door.setStatus(status);
    door.setType(type);
    door.setConnectionStatus(connectionStatus);
    door.setOnlineStatus(onlineStatus);
    door.setBatteryStatus(batteryStatus);
    door.setTamperStatus(tamperStatus);
    door.setOpeningMode(openingMode);
    door.setBatteryLevel(batteryLevel);
    door.setUpdateRequired(updateRequired);

    return door;
  }

  public static DoorTagJpa randomDoorTag() {
    DoorTagJpa doorTag = new DoorTagJpa();
    fillTrackedEntityProperties(doorTag);

    DoorJpa door = randomDoor();
    TagJpa tag = randomTag();

    doorTag.setEntity(door);
    doorTag.setTag(tag);
    doorTag.setType("Door");

    return doorTag;
  }

  public static DroneJpa randomDrone() {
    DroneJpa drone = new DroneJpa();
    fillSchoolEntityProperties(drone);

    String externalId = StringUtils.randomAlphanumeric();
    DroneStatus status = randomEnum(DroneStatus.values());
    DroneType type = randomEnum(DroneType.values());
    Point currentLocation = randomPoint();

    drone.setExternalId(externalId);
    drone.setStatus(status);
    drone.setType(type);
    drone.setCurrentLocation(currentLocation);

    return drone;
  }

  public static IntegrationJpa randomIntegration() {
    IntegrationJpa integration = new IntegrationJpa();
    fillTrackedEntityProperties(integration);

    String name = StringUtils.randomAlphanumeric();
    String description = StringUtils.randomAlphanumeric();
    String connectionParams = StringUtils.randomAlphanumeric();
    String metaParams = StringUtils.randomAlphanumeric();
    IntegrationStatus status = randomEnum(IntegrationStatus.values());

    integration.setName(name);
    integration.setDescription(description);
    integration.setConnectionParams(connectionParams);
    integration.setMetaParams(metaParams);
    integration.setStatus(status);

    return integration;
  }

  public static IntegrationIndexJpa randomIntegrationIndex() {
    IntegrationIndexJpa integrationIndex = new IntegrationIndexJpa();
    fillTrackedEntityProperties(integrationIndex);

    IndexJpa index = randomIndex();
    IntegrationJpa integration = randomIntegration();

    integrationIndex.setIndex(index);
    integrationIndex.setIntegration(integration);

    return integrationIndex;
  }

  public static SchoolDistrictIndexJpa randomSchoolDistrictIndex() {
    SchoolDistrictIndexJpa schoolDistrictIndex = new SchoolDistrictIndexJpa();
    fillTrackedEntityProperties(schoolDistrictIndex);

    IndexJpa index = randomIndex();
    SchoolDistrictJpa schoolDistrict = randomSchoolDistrict();

    schoolDistrictIndex.setIndex(index);
    schoolDistrictIndex.setSchoolDistrict(schoolDistrict);

    return schoolDistrictIndex;
  }

  public static SchoolIndexJpa randomSchoolIndex() {
    SchoolIndexJpa schoolIndex = new SchoolIndexJpa();
    fillTrackedEntityProperties(schoolIndex);

    IndexJpa index = randomIndex();
    SchoolJpa school = randomSchool();

    schoolIndex.setIndex(index);
    schoolIndex.setSchool(school);

    return schoolIndex;
  }

  public static RoleJpa randomRole() {
    RoleJpa role = new RoleJpa();
    fillTrackedEntityProperties(role);

    String name = StringUtils.randomAlphanumeric();
    role.setName(name);

    return role;
  }

  public static UserJpa randomUser() {
    UserJpa user = new UserJpa();
    fillTrackedEntityProperties(user);

    String email = StringUtils.randomAlphanumeric();
    String password = StringUtils.randomAlphanumeric();
    String firstName = StringUtils.randomAlphanumeric();
    String lastName = StringUtils.randomAlphanumeric();
    String imageUrl = StringUtils.randomAlphanumeric();

    UserStatus status = randomEnum(UserStatus.values());
    ZonedDateTime statusDate = ZonedDateTime.now();
    ZonedDateTime activationDate = ZonedDateTime.now();
    ZonedDateTime lastLogin = ZonedDateTime.now();
    SchoolDistrictJpa schoolDistrict = randomSchoolDistrict();
    Set<RoleJpa> roles = Set.of(randomRole());

    user.setEmail(email);
    user.setPassword(password);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setImageUrl(imageUrl);
    user.setStatus(status);
    user.setActivationDate(activationDate);
    user.setLastLogin(lastLogin);
    user.setStatusDate(statusDate);
    user.setSchoolDistrict(schoolDistrict);
    user.setRoles(roles);

    return user;
  }

  public static IndexJpa randomIndex() {
    IndexJpa index = new IndexJpa();
    fillTrackedEntityProperties(index);

    String name = StringUtils.randomAlphanumeric();
    String description = StringUtils.randomAlphanumeric();
    IndexStatus status = randomEnum(IndexStatus.values());

    index.setName(name);
    index.setDescription(description);
    index.setStatus(status);

    return index;
  }

  public static TagJpa randomTag() {
    TagJpa tag = new TagJpa();
    fillTrackedEntityProperties(tag);

    String name = StringUtils.randomAlphanumeric();
    tag.setName(name);

    return tag;
  }

  public static <J extends BaseJpaTrackedEntity> void fillTrackedEntityProperties(J trackedEntity) {
    String guid = UUID.randomUUID().toString();
    ZonedDateTime created = ZonedDateTime.now();
    ZonedDateTime updated = ZonedDateTime.now();

    trackedEntity.setGuid(guid);
    trackedEntity.setCreated(created);
    trackedEntity.setUpdated(updated);
  }

  public static <J extends BaseJpaCompositeEntity> void fillSchoolEntityProperties(J schoolEntity) {
    fillTrackedEntityProperties(schoolEntity);

    RegionJpa region1 = randomRegion();
    RegionJpa region2 = randomRegion();
    Point geoLocation = randomPoint();

    schoolEntity.setRegions(Sets.newHashSet(region1, region2));
    schoolEntity.setGeoLocation(geoLocation);
  }

//  public static <J extends BaseJpaGeoEntity> void fillGeoEntityProperties(J geoEntity) {
//    fillTrackedEntityProperties(geoEntity);
//
//    Random random = new Random();
//
//    Polygon geoBoundaries = randomPolygon();
//    Point geoLocation = randomPoint();
//    Float geoRotation = random.nextFloat();
//    Float geoZoom = random.nextFloat();
//
//    geoEntity.setGeoBoundaries(geoBoundaries);
//    geoEntity.setGeoLocation(geoLocation);
//    geoEntity.setGeoRotation(geoRotation);
//    geoEntity.setGeoZoom(geoZoom);
//  }

  public static <E extends Enum<E>> E randomEnum(E[] enumerations) {
    return enumerations[random.nextInt(enumerations.length)];
  }

  /**
   *
   * @return A random Coordinate object, which will have no Z (z-ordinate) or M (measure)
   */
  public static Coordinate randomCoords() {
    Random random = new Random();

    double x = random.nextDouble();
    double y = random.nextDouble();
    return new CoordinateXY(x, y);
  }

  /**
   *
   * @return A random Point object, which is created using only a Coordinate object
   */
  public static Point randomPoint() {
    Coordinate coords = randomCoords();
    Point point = new GeometryFactory().createPoint(coords);
    point.setSRID(4326);
    return point;
  }

  /**
   *
   * @return A random Polygon object, which is created using only an array of Coordinate objects.
   * It will only have a shell (no holes).
   * For now, we will just draw a random quadrilateral.
   */
  public static Polygon randomPolygon() {

//    int numCoords = random.nextInt(MAX_POLYGON_VERTICES - MIN_POLYGON_VERTICES) +
//        MIN_POLYGON_VERTICES;
//    for (int i=1; i < numCoords - 1; i++) {
//      shell[i] = randomCoords();
//    }
//    shell[numCoords - 1] = startPoint;

    Coordinate[] shell = new Coordinate[5];

    Coordinate startPoint = randomCoords();
    double x = startPoint.getX();
    double y = startPoint.getY();
    shell[0] = startPoint;
    shell[1] = new CoordinateXY(x + 1, y + 1);
    shell[2] = new CoordinateXY(x + 2, y);
    shell[3] = new CoordinateXY(x + 1, y - 1);
    shell[4] = startPoint;

    Polygon polygon = new GeometryFactory().createPolygon(shell);
    polygon.setSRID(4326);

    return polygon;
  }

}

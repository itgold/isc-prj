package com.iscweb.service;

import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.service.entity.IDeviceEntityService;
import com.iscweb.service.entity.RegionEntityService;
import com.iscweb.service.util.DeviceMetaResolver;
import com.iscweb.service.util.meta.BaseDeviceMeta;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

@Transactional(transactionManager = "jpaTransactionManager")
public abstract class BaseDeviceBusinessService<DTO extends BaseExternalEntityDto> implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService regionEntityService;

    public abstract IDeviceEntityService<DTO> getEntityService();

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.CREATE)
    public DTO create(DTO dto, List<TagDto> tags) throws ServiceException {
        return getEntityService().create(dto, tags);
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public DTO update(DTO dto, List<TagDto> tags) throws ServiceException {
        return getEntityService().update(dto, tags);
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.DELETE)
    public void delete(String guid) {
        getEntityService().delete(guid);
    }

    /**
     * Resolves a parent region for a given device. It takes the best guess on deciding
     * what is the parent region for a given device based on the device name.
     *
     * @param entityName device name.
     * @return a guid of parent region if any.
     */
    public String resolveParentRegion(EntityType entityType, String entityName) {
        RegionJpa result = null;
        BaseDeviceMeta deviceMeta = DeviceMetaResolver.resolve(entityType).valueOf(entityName);
        RegionJpa district = getRegionEntityService().findByName(deviceMeta.getDistrict())
                                                     .stream()
                                                     .filter(r -> r.getType().isStatic())
                                                     .findFirst()
                                                     .orElse(null);
        if (district != null) {
            result = district;
            RegionJpa school = getRegionEntityService().findByName(deviceMeta.getSchool(), district)
                                                       .stream()
                                                       .filter(r -> r.getType().isStatic())
                                                       .findFirst()
                                                       .orElse(null);
            if (school != null) {
                result = school;
                RegionJpa building = getRegionEntityService().findByName(deviceMeta.getBuilding(), school)
                                                             .stream()
                                                             .filter(r -> r.getType().isStatic())
                                                             .findFirst()
                                                             .orElse(null);
                if (building != null) {
                    result = building;
                    RegionJpa floor = getRegionEntityService().findByName(deviceMeta.getFloor(), building)
                                                              .stream()
                                                              .filter(r -> r.getType().isStatic())
                                                              .findFirst()
                                                              .orElse(null);
                    if (floor != null) {
                        result = floor;
                        if (StringUtils.notBlank(deviceMeta.getRoom())) {
                            Set<RegionJpa> rooms = getRegionEntityService().findByRegion(floor);
                            int greatestMatch = minPrefix(deviceMeta.getRoom()) - 1;
                            RegionJpa roomCandidate = null;
                            for (RegionJpa room : rooms) {
                                // prioritize exact room match over all others
                                if (room.getName().equalsIgnoreCase(deviceMeta.getRoom())) {
                                    roomCandidate = room;
                                    break;
                                } else { //prioritize match by longest prefix
                                    int commonPrefixLength = greatestCommonPrefixLength(deviceMeta.getRoom(), room.getName());
                                    if (commonPrefixLength > 0 && commonPrefixLength > greatestMatch) {
                                        greatestMatch = commonPrefixLength;
                                        roomCandidate = room;
                                    }
                                }
                            }

                            if (roomCandidate != null) {
                                result = roomCandidate;
                            }
                        }
                    }
                }
            }
        }

        return result != null ? result.getGuid() : null;
    }

    /**
     * Returns smallest prefix that needs to be considered as sufficient for matching with the room.
     *
     * @param deviceName name of the device.
     * @return min amount of characters to be used for matching with rooms.
     */
    protected int minPrefix(@Nonnull String deviceName) {
        Integer result = null;
        for (int i = 0; i < deviceName.length(); i++) {
            if (!Character.isDigit(deviceName.charAt(i))) {
                result = i;
                break;
            }
        }
        if (result == null) {
            result = deviceName.length();
        }
        return result;
    }


    /**
     * Checks for the greatest common prefix of device name and room name and returns its length.
     *
     * @param deviceName name of the device.
     * @param roomName   name of the room.
     * @return the length of the common part.
     */
    protected int greatestCommonPrefixLength(String deviceName, String roomName) {
        Integer result = null;

        int minLength = Math.min(deviceName.length(), roomName.length());
        for (int i = 0; i < minLength; i++) {
            if (deviceName.charAt(i) != roomName.charAt(i)) {
                result = deviceName.substring(0, i).length();
                break;
            }
        }
        if (result == null) {
            result = deviceName.substring(0, minLength).length();
        }

        return result;
    }

}

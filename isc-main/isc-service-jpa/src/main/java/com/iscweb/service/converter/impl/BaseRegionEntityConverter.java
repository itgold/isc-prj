package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import com.iscweb.service.util.GeoUtils;

import java.util.Set;

import static com.iscweb.common.util.ObjectUtils.ID_NONE;

public abstract class BaseRegionEntityConverter<D extends BaseSchoolEntityDto, J extends BaseJpaCompositeEntity> extends BaseConverter<D, J> {

    protected BaseRegionEntityConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected J toJpa() {
        J result = super.toJpa();
        D dto = getDto();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.METADATA)) {
                if (dto.isModified("geoLocation")) {
                    result.setGeoLocation(GeoUtils.convert(dto.getGeoLocation()));
                }
            }

            if (getScope().gte(Scope.ALL)) {
                if (dto.isModified("parentIds")) {
                    RegionJpaRepository regionRepository = getConvertContext().getRegionRepository();
                    if (dto.getParentIds() != null) {
                        Set<RegionJpa> regions = regionRepository.findByGuidIn(dto.getParentIds());
                        result.getRegions().clear();
                        regions.forEach(result::addRegion);
                    } else {
                        result.addRegion(regionRepository.findById(ID_NONE).orElseThrow());
                    }
                }
            }
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @SuppressWarnings("Duplicates")
    @Override
    public D toDto() {
        D result = super.toDto();
        J jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.METADATA)) {
                if (jpa.getGeoLocation() != null) {
                    result.setGeoLocation(GeoUtils.convert(jpa.getGeoLocation()));
                }
            }

            if (getScope().gte(Scope.ALL)) {
                if (jpa.getRegions() != null) {
                    for (IRegion region : jpa.getRegions()) {
                        if (region != null) {
                            result.addParent(region.getGuid());
                        }
                    }
                }
            }
        }

        return result;
    }
}

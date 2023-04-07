package com.iscweb.service.entity;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.dto.entity.core.SchoolSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.model.entity.ISchool;
import com.iscweb.common.model.entity.ISchoolDistrict;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.common.model.metadata.SchoolStatus;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SchoolJpa;
import com.iscweb.persistence.repositories.impl.SchoolJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.util.MiscUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

/**
 * Service class for schools.
 *
 * @author skurenkov
 */
@Slf4j
@Service
public class SchoolEntityService extends BaseJpaEntityService<SchoolJpaRepository, ISchool> implements EntityService<SchoolDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictEntityService schoolDistrictEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService regionEntityService;

    public SchoolDto create(SchoolDto schoolDto) {
        SchoolJpa result = Convert.my(schoolDto)
                                  .scope(Scope.ALL)
                                  .attr(GUID, true)
                                  .boom();

        RegionJpa region = createRegion(schoolDto.getName(), result.getSchoolDistrict());
        result.setRegion(region);

        result = this.createOrUpdate(result);

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public SchoolDto update(SchoolDto schoolDto) {
        ISchool result = Convert.my(schoolDto)
                                  .withJpa(() -> findByGuid(schoolDto.getId()))
                                  .scope(Scope.ALL).boom();

        result = this.update(result);
        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public ISchool update(ISchool school) {

        SchoolJpa result = (SchoolJpa) school;

        if (result.getRegion() == null) {
            IRegion defaultRegion = getRegionEntityService().fetchGlobalRootRegion();
            result.setRegion((RegionJpa) defaultRegion);
        }

        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    public void delete(String guid) {
        // We may want to re-assign all entities (doors, cameras, drones, etc) to NO school
        // and NO region and delete all regions.
        SchoolJpa school = (SchoolJpa) findByGuid(guid);
        if (school != null) {
            school.setStatus(SchoolStatus.DEACTIVATED);
            this.update(school);
        }
    }

    public ISchool findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    public PageResponseDto<SchoolDto> findSchools(QueryFilterDto filter, Pageable paging) {
        Page<ISchool> page = getRepository().findEntities(filter, paging);

        return SchoolSearchResultDto.builder()
                                    .numberOfItems((int) page.getTotalElements())
                                    .numberOfPages(page.getTotalPages())
                                    .data(page.getContent()
                                              .stream()
                                              .map(door -> (SchoolDto) Convert.my(door).scope(Scope.ALL).boom())
                                              .collect(Collectors.toList())).build();
    }

    public List<SchoolDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(school -> (SchoolDto) Convert.my(school).scope(Scope.ALL).boom())
                              .collect(Collectors.toList());
    }

    /**
     * Creates new school region object.
     *
     * @param name           the school name.
     * @param schoolDistrict school district associated with the school if any.
     * @return a new region jpa object.
     */
    private RegionJpa createRegion(String name, @Nullable ISchoolDistrict schoolDistrict) {
        RegionDto result = new RegionDto();
        result.setName(name + " Region");
        result.setId(MiscUtils.generateGuid());
        result.setType(RegionType.SCHOOL);
        result.setStatus(RegionStatus.DEACTIVATED);

        //setting the school's parent region.
        IRegion schoolRegionParent;
        if (schoolDistrict != null) {
            schoolRegionParent = schoolDistrict.getRegion();
        } else {
            schoolRegionParent = getRegionEntityService().fetchGlobalRootRegion();
        }
        result.setParentIds(Sets.newHashSet(schoolRegionParent.getGuid()));
        result = getRegionEntityService().create(result);
        return (RegionJpa) getRegionEntityService().findByGuid(result.getId());
    }

    public SchoolDto findByRegionGuid(String regionGuid) {
        SchoolJpa school = getRepository().findByRegionGuid(regionGuid);
        return null != school ? (SchoolDto) Convert.my(school).scope(Scope.ALL).boom() : null;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}

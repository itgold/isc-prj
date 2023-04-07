package com.iscweb.service.entity;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DistrictSearchResultDto;
import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.model.entity.ISchoolDistrict;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

/**
 * Service class for school districts.
 *
 * @author skurenkov
 */
@Slf4j
@Service
public class SchoolDistrictEntityService extends BaseJpaEntityService<SchoolDistrictJpaRepository, ISchoolDistrict> implements EntityService<SchoolDistrictDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService regionEntityService;

    public ISchoolDistrict findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    public PageResponseDto<SchoolDistrictDto> findDistricts(QueryFilterDto filter, Pageable paging) {
        Page<ISchoolDistrict> page = getRepository().findEntities(filter, paging);

        return DistrictSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(door -> (SchoolDistrictDto) Convert.my(door).scope(Scope.ALL).boom())
                        .collect(Collectors.toList())).build();
    }

    public SchoolDistrictDto create(SchoolDistrictDto schoolDistrictDto) {
        SchoolDistrictJpa result = Convert.my(schoolDistrictDto)
                                          .scope(Scope.ALL)
                                          .attr(GUID, true)
                                          .boom();
        RegionJpa region = createRegion(schoolDistrictDto.getName());
        result.setRegion(region);

        result = this.createOrUpdate(result);
        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public SchoolDistrictDto update(SchoolDistrictDto schoolDistrictDto) {

        SchoolDistrictJpa result = Convert.my(schoolDistrictDto)
                                          .withJpa(() -> findByGuid(schoolDistrictDto.getId()))
                                          .scope(Scope.ALL)
                                          .boom();

        return Convert.my(this.update(result)).scope(Scope.ALL).boom();
    }

    public ISchoolDistrict update(ISchoolDistrict schoolDistrict) {

        SchoolDistrictJpa result = (SchoolDistrictJpa) schoolDistrict;

        if (result.getRegion() == null) {
            IRegion defaultRegion = getRegionEntityService().fetchGlobalRootRegion();
            result.setRegion((RegionJpa) defaultRegion);
        }

        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    public void delete(String guid) {
        SchoolDistrictJpa schoolDistrict = (SchoolDistrictJpa) findByGuid(guid);
        if (schoolDistrict != null) {
            schoolDistrict.setStatus(SchoolDistrictStatus.DEACTIVATED);
            this.update(schoolDistrict);
        }
    }

    public List<SchoolDistrictDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                .stream()
                .map(door -> (SchoolDistrictDto) Convert.my(door).scope(Scope.ALL).boom())
                .collect(Collectors.toList());
    }

    /**
     * Creates new school region object.
     * @param name the school name.
     * @return a new region jpa object.
     */
    private RegionJpa createRegion(String name) {
        RegionJpa result = new RegionJpa();
        result.setName(name + " Region");
        result.setGuid(MiscUtils.generateGuid());
        result.setType(RegionType.SCHOOL_DISTRICT);
        result.setStatus(RegionStatus.DEACTIVATED);

        //setting the school district's parent region.
        IRegion schoolRegionParent = getRegionEntityService().fetchGlobalRootRegion();
        result.setRegions(Sets.newHashSet((RegionJpa) schoolRegionParent));

        getRegionEntityService().createOrUpdate(result);

        return result;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}

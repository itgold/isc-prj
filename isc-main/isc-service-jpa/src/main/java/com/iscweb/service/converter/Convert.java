package com.iscweb.service.converter;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.IContextObject;
import com.iscweb.common.model.dto.IEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.persistence.repositories.impl.RoleJpaRepository;
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository;
import com.iscweb.service.converter.impl.*;
import com.iscweb.service.converter.user.ExternalUserConverter;
import com.iscweb.service.converter.user.UserConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * A factory class for converters API.
 * Should be used to access application converters.
 * <pre>
 * {@code
 *  Convert.my(enclave).setScope(Scope.USER).boom()
 * }
 * </pre>
 *
 * @author skurenkov
 */
public class Convert {

    /**
     * Attribute used to indicate that converter should generate GUID for entity.
     */
    public static final String GUID = "GUID";

    /**
     * Context that converters can access to use specific application beans.
     */
    public static class ConvertContext implements IContextObject {

        @Getter
        @Setter
        private RoleJpaRepository roleRepository;

        @Getter
        @Setter
        private RegionJpaRepository regionRepository;

        @Getter
        @Setter
        private SchoolDistrictJpaRepository schoolDistrictRepository;

    }

    /**
     * A singleton instance of the converter.
     */
    private static final Convert INSTANCE = new Convert();

    /**
     * @see Convert.ConvertContext
     */
    private final ConvertContext context;

    /**
     * A private constructor.
     * Use one of the factory methods to get a reference to the real converter.
     */
    private Convert() {
        this.context = new ConvertContext();
    }

    /**
     * Returns the singleton instance of the converter.
     *
     * @return this class instance.
     */
    private static Convert get() {
        return INSTANCE;
    }

    /**
     * A converter factory method which operates on a DTO.
     *
     * @param dto object to be set as a source.
     * @param <E> target entity type for conversion.
     * @return new instance of the converter initialized with a given dto object.
     */
    @SuppressWarnings("unchecked")
    public static <E extends IApplicationEntity, C extends BaseConverter<? extends IEntityDto, E>> C my(IEntityDto dto) {
        return (C) get().converterFor(dto);
    }

    /**
     * A converter factory method which operates on a JPA.
     *
     * @param jpa object to be set as a source.
     * @param <D> target dto type for conversion.
     * @return new instance of the converter initialized with a given jpa object.
     */
    @SuppressWarnings("unchecked")
    public static <D extends IEntityDto, C extends BaseConverter<D, ? extends IApplicationEntity>> C my(IApplicationEntity jpa) {
        return (C) get().converterFor(jpa);
    }

    /**
     * Converter initialization logic which should be called during the application startup.
     *
     * @param roleRepository role repository reference.
     */
    public static void init(RoleJpaRepository roleRepository,
                            RegionJpaRepository regionRepository,
                            SchoolDistrictJpaRepository schoolDistrictRepository) {

        Convert factory = get();
        factory.initContext(roleRepository,
                            regionRepository,
                            schoolDistrictRepository);
    }

    private void initContext(RoleJpaRepository roleRepository,
                             RegionJpaRepository regionRepository,
                             SchoolDistrictJpaRepository schoolDistrictRepository) {

        context.setRoleRepository(roleRepository);
        context.setRegionRepository(regionRepository);
        context.setSchoolDistrictRepository(schoolDistrictRepository);
    }

    /**
     * Creates a new converter from an object in the application model.
     *
     * @param modelObject model object that .
     * @param <C> converter type.
     * @return new converter.
     */
    private <D extends IEntityDto,
            E extends IApplicationEntity,
            C extends BaseConverter<D, E>> C converterFor(IApplicationModel modelObject) {

        C result;

        ConverterType converterType = modelObject.getConverterType();
        result = getConverterByType(converterType);
        result.setSource(modelObject);

        return result;
    }

    /**
     * Creates a particular converter object from the provided converter type and initializes it.
     *
     * @param converterType type of converter to create and initialize.
     * @param <C> returning converter type.
     * @return new converter instance.
     */
    @SuppressWarnings("unchecked")
    private <D extends IEntityDto,
             E extends IApplicationEntity,
             C extends BaseConverter<D, E>> C getConverterByType(ConverterType converterType) {

        C result;
        switch (converterType) {
            case USER:
                result = (C) new UserConverter(this.context);
                break;
            case EXTERNAL_USER:
                result = (C) new ExternalUserConverter(this.context);
                break;
            case ROLE:
                result = (C) new RoleConverter(this.context);
                break;
            case INDEX:
                result = (C) new IndexConverter<>(this.context);
                break;
            case SCHOOL_DISTRICT:
                result = (C) new SchoolDistrictConverter(this.context);
                break;
            case SCHOOL:
                result = (C) new SchoolConverter(this.context);
                break;
            case REGION:
                result = (C) new RegionConverter(this.context);
                break;
            case DOOR:
                result = (C) new DoorConverter(this.context);
                break;
            case CAMERA:
                result = (C) new CameraConverter(this.context);
                break;
            case SPEAKER:
                result = (C) new SpeakerConverter(this.context);
                break;
            case DRONE:
                result = (C) new DroneConverter(this.context);
                break;
            case TAG:
                result = (C) new TagConverter(this.context);
                break;
            case UTILITY:
                result = (C) new UtilityConverter(this.context);
                break;
            case SAFETY:
                result = (C) new SafetyConverter(this.context);
                break;
            case RADIO:
                result = (C) new RadioConverter(this.context);
                break;
            case ALERT:
                result = (C) new AlertConverter(this.context);
                break;
            case ALERT_TRIGGER:
                result = (C) new AlertTriggerConverter(this.context);
                break;
            default:
                throw new IllegalArgumentException("Invalid converter factory parameter: " + converterType);
        }
        return result;
    }
}

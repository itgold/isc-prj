package com.iscweb.service.converter;

import com.google.common.collect.Maps;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.IEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.service.util.MiscUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Supplier;

import static com.iscweb.service.converter.Convert.GUID;

/**
 * Base converter class that implements base functionality used by all converter.
 * Provides converter context with repositories and services used and a common api for converters.
 * @param <D> DTO.
 * @param <J> JPA.
 */
@Slf4j
public abstract class BaseConverter<D extends IEntityDto, J extends IApplicationEntity> {

    @Getter(AccessLevel.PROTECTED)
    private D dto;

    @Getter(AccessLevel.PROTECTED)
    private J jpa;

    /**
     * The type of entity requested converter instance is made for.
     */
    @Getter
    private ConverterType type;

    /**
     * The scope determines the amount of data that will be used in the conversion.
     */
    @Getter(AccessLevel.PROTECTED)
    private Scope scope = Scope.BASIC;

    /**
     * The scope determines the amount of sensitive data that will be used in the conversion.
     */
    @Getter(AccessLevel.PROTECTED)
    private SecurityLevel securityLevel = SecurityLevel.BASIC;

    @Getter(AccessLevel.PROTECTED)
    private final Convert.ConvertContext convertContext;

    /**
     * Attributes that can be used during the conversion process.
     */
    @Getter
    @Setter
    private Map<String, Object> attributes = Maps.newHashMap();

    /**
     * Creates a new instance of the converter. It has a back reference to its parent context.
     * This context provides access to repositories and system objects when needed.
     *
     * @param convertContext {@link Convert} object that provides context to this converter
     */
    protected BaseConverter(Convert.ConvertContext convertContext) {
        this.convertContext = convertContext;
    }

    protected abstract J createJpa();

    protected abstract D createDto();

    /**
     * Initialization method for the converter's source.
     *
     * @param modelObject object to be set as a source for this converter.
     */
    @SuppressWarnings("unchecked")
    public void setSource(IApplicationModel modelObject) {
        if (modelObject == null) {
            throw new IllegalArgumentException("Model object cannot be null");
        }
        this.type = modelObject.getConverterType();
        if (modelObject instanceof IEntityDto) {
            this.dto = (D) modelObject;
        } else if (modelObject instanceof IApplicationEntity) {
            this.jpa = (J) modelObject;
        }
    }

    /**
     * The final frontier in JPA/DTO conversion.
     *
     * @param <A> application model type that defines the returned entity.
     * @return converted entity of given type.
     */
    @SuppressWarnings("unchecked")
    public <A extends IApplicationModel> A boom() {
        A result = null;

        if (getDto() != null) {
            this.jpa = toJpa();
            result = (A) getJpa();
        } else if (getJpa() != null) {
            this.dto = toDto();
            result = (A) getDto();
        }

        return result;
    }

    /**
     * Template method for converting DTO to JPA.
     *
     * Populates sensitive data according with the {@link Scope} set during converter initialization in order
     * to avoid processing unnecessary sensitive data. By default, this method applies basic sensitive data,
     * but it can be overwritten and implemented in a subclass.
     *
     * @return initialized jpa object instance.
     */
    protected J toJpa() {
        J result = this.jpa != null ? this.jpa : createJpa();
        D dto = getDto();

        if (result != null && dto != null) {
            switch (getSecurityLevel()) {
                case ALL:
                    if (dto.getRowId() != null) {
                        result.setId(dto.getRowId());
                    }
                    //fall through
                case USER:
                    if (dto.getCreated() != null) {
                        result.setCreated(dto.getCreated());
                    }
                    //fall through
                case BASIC:
                    if (dto.getUpdated() != null) {
                        result.setUpdated(dto.getUpdated());
                    }
                    //fall through
                case IDENTITY:
                    result.setGuid(dto.getId());
                    //fall through
                default:
                    break;
            }

            Boolean generateGuid = attr(GUID);
            if (result.getGuid() == null && generateGuid != null && generateGuid) {
                result.setGuid(MiscUtils.generateGuid());
            }
        }


        return result;
    }

    /**
     * Template method for converting JPA to DTO.
     *
     * Populates sensitive data in accordance with the provided {@link Scope} during converter initialization
     * in order to avoid processing unnecessary sensitive data.
     * By default, this method applies basic sensitive data, but it can be overwritten and implemented in a subclass.
     *
     * @return new and initialized dto object instance.
     */
    protected D toDto() {
        D result = null;
        J jpa = getJpa();

        if (jpa != null) {
            result = createDto();
            switch (getSecurityLevel()) {
                case ALL:
                    result.setRowId(jpa.getId());
                    result.setCreated(jpa.getCreated());
                    //fall through
                case USER:
                    //fall through
                case BASIC:
                    result.setUpdated(jpa.getUpdated());
                    //fall through
                case IDENTITY:
                    result.setId(jpa.getGuid());
                    //fall through
                default:
                    break;
            }
        }

        return result;
    }

    /**
     * Initializes an attribute that has to be used during the conversion.
     * Attributes are objects stored in the converter scope and are initialized as String/Object pairs.
     *
     * @param name  attribute name.
     * @param value attribute value.
     * @return a chaining reference to the same converter instance.
     */
    public BaseConverter<D, J> attr(String name, Object value) {
        this.getAttributes().put(name, value);
        return this;
    }

    /**
     * An accessor for attributes (set by {@link #attr(String, Object)}).
     *
     * @param name name of attribute to retrieve
     * @param <A>  type of the attribute to cast to
     * @return an attribute value of the given type if found
     */
    @SuppressWarnings("unchecked")
    public <A> A attr(String name) {
        return (A) getAttributes().get(name);
    }

    /**
     * Sets the scope for the resulting entity for this converter instance.
     * Can be used to indicate how much sensitive data is added to resulting object.
     *
     * @return a chaining reference to the same converter.
     */
    public BaseConverter<D, J> scope(Scope scope) {
        this.scope = scope;
        log.trace("Converter scope has been set to {}", scope);
        return this;
    }

    public BaseConverter<D, J> securityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
        log.trace("Converter security level has been set to {}", securityLevel);
        return this;
    }

    /**
     * Do not create new Jpa object but use provided one when do dto to jpa conversion.
     *
     * @param originalJpa Jpa object to use.
     * @return a chaining reference to the same converter.
     */
    public BaseConverter<D, J> withJpa(J originalJpa) {
        this.jpa = originalJpa;
        return this;
    }

    public BaseConverter<D, J> withJpa(Supplier<J> supplier) {
        this.jpa = supplier.get();
        return this;
    }
}

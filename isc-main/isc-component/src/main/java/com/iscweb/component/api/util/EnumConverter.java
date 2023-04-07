package com.iscweb.component.api.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iscweb.common.exception.InvalidValueException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles conversion of strings to enums.
 */
public class EnumConverter implements ConditionalGenericConverter {

    /**
     * See {@link ConditionalGenericConverter#getConvertibleTypes()}.
     *
     * @return the set of {@link ConvertiblePair} objects which this class convert between.
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Sets.newHashSet(new ConvertiblePair(String.class, Enum.class));
    }

    /**
     * See {@link ConditionalGenericConverter#convert(Object, TypeDescriptor, TypeDescriptor)}.
     *
     * @param source     The string being converted.
     * @param sourceType A TypeDescriptor for String.
     * @param targetType A TypeDescriptor for the Enum being converted to (also contains
     *                   annotation metadata).
     * @return The result of the conversion (will be an enum value).
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"}) //working with raw Enum types to convert any enum object
    public Object convert(Object source, @NonNull TypeDescriptor sourceType, TypeDescriptor targetType) {
        Enum result = null;
        Class<Enum> enumType = (Class<Enum>) targetType.getType();

        if (source != null) {
            // Normalize source string.
            String sourceString = ((String) source).trim().toUpperCase();

            // Check whether mapping annotations have been declared.
            Mapping mapping = targetType.getAnnotation(Mapping.class);
            if (mapping != null) {
                for (Mapped mapped : mapping.mappings()) {
                    // If source string is remapped using annotation, replace with mapped value.
                    if (sourceString.equalsIgnoreCase(mapped.source())) {
                        sourceString = mapped.value();

                        // It would be nice if it were possible to check this at compile time, but it does not
                        // appear possible to do so while still handling all enums in a generic way.
                        if (!EnumUtils.isValidEnum(enumType, sourceString)) {
                            throw new IllegalArgumentException(
                                    String.format("Value %s used in @Mapped annotation is invalid", sourceString));
                        }
                        break;
                    }
                }
            }

            try {
                result = Enum.valueOf(enumType, sourceString);
            } catch (IllegalArgumentException ex) {
                Set<String> allowedValues = Arrays.stream(targetType.getType().getEnumConstants())
                                                  .map(it -> ((Enum) it).name())
                                                  .collect(Collectors.toSet());


                if (mapping != null) {
                    for (Mapped mapped : mapping.mappings()) {
                        if (EnumUtils.isValidEnum(enumType, mapped.value())) {
                            allowedValues.remove(mapped.value());
                        } else {
                            // It would be better if this were a compile-time check.
                            throw new IllegalArgumentException(
                                    String.format("Value %s used in @Mapped annotation is invalid.", mapped.value()));
                        }
                    }
                    allowedValues.addAll(Arrays.stream(mapping.mappings())
                                               .map(Mapped::source)
                                               .map(String::toUpperCase)
                                               .collect(Collectors.toSet()));
                }

                List<String> allowedValuesList = Lists.newArrayList(allowedValues);
                Collections.sort(allowedValuesList);

                throw new InvalidValueException((String) source, allowedValuesList);
            }
        }

        return result;
    }

    /**
     * See {@link ConditionalGenericConverter#matches(TypeDescriptor, TypeDescriptor)}.
     *
     * @param sourceType A TypeDescriptor for the source.
     * @param targetType A TypeDescriptor for the target.
     * @return true if the sourceType is String and target type is Enum
     */
    @Override
    public boolean matches(TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return String.class.isAssignableFrom(sourceType.getType()) &&
                Enum.class.isAssignableFrom(targetType.getType());
    }

    /**
     * This annotation can be put on an Enum field to customize the mapping
     * from string to enum.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Mapping {
        Mapped[] mappings();
    }

    /**
     * Indicates that the String given by 'source()' should be mapped to the
     * enum value with name 'value()'.
     */
    public @interface Mapped {
        String source();

        String value();
    }
}

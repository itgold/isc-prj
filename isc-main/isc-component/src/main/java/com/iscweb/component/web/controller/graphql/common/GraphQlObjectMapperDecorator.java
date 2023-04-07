package com.iscweb.component.web.controller.graphql.common;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.iscweb.common.model.dto.ITrackableDto;
import com.iscweb.common.util.TrackableDtoProxy;
import graphql.kickstart.tools.PerFieldObjectMapperProvider;
import graphql.language.FieldDefinition;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Custom <code>ObjectMapper</code> decorator which adds automatic change tracking for all objects which
 * implement the <code>ITrackableDto</code> interface.
 */
public class GraphQlObjectMapperDecorator implements PerFieldObjectMapperProvider {

    private final PerFieldObjectMapperProvider defaultProvider;

    public GraphQlObjectMapperDecorator(PerFieldObjectMapperProvider defaultProvider) {
        this.defaultProvider = defaultProvider;
    }

    /**
     * @see PerFieldObjectMapperProvider#provide(FieldDefinition)
     */
    @NotNull
    @Override
    public ObjectMapper provide(@NotNull FieldDefinition fieldDefinition) {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public BeanDeserializerBuilder updateBuilder(DeserializationConfig config,
                                                         BeanDescription beanDesc,
                                                         BeanDeserializerBuilder builder) {

                if (ITrackableDto.class.isAssignableFrom(beanDesc.getBeanClass())) {
                    if (builder.getValueInstantiator() instanceof StdValueInstantiator) {
                        builder.setValueInstantiator(
                                new CustomStdValueInstantiator((StdValueInstantiator) builder.getValueInstantiator())
                        );
                    }
                }

                return builder;
            }
        });

        ObjectMapper mapper = defaultProvider.provide(fieldDefinition);
        mapper.registerModule(module);

        return mapper;
    }

    private static class CustomStdValueInstantiator extends StdValueInstantiator {

        public CustomStdValueInstantiator(StdValueInstantiator src) {
            super(src);
        }

        @Override
        public Object createUsingDefault(DeserializationContext context) throws IOException {
            Object instance = super.createUsingDefault(context);

            if (instance instanceof ITrackableDto && !(instance instanceof TrackableDtoProxy)) {
                instance = TrackableDtoProxy.getProxy((ITrackableDto) instance);
            }

            return instance;
        }
    }

}

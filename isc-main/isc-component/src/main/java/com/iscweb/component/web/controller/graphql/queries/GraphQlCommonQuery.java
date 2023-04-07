package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.TupleBaseDto;
import com.iscweb.common.service.integration.DeviceStateDictionary;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GraphQL query resolver for users.
 */
@Slf4j
@Component
public class GraphQlCommonQuery implements GraphQLQueryResolver {

    public List<DeviceStateTuple> deviceCodes() {
        return List.of(DeviceStateDictionary.values())
                .stream()
                .map(e -> new DeviceStateTuple(e.name(), e))
                .collect(Collectors.toList());
    }

    public static class DeviceStateTuple extends TupleBaseDto<String, DeviceStateDictionary> {
        public DeviceStateTuple(String name, DeviceStateDictionary e) {
            super(name, e);
        }
    }
}

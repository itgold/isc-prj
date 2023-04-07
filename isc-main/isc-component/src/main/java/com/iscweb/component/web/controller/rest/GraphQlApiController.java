package com.iscweb.component.web.controller.rest;

import com.iscweb.common.util.UserUtils;
import com.iscweb.component.web.controller.graphql.common.BaseGraphQlController;
import com.iscweb.component.web.controller.graphql.mutations.*;
import com.iscweb.component.web.controller.graphql.queries.*;
import com.iscweb.service.security.IscPrincipal;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/rest/graphql")
public class GraphQlApiController extends BaseGraphQlController {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSchoolDistrictQuery schoolDistrictQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSchoolQuery schoolQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSchoolMutation schoolMutationResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSchoolEntityQuery schoolEntityQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSchoolEntityMutation schoolEntityMutationResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlUserQuery userQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlTagsQuery tagsQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlTagMutation tagMutationResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlDoorTagsQuery doorTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlCameraTagsQuery cameraTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlDroneTagsQuery droneTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSpeakerTagsQuery speakerTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlUserMutation userMutationResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlDeviceActionMutation deviceActionMutation;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlCommonQuery commonQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlUtilityTagsQuery utilityTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlSafetyTagsQuery safetyTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private GraphQlRadioTagsQuery radioTagsResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    GraphQlEventsQuery eventsQueryResolver;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    GraphQlEventsMutation eventsMutation;

    @Getter
    private GraphQL adminGraphQlApi;

    @Getter
    private GraphQL nonAdminGraphQlApi;

    @PostConstruct
    public void initialize() {
        this.adminGraphQlApi = buildEngine(buildAdminSchema());
        this.nonAdminGraphQlApi = buildEngine(buildNotAdminSchema());
    }

    @Override
    protected GraphQL getGraphQl(IscPrincipal principal) {
        GraphQL result = getNonAdminGraphQlApi();
        if (UserUtils.isAdmin(principal)) {
            result = getAdminGraphQlApi();
        }

        return result;
    }

    protected GraphQLSchema buildAdminSchema() {
        return SchemaParser.newParser()
                .file("common.graphql")
                .file("events.graphql")
                .file("schoolsEntities.graphql")
                .file("schools.graphql")
                .file("tags.graphql")
                .file("safeties.graphql")
                .file("users.graphql")
                .file("adminSchema.graphql")
                .file("utilities.graphql")
                .resolvers(
                        getCommonQueryResolver(),
                        getEventsQueryResolver(),
                        getSchoolQueryResolver(),
                        getSchoolEntityQueryResolver(),
                        getSchoolDistrictQueryResolver(),
                        getTagsQueryResolver(),
                        getUserQueryResolver(),
                        getDoorTagsResolver(),
                        getCameraTagsResolver(),
                        getDroneTagsResolver(),
                        getSpeakerTagsResolver(),
                        getUtilityTagsResolver(),
                        getSafetyTagsResolver(),
                        getRadioTagsResolver(),
                        getSchoolMutationResolver(),
                        getSchoolEntityMutationResolver(),
                        getUserMutationResolver(),
                        getTagMutationResolver(),
                        getDeviceActionMutation(),
                        getEventsMutation()
                )
                .scalars(
                        ExtendedScalars.DateTime
                )
                .options(parserOptions())
                .build()
                .makeExecutableSchema();
    }

    protected GraphQLSchema buildNotAdminSchema() {
        return SchemaParser.newParser()
                .file("common.graphql")
                .file("events.graphql")
                .file("schoolsEntities.graphql")
                .file("schools.graphql")
                .file("tags.graphql")
                .file("allSchema.graphql")
                .resolvers(
                        getCommonQueryResolver(),
                        getEventsQueryResolver(),
                        getSchoolQueryResolver(),
                        getSchoolEntityQueryResolver(),
                        getSchoolDistrictQueryResolver(),
                        getTagsQueryResolver(),
                        getDoorTagsResolver(),
                        getCameraTagsResolver(),
                        getDroneTagsResolver(),
                        getSpeakerTagsResolver(),
                        getRadioTagsResolver(),
                        getDeviceActionMutation()
                )
                .scalars(
                        ExtendedScalars.DateTime
                )
                .options(parserOptions())
                .build()
                .makeExecutableSchema();
    }
}

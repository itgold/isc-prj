package com.iscweb.component.web.util;

import com.google.common.collect.Lists;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
import com.iscweb.service.security.IscPrincipal;
import graphql.GraphQLContext;
import graphql.execution.MergedField;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import graphql.language.FragmentSpread;
import graphql.language.InlineFragment;
import graphql.language.Selection;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class to deal with the GraphQL engine.
 */
@Slf4j
public final class GraphQlUtils {

    public static final String PRINCIPAL = "principal";
    private static final String COLUMN_ID = "id";

    private static final List<String> IGNORED_FIELDS = Lists.newArrayList("__typename");

    /**
     * Get the current user executing the GraphQL operation.
     *
     * @param env GraphQL resolver execution context
     * @return Current principal
     */
    public static IscPrincipal currentUser(DataFetchingEnvironment env) {
        GraphQLContext context = env.getContext();
        return context.get(PRINCIPAL);
    }

    /**
     * Get the current list of user roles.
     *
     * @param env GraphQL resolver execution context
     * @return List of user roles
     */
    public static List<String> currentUserRoles(DataFetchingEnvironment env) {
        IscPrincipal principal = currentUser(env);
        return principal != null ? principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * Check if user executing GraphQL the operation has a specific role.
     *
     * @param role Role to check.
     * @param env GraphQL resolver execution context
     * @return <code>true</code> if the user has specified role and <code>false</code> otherwise.
     */
    public static boolean hasRole(String role, DataFetchingEnvironment env) {
        return GraphQlUtils.currentUserRoles(env).contains(role);
    }

    /**
     * List all fields selected in query and expected by UI to return.
     * Can be used for projections when do JPA queries.
     *
     * @param env GraphQL resolver execution context
     * @return List of field been selected to return back for query/mutation results
     */
    public static List<ProjectionDto> queryFieldsSelection(DataFetchingEnvironment env) {
        String fieldName = env.getField().getName();
        List<ProjectionDto> result = null;
        MergedField fields = env.getMergedField();
        Field field = fields.getFields().stream().filter(f -> StringUtils.equals(f.getName(), fieldName)).findFirst().orElseGet(null);
        if (field != null) {
            result = field.getSelectionSet().getSelections()
                    .stream()
                    .map(selection -> GraphQlUtils.extractSelectionName(selection, env))
                    .reduce(Lists.newArrayList(),
                            (accumulator, value) -> {
                                accumulator.addAll(value); return accumulator;
                            });
        }

        if (result != null) {
            ProjectionDto idColumn = result.stream().filter(f -> StringUtils.equals(f.getColumnName(), COLUMN_ID)).findFirst().orElse(null);
            if (idColumn == null) {
                result.add(new ProjectionDto(COLUMN_ID, Collections.emptyList()));
            }
        }

        return result != null ? result : Collections.emptyList();
    }

    private static List<ProjectionDto> extractSelectionName(Selection<?> selection, DataFetchingEnvironment env) {
        List<ProjectionDto> fieldNames = Lists.newArrayList();

        if (selection instanceof Field) {
            Field field = (Field) selection;
            if (!IGNORED_FIELDS.contains(field.getName())) {
                ProjectionDto dto = new ProjectionDto(field.getName());
                if (field.getSelectionSet() != null && !CollectionUtils.isEmpty(field.getSelectionSet().getSelections())) {
                    List<ProjectionDto> children = Lists.newArrayList();
                    field.getSelectionSet()
                            .getSelections()
                            .forEach(child -> children.addAll(GraphQlUtils.extractSelectionName(child, env)));
                    dto.setChildren(children);
                }
                fieldNames.add(dto);
            }
        } else if (selection instanceof FragmentSpread) {
            FragmentSpread fragmentSpread = (FragmentSpread) selection;
            FragmentDefinition fragment = env.getFragmentsByName().get(fragmentSpread.getName());
            fragment.getSelectionSet().getSelections().stream()
                    .map(s -> GraphQlUtils.extractSelectionName(s, env))
                    .reduce(fieldNames, (accumulator, value) -> {
                        accumulator.addAll(value); return accumulator;
                    });

        } else if (selection instanceof InlineFragment) {
            ((InlineFragment) selection).getSelectionSet().getSelections().stream()
                    .map(s -> GraphQlUtils.extractSelectionName(s, env))
                    .reduce(fieldNames, (accumulator, value) -> {
                        accumulator.addAll(value); return accumulator;
                    });
        }

        return fieldNames
                .stream()
                .filter(fieldName -> fieldName != null && !IGNORED_FIELDS.contains(fieldName.getColumnName()))
                .collect(Collectors.toList());
    }

    public static PageRequest sortingPage(PageRequestDto page, List<SortOrderDto> sort, PageRequest defaultPage) {
        return page != null ? page.toPageRequest(sort) : defaultPage;
    }
}

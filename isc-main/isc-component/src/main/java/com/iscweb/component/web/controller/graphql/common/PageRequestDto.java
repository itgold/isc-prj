package com.iscweb.component.web.controller.graphql.common;

import com.google.common.collect.Lists;
import com.iscweb.common.model.dto.IDto;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * DTO class to handle pagination information for GraphQL queries.
 */
@Data
public class PageRequestDto implements IDto {

    private int page;
    private int size;

    public PageRequest toPageRequest() {
        return toPageRequest(null);
    }

    /**
     * Utility method to create standard Spring Data pageable request.
     *
     * @param sort Sorting information
     * @return Spring Data pageable request
     */
    public PageRequest toPageRequest(List<SortOrderDto> sort) {
        PageRequest result = PageRequest.of(page, size);

        if (sort != null && sort.size() > 0) {
            List<Sort.Order> orders = Lists.newArrayList();
            for (SortOrderDto order : sort) {
                if (order.getDirection() != null) {
                    orders.add(new Sort.Order(order.getDirection(), order.getProperty()));
                } else {
                    orders.add(Sort.Order.by(order.getProperty()));
                }
            }
            result = PageRequest.of(page, size, Sort.by(orders));
        }

        return result;
    }
}

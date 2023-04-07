package com.iscweb.service;

import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.persistence.repositories.impl.IndexJpaRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * A service to handle administrative tasks.
 */
@Service
public class AdminService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull IndexJpaRepository repository;

    private PageRequest getDefaultPagination(int pageNumber) {
        return PageRequest.of(pageNumber, 500, Sort.Direction.DESC, "created");
    }

    public void asyncEmailInactivity() {
//        getUserInactivityService().asyncEmailInactivity();
    }
}

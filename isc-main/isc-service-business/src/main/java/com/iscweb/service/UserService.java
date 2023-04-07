package com.iscweb.service;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.PublicUserMetadataDto;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.common.model.dto.entity.core.UserSearchResultDto;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.entity.user.UserEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;
import static com.iscweb.common.security.ApplicationSecurity.PERMIT_ALL;

@Service
public class UserService extends BaseBusinessService<UserDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserEntityService entityService;

    public boolean deleteUser(String userGuid, String principalEmail) throws ServiceException {
        return getEntityService().deleteUser(userGuid, principalEmail);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<UserDto> findAll(PageRequest pageRequest) {
        return getEntityService().findAll(pageRequest).stream()
                .map(user -> (UserDto) Convert.my(user).boom())
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public UserSearchResultDto findUsers(QueryFilterDto filter, PageRequest pageRequest) {
        return (UserSearchResultDto) getEntityService().findUsers(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public PublicUserMetadataDto getUserMetadata(String guid) throws ServiceException {
        return getEntityService().getUserMetadata(guid);
    }
}

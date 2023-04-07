package com.iscweb.service.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * Our default schema is not the same as the default JdbcClientDetailsService implementation, so we
 * need to override it and correct SQL queries for it to be compatible.
 *
 * @author skurenkov
 */
@Slf4j
public class OauthJdbcClientDetailsService extends JdbcClientDetailsService {

    private static final String CLIENT_FIELDS_FOR_UPDATE = "oak_resource_ids, oak_scope, "
            + "oak_authorized_grant_types, oak_web_server_redirect_uri, oak_authorities, oak_access_token_validity, "
            + "oak_refresh_token_validity, oak_additional_information, oak_autoapprove";

    private static final String CLIENT_FIELDS = "oak_client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    private static final String BASE_FIND_STATEMENT = "select oak_client_id, " + CLIENT_FIELDS
            + " from oauth_api_keys";

    private static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by oak_client_id";

    private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where oak_client_id = ?";

    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_api_keys (" + CLIENT_FIELDS
            + ", oak_client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DEFAULT_UPDATE_STATEMENT = "update oauth_api_keys " + "set "
            + CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where oak_client_id = ?";

    private static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update oauth_api_keys "
            + "set oak_client_secret = ? where oak_client_id = ?";

    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_api_keys where oak_client_id = ?";


    /**
     * Default data source initialization constructor.
     *
     * @param dataSource data source to initialize.
     */
    public OauthJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);

        log.debug("Overriding default JdbcClientDetailsService with Loop DB schema");

        setDeleteClientDetailsSql(DEFAULT_DELETE_STATEMENT);

        setFindClientDetailsSql(DEFAULT_FIND_STATEMENT);

        setUpdateClientDetailsSql(DEFAULT_UPDATE_STATEMENT);

        setUpdateClientSecretSql(DEFAULT_UPDATE_SECRET_STATEMENT);

        setInsertClientDetailsSql(DEFAULT_INSERT_STATEMENT);

        setSelectClientDetailsSql(DEFAULT_SELECT_STATEMENT);
    }
}

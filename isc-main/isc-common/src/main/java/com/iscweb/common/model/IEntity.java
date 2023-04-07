package com.iscweb.common.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * An interface for all system entities.
 *
 * @author skurenkov
 */
public interface IEntity extends Serializable {

    /**
     * @return the id of the object. The implementation of this method with annotations should look
     * like this (in postgres):
     * <pre>
     * {@code
     * @literal @Column(name = "table_prefix_id")
     * @literal @Id
     * @literal @SequenceGenerator(name = "table_prefix_id_seq", sequenceName = "table_prefix_id_seq", allocationSize = 1)
     * @literal @GeneratedValue(strategy = GenerationType.AUTO, generator = "table_prefix_id_seq")
     *  public Long getRowId() {
     *     return this.id;
     *  }
     * }</pre>
     * On the database side, "[table_prefix]_id_seq" must exist as well
     */
    Long getId();

    void setId(Long id);

    ZonedDateTime getCreated();

    void setCreated(ZonedDateTime created);

    ZonedDateTime getUpdated();

    void setUpdated(ZonedDateTime updated);

    String getGuid();

    void setGuid(String guid);
}

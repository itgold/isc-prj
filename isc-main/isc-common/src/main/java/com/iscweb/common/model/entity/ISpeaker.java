package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.SpeakerStatus;
import com.iscweb.common.model.metadata.SpeakerType;
import org.locationtech.jts.geom.Point;

import java.util.Set;

/**
 * Speaker contract.
 *
 * @author alex@iscweb.io
 * Date: 4/5/2021
 */
public interface ISpeaker extends ISchoolEntity {

    String getExternalId();

    SpeakerStatus getStatus();

    SpeakerType getType();

    String getName();

    String getDescription();

    Set<? extends IDeviceStateItem> getState();
}

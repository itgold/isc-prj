package com.iscweb.service.sync

import spock.lang.Specification

/**
 *
 * Date: 1/7/16
 * @author skurenkov
 */
class SyncServiceHelperTest extends Specification {

    String target = new String();

    def "parseExtension(...)"() {

        when:
            String result = target.toString();
        then:
            result != null;
    }

}

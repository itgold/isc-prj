package com.iscweb.integration.cameras.mip.services.streaming.adapters;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class BooleanYesNoAdapter extends XmlAdapter<String, Boolean> {

    private static final List<String> TRUE_VALUES = Lists.newArrayList("TRUE", "true", "YES", "yes", "1");

    @Override
    public Boolean unmarshal(String v) {
        return TRUE_VALUES.contains(v);
    }

    @Override
    public String marshal(Boolean v) {
        return v != null && v ? "yes" : "no";
    }
}

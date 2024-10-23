package com.github.stilvergp.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return (v != null) ? LocalDateTime.parse(v, formatter) : null;
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return (v != null) ? v.format(formatter) : null;
    }
}

package com.meresti.xml.adapter;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static java.util.Objects.requireNonNull;

public class TemporalAccessorXmlAdapter<T extends TemporalAccessor> extends XmlAdapter<String, T> {
    private final DateTimeFormatter formatter;
    private final TemporalQuery<? extends T> temporalQuery;

    public TemporalAccessorXmlAdapter(DateTimeFormatter formatter, TemporalQuery<? extends T> temporalQuery) {
        this.formatter = requireNonNull(formatter, "formatter must not be null");
        this.temporalQuery = requireNonNull(temporalQuery, "temporal query must not be null");
    }

    @Override
    public T unmarshal(String stringValue) {
        return stringValue != null ? formatter.parse(stringValue, temporalQuery) : null;
    }

    @Override
    public String marshal(T value) {
        return value != null ? formatter.format(value) : null;
    }
}

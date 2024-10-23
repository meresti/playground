package com.meresti.xml.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateXmlAdapter extends TemporalAccessorXmlAdapter<LocalDate> {
    public LocalDateXmlAdapter() {
        super(DateTimeFormatter.ISO_DATE, LocalDate::from);
    }
}

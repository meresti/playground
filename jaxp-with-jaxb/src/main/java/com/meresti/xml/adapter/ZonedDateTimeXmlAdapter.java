package com.meresti.xml.adapter;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeXmlAdapter extends TemporalAccessorXmlAdapter<ZonedDateTime> {
    public ZonedDateTimeXmlAdapter() {
        super(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSSXXX"), ZonedDateTime::from);
    }
}

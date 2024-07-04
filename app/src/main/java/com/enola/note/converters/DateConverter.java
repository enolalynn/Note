package com.enola.note.converters;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Long dateToTimestamp(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date timestampToDate(Long timestamp){
        return new Date(timestamp);
    }
}

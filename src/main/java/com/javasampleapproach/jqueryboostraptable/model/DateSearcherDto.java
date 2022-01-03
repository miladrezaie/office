package com.javasampleapproach.jqueryboostraptable.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DateSearcherDto {


//    @Date(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd")

    @Temporal(TemporalType.DATE)
    private Date endDate;
}

package com.rpa.dramascrapper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Drama {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private int id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date firstAiringDate;

    public Drama() {
        this.id = -1;
        this.name = null;
        this.firstAiringDate = null;
    }

    public Drama(int id) {
        this.id = id;
    }

    public Drama(String name, Date firstAiringDate) {
        this.firstAiringDate = firstAiringDate;
        this.name = name;
    }

}

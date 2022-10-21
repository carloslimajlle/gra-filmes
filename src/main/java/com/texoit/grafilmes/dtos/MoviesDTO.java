package com.texoit.grafilmes.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * @author Carlos Lima on 20/10/2022
 */
@Data
public class MoviesDTO {

    @CsvBindByName(column = "year", required = true)
    private int year;

    @CsvBindByName(column = "title", required = true)
    private String title;

    @CsvBindByName(column = "studios", required = true)
    private String studios;

    @CsvBindByName(column = "producers", required = true)
    private String producers;

    @CsvBindByName(column = "winner")
    private String winner;
}

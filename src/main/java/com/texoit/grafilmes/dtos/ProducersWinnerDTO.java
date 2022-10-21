package com.texoit.grafilmes.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * @author Carlos Lima on 20/10/2022
 */
@Data
@Builder
public class ProducersWinnerDTO {

    private String producer;

    private int interval;

    private int previousWin;

    private int followingWin;
}

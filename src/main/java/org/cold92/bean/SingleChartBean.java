package org.cold92.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单条折线图bean
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class SingleChartBean {

    // x轴
    private String date;
    // y轴
    private int nowConfirm;
}

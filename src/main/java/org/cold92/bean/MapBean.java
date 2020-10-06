package org.cold92.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地图：全中国感染情况
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("map")
public class MapBean {

    private String name;
    private int value;
}

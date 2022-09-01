package com.tothefor.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Steven
 * @Date 2022/3/13 17:06
 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PieData {
    private Integer value;
    private String name;
}

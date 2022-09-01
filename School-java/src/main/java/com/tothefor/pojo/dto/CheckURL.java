package com.tothefor.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Steven
 * @Date 2022/3/13 15:43
 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckURL {
    private String token ;
    private String urlpath;
}

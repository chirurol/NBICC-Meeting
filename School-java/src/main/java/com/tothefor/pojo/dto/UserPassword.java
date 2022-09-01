package com.tothefor.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Steven
 * @Date 2022/08/31 20:17
 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPassword {

    private Long userid;
    private String oldP;
    private String newP;
    private String newPP;
}

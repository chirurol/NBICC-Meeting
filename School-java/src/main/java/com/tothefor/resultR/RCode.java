package com.tothefor.resultR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 状态码
 * @Author Steven
 * @Date 2022/08/31 11:39
 
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public enum RCode {

    /**
     * 成功
     */
    SUCCESS(true,200, "成功"),

    /**
     * 失败
     */
    FAIL(false,500, "失败");

    /**
     * @Author Steven
     * @Date 2022/08/31 11:54
     
     * @属性 statusFlag
     * @作用 判断是否成功
     */
    private Boolean statusFlag;

    /**
     * @Author Steven
     * @Date 2022/08/31 11:56
     
     * @属性 statusCode
     * @作用 返回结果状态码
     */
    private Integer statusCode;

    /**
     * @Author Steven
     * @Date 2022/08/31 11:56
     
     * @属性 statusMessage
     * @作用 提示信息
     */
    private String statusMessage;


}

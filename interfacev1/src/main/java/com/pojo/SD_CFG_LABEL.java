package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Author:   tyx
 * Date:     2020/11/18 19:47
 * Description: 策略分组表，风险策略表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SD_CFG_LABEL {
    private String ID_;
    private String CODE_;
    private String NAME_;
    private String TYPE_;
    private int PRIORITY;
    private String OPER_SCENE_ID;
    private String CREATE_BY;
    private String UPDATE_BY;
    private Timestamp CREATE_TIME;
    private Timestamp UPDATE_TIME;
}
 

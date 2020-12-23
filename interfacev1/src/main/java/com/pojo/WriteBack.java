package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author:   tyx
 * Date:     2020/11/6 11:00
 * Description: 数据回写类
 */
@Data
@AllArgsConstructor
public class WriteBack {
    public int rowNum;
    public int cellNum;
    public String content;
    public int sheetIndex;
}
 

package com.zhq.devtools.database;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/15 14:44
 * Description
 */
public class ClassBean extends LitePalSupport {

    @Column(unique = true ,defaultValue = "unknown")
    public String className;
    public String classNum;

}

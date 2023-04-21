package com.zhq.toolslib.window;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/29 14:18
 * Description
 */
public class ButtonItemBean {
    public ButtonItemBean(String itemName) {
        this.itemName = itemName;
    }

    public ButtonItemBean(String itemName, String itemNameColor) {
        this.itemName = itemName;
        this.itemNameColor = itemNameColor;
    }

    public int id;
    public String itemName;
    public String itemNameColor;
    public int itemBgResId;
    public float itemTextSizeSp;
}

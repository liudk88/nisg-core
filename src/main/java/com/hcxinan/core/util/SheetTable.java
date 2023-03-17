package com.hcxinan.core.util;

import java.util.List;

public class SheetTable {
    private int index;//tabel在sheet第几个（从上往下排）

    private int x;//表格起点的横坐标

    private int y;//表格起点的纵坐标

    private List<String[]> datas;

    public SheetTable(int index) {
        this.index = index;
    }

    public SheetTable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String[]> getDatas() {
        return datas;
    }

    public void setDatas(List<String[]> datas) {
        this.datas = datas;
    }
}

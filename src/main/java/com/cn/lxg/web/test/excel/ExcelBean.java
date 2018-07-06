package com.cn.lxg.web.test.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExcelBean implements Serializable {
    private static final long serialVersionUID = 1776188080397849282L;
    public List<SheetBean> sheetList = new ArrayList();
    boolean wrap;

    public ExcelBean() {
    }

    public SheetBean addSheet(String sheetName) {
        SheetBean sheetBean = new SheetBean();
        sheetBean.setValue(sheetName);
        this.sheetList.add(sheetBean);
        return sheetBean;
    }

    public List<SheetBean> getSheetList() {
        return this.sheetList;
    }

    public boolean isWrap() {
        return this.wrap;
    }

    public void setSheetList(List<SheetBean> sheetList) {
        this.sheetList = sheetList;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof ExcelBean)) {
            return false;
        } else {
            ExcelBean other = (ExcelBean)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                List this$sheetList = this.getSheetList();
                List other$sheetList = other.getSheetList();
                if(this$sheetList == null) {
                    if(other$sheetList == null) {
                        return this.isWrap() == other.isWrap();
                    }
                } else if(this$sheetList.equals(other$sheetList)) {
                    return this.isWrap() == other.isWrap();
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExcelBean;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        List $sheetList = this.getSheetList();
        int result1 = result * 59 + ($sheetList == null?43:$sheetList.hashCode());
        result1 = result1 * 59 + (this.isWrap()?79:97);
        return result1;
    }

    public String toString() {
        return "ExcelBean(sheetList=" + this.getSheetList() + ", wrap=" + this.isWrap() + ")";
    }
}

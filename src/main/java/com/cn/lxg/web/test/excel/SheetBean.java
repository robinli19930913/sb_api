package com.cn.lxg.web.test.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetBean implements Serializable {
    private static final long serialVersionUID = 4512511036918724645L;
    private List<RowBean> rowList = new ArrayList();
    private String value;

    public SheetBean() {
    }

    public RowBean addRow() {
        RowBean rowBean = new RowBean();
        this.rowList.add(rowBean);
        return rowBean;
    }

    public List<RowBean> getRowList() {
        return this.rowList;
    }

    public String getValue() {
        return this.value;
    }

    public void setRowList(List<RowBean> rowList) {
        this.rowList = rowList;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof SheetBean)) {
            return false;
        } else {
            SheetBean other = (SheetBean)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                List this$rowList = this.getRowList();
                List other$rowList = other.getRowList();
                if(this$rowList == null) {
                    if(other$rowList != null) {
                        return false;
                    }
                } else if(!this$rowList.equals(other$rowList)) {
                    return false;
                }

                String this$value = this.getValue();
                String other$value = other.getValue();
                if(this$value == null) {
                    if(other$value != null) {
                        return false;
                    }
                } else if(!this$value.equals(other$value)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof SheetBean;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        List $rowList = this.getRowList();
        int result1 = result * 59 + ($rowList == null?43:$rowList.hashCode());
        String $value = this.getValue();
        result1 = result1 * 59 + ($value == null?43:$value.hashCode());
        return result1;
    }

    public String toString() {
        return "SheetBean(rowList=" + this.getRowList() + ", value=" + this.getValue() + ")";
    }
}

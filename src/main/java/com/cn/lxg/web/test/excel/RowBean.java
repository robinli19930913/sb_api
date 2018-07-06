package com.cn.lxg.web.test.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RowBean implements Serializable {
    private static final long serialVersionUID = -6667319257164445219L;
    private List<CellBean> cellList = new ArrayList();

    public RowBean() {
    }

    public RowBean addCell(CellBean cellBean) {
        this.cellList.add(cellBean);
        return this;
    }

    public List<CellBean> getCellList() {
        return this.cellList;
    }

    public void setCellList(List<CellBean> cellList) {
        this.cellList = cellList;
    }
}
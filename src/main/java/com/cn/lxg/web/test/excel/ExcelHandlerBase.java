package com.cn.lxg.web.test.excel;

public abstract class ExcelHandlerBase implements ExcelHandler {
    protected boolean wrap = false;

    public ExcelHandlerBase() {
    }

    protected void initConfigure(ExcelBean excelBean) {
        if(excelBean != null) {
            this.wrap = excelBean.isWrap();
        }

    }
}

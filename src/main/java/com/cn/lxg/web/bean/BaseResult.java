package com.cn.lxg.web.bean;

import java.util.List;

import com.github.pagehelper.Page;
import lombok.Data;

@Data
public class BaseResult {
	private boolean success = Boolean.TRUE;
	private String msg = "操作成功！";
	private Object data;
	private List rows;
	private List rowsPage;
	private Long total = 0L;

	public List getRowsPage() {
		return rowsPage;
	}
	public void setRowsPage(List rowsPage) {
		if(rowsPage != null &&rowsPage.size() > 0) {
			Page page = (Page)rowsPage;
			this.total = page.getTotal();
		}
		this.rows = rowsPage;
	}
}

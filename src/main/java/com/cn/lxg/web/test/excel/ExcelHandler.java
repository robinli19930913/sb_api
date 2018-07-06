package com.cn.lxg.web.test.excel;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface ExcelHandler {
    byte[] createExcel(ExcelBean var1) throws IOException;

    ExcelBean parseExcel(byte[] var1, boolean var2) throws IOException, InvalidFormatException;
}

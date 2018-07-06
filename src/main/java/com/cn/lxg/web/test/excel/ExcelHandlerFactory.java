package com.cn.lxg.web.test.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class ExcelHandlerFactory {
    public ExcelHandlerFactory() {
    }

    public static byte[] create2003Excel(ExcelBean excelBean) throws IOException {
        ExcelHandlerHSSF excelHandler = new ExcelHandlerHSSF();
        return excelHandler.createExcel(excelBean);
    }

    public static ExcelBean parse2003Excel(byte[] bytes) throws IOException, InvalidFormatException {
        ExcelHandlerHSSF excelHandler = new ExcelHandlerHSSF();
        return excelHandler.parseExcel(bytes, false);
    }

    public static ExcelBean parse2003Excel(InputStream inputStream) throws IOException, InvalidFormatException {
        ExcelHandlerHSSF excelHandler = new ExcelHandlerHSSF();
        return excelHandler.parseExcel(input2byte(inputStream), false);
    }

    public static ExcelBean parse2003Excel(byte[] bytes, boolean isSingleString) throws IOException, InvalidFormatException {
        ExcelHandlerHSSF excelHandler = new ExcelHandlerHSSF();
        return excelHandler.parseExcel(bytes, isSingleString);
    }

    public static ExcelBean parse2003Excel(InputStream inputStream, boolean isSingleString) throws IOException, InvalidFormatException {
        ExcelHandlerHSSF excelHandler = new ExcelHandlerHSSF();
        return excelHandler.parseExcel(input2byte(inputStream), isSingleString);
    }

    public static byte[] create2007Excel(ExcelBean excelBean) throws IOException {
        ExcelHandlerXSSF excelHandler = new ExcelHandlerXSSF();
        return excelHandler.createExcel(excelBean);
    }

    public static ExcelBean parse2007Excel(byte[] bytes) throws IOException, InvalidFormatException {
        ExcelHandlerXSSF excelHandler = new ExcelHandlerXSSF();
        return excelHandler.parseExcel(bytes, false);
    }

    public static ExcelBean parse2007Excel(InputStream inputStream) throws IOException, InvalidFormatException {
        ExcelHandlerXSSF excelHandler = new ExcelHandlerXSSF();
        return excelHandler.parseExcel(input2byte(inputStream), false);
    }

    public static ExcelBean parse2007Excel(byte[] bytes, boolean isSingleString) throws IOException, InvalidFormatException {
        ExcelHandlerXSSF excelHandler = new ExcelHandlerXSSF();
        return excelHandler.parseExcel(bytes, isSingleString);
    }

    public static ExcelBean parse2007Excel(InputStream inputStream, boolean isSingleString) throws IOException, InvalidFormatException {
        ExcelHandlerXSSF excelHandler = new ExcelHandlerXSSF();
        return excelHandler.parseExcel(input2byte(inputStream), isSingleString);
    }

    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        boolean rc = false;

        int rc1;
        while((rc1 = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc1);
        }

        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static void main(String[] args) {
        try {
            ExcelBean e = new ExcelBean();
            e.setWrap(true);
            SheetBean excelBean = e.addSheet("test");

            for(int excel = 0; excel < 10; ++excel) {
                RowBean excel2003 = excelBean.addRow();
                excel2003.addCell(new CellBean(Integer.valueOf(50)));
                excel2003.addCell(new CellBean(Double.valueOf("10.02")));
                excel2003.addCell(new CellBean(Double.valueOf("20.00")));
                excel2003.addCell(new CellBean("测试测试测试测试测试测试测试测试测测试测试测试试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"));
                excel2003.addCell(new CellBean("啊啊 啊啊啊啊啊啊啊"));
                excel2003.addCell(new CellBean("fwefweffwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfefwefwefwfewfe"));
                excel2003.addCell(new CellBean("a a a测试"));
                excel2003.addCell(new CellBean(new Date(), (String)null));
            }

            byte[] var13 = create2007Excel(e);
            byte[] var14 = create2003Excel(e);
            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\29007\\Desktop\\test2007.xlsx"));
            FileOutputStream fileOutputStreamexcel2003 = new FileOutputStream(new File("C:\\Users\\29007\\Desktop\\test2003.xls"));
            fileOutputStream.write(var13);
            fileOutputStreamexcel2003.write(var14);
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        try {
            FileInputStream var11 = new FileInputStream(new File("C:\\Users\\29007\\Desktop\\5.0开发排期.xlsx"));
            ExcelBean var12 = parse2007Excel((InputStream)var11);
            System.out.println(var12);
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (InvalidFormatException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }
}
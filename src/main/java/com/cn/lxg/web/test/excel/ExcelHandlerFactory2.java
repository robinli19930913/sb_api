package com.cn.lxg.web.test.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelHandlerFactory2 {
    public ExcelHandlerFactory2() {
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
            FileInputStream e = new FileInputStream(new File("C:\\Users\\29007\\Desktop\\5.0开发排期.xlsx"));
            Long startTime = Long.valueOf(System.currentTimeMillis());
            ExcelBean excelBean2007 = parse2007Excel((InputStream)e);
            System.out.println(excelBean2007);
            System.out.println();
            System.err.println((System.currentTimeMillis() - startTime.longValue()) / 1000L + "秒");
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (InvalidFormatException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }
}

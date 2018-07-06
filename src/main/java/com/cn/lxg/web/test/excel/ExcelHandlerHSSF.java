package com.cn.lxg.web.test.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelHandlerHSSF extends ExcelHandlerBase implements ExcelHandler {
    private HSSFWorkbook hssfWorkbook = null;

    public ExcelHandlerHSSF() {
    }

    public byte[] createExcel(ExcelBean excelBean) throws IOException {
        this.initConfigure(excelBean);
        this.hssfWorkbook = new HSSFWorkbook();
        CellStyle cellStyle = this.getCellStyle(this.hssfWorkbook);
        Iterator out = excelBean.getSheetList().iterator();

        while(out.hasNext()) {
            SheetBean sheetBean = (SheetBean)out.next();
            String safeName = WorkbookUtil.createSafeSheetName(sheetBean.getValue());
            HSSFSheet sheet = this.hssfWorkbook.createSheet(safeName);
            short rowIndex = 0;
            ArrayList list = new ArrayList();

            for(Iterator i = sheetBean.getRowList().iterator(); i.hasNext(); ++rowIndex) {
                RowBean rowBean = (RowBean)i.next();
                HSSFRow row = sheet.createRow(rowIndex);
                int cellIndex = 0;

                for(Iterator var13 = rowBean.getCellList().iterator(); var13.hasNext(); ++cellIndex) {
                    CellBean cellBean = (CellBean)var13.next();
                    Integer aLong = null;
                    if(list.size() > cellIndex) {
                        aLong = (Integer)list.get(cellIndex);
                    }

                    if(StringUtils.isNotBlank(cellBean.getValueByString())) {
                        if(aLong == null) {
                            int cell = cellBean.getValueByString().getBytes().length * 256;
                            list.add(Integer.valueOf(cell > '\uff00'?'\uff00':cell));
                        } else {
                            Integer var19 = Integer.valueOf(cellBean.getValueByString().getBytes().length * 256);
                            if(aLong.compareTo(var19) < -1) {
                                list.set(cellIndex, Integer.valueOf(var19.intValue() > '\uff00'?'\uff00':var19.intValue()));
                            }
                        }
                    }

                    HSSFCell var20 = row.createCell(cellIndex);
                    this.setCellValue(var20, cellBean, cellStyle);
                }
            }

            for(int var18 = 0; var18 < list.size(); ++var18) {
                sheet.autoSizeColumn(var18, true);
            }
        }

        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        this.hssfWorkbook.write(var17);
        return var17.toByteArray();
    }

    private void setCellValue(HSSFCell cell, CellBean cellBean, CellStyle cellStyle) {
        cell.setCellStyle(cellStyle);
        HSSFCreationHelper createHelper = this.hssfWorkbook.getCreationHelper();
        if(cellBean.getValueByDate() != null) {
            String dateformat = "m/d/yy h:mm";
            if(cellBean.getDateFormat() != null) {
                dateformat = cellBean.getDateFormat();
            }

            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateformat));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cellBean.getValueByDate());
        } else if(cellBean.getValueByString() != null) {
            cell.setCellValue(createHelper.createRichTextString(cellBean.getValueByString()));
        } else if(cellBean.getValueByDouble() != null) {
            cell.setCellValue(cellBean.getValueByDouble().doubleValue());
        } else if(cellBean.getValueByBoolean() != null) {
            cell.setCellValue(cellBean.getValueByBoolean().booleanValue());
        } else if(cellBean.getValueByInteger() != null) {
            cell.setCellValue((double)cellBean.getValueByInteger().intValue());
        }

    }

    private HSSFFont getContentFont(HSSFWorkbook workbook) {
        HSSFFont contentStyle = workbook.createFont();
        contentStyle.setBoldweight((short)400);
        return contentStyle;
    }

    private CellStyle getCellStyle(HSSFWorkbook hssfWorkbook) {
        HSSFCellStyle contentStyle = hssfWorkbook.createCellStyle();
        contentStyle.setFillForegroundColor((short)9);
        contentStyle.setFillPattern((short)1);
        contentStyle.setBorderBottom((short)1);
        contentStyle.setBorderLeft((short)1);
        contentStyle.setBorderRight((short)1);
        contentStyle.setBorderTop((short)1);
        contentStyle.setAlignment((short)1);
        contentStyle.setWrapText(this.wrap);
        contentStyle.setVerticalAlignment((short)1);
        HSSFFont contentFont = this.getContentFont(hssfWorkbook);
        contentStyle.setFont(contentFont);
        return contentStyle;
    }

    public ExcelBean parseExcel(byte[] bytes, boolean isSingleString) throws IOException, InvalidFormatException {
        ExcelBean excelBean = new ExcelBean();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Workbook wb = WorkbookFactory.create(byteArrayInputStream);
        int numberOfSheets = wb.getNumberOfSheets();
        ArrayList sheetBeans = new ArrayList();

        for(int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = wb.getSheetAt(i);
            SheetBean sheetBean = new SheetBean();
            sheetBean.setValue(sheet.getSheetName());
            Iterator rowIterator = sheet.iterator();
            ArrayList rowBeens = new ArrayList();
            sheetBean.setRowList(rowBeens);
            sheetBeans.add(sheetBean);

            while(rowIterator.hasNext()) {
                RowBean rowBean = new RowBean();
                rowBeens.add(rowBean);
                Row row = (Row)rowIterator.next();
                ArrayList cellBeens = new ArrayList();
                rowBean.setCellList(cellBeens);
                short lastCellNum = row.getLastCellNum();

                for(int celli = 0; celli < lastCellNum; ++celli) {
                    Cell cell = row.getCell(celli);
                    CellBean cellBean = null;
                    if(cell != null) {
                        int cellType = cell.getCellType();
                        cellBean = new CellBean();
                        if(cellType == 1) {
                            cellBean = new CellBean(cell.getStringCellValue());
                        } else if(cellType == 0) {
                            if(HSSFDateUtil.isCellDateFormatted(cell)) {
                                double value = cell.getNumericCellValue();
                                Date date = DateUtil.getJavaDate(value);
                                cellBean = new CellBean();
                                cellBean.setValueByDate(date);
                            } else {
                                cellBean = new CellBean(Double.valueOf(cell.getNumericCellValue()));
                            }
                        }

                        if(cellBean.getValueByDate() != null) {
                            cellBean.setCellType(6);
                        } else {
                            cellBean.setCellType(cellType);
                        }
                    }

                    cellBeens.add(cellBean);
                }
            }
        }

        excelBean.setSheetList(sheetBeans);
        return excelBean;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        File file = new File("d:\\2.xls");
        FileInputStream inputStream = new FileInputStream(file);
        ExcelBean excelBean = ExcelHandlerFactory.parse2003Excel(inputStream);
        List sheetList = excelBean.getSheetList();
        int rownum = 0;
        ArrayList orderNos = new ArrayList();
        new ArrayList();
        Iterator var8 = sheetList.iterator();

        while(var8.hasNext()) {
            SheetBean sheetBean = (SheetBean)var8.next();
            List rowList = sheetBean.getRowList();

            for(Iterator var11 = rowList.iterator(); var11.hasNext(); ++rownum) {
                RowBean rowBean = (RowBean)var11.next();
                List cellList = rowBean.getCellList();
                int cellnum = 0;
                if(rownum != 0) {
                    Object orderNo = null;
                    String expressCom = null;
                    String expressNum = null;

                    for(Iterator var18 = cellList.iterator(); var18.hasNext(); ++cellnum) {
                        CellBean cellBean = (CellBean)var18.next();
                        if(cellBean != null) {
                            switch(cellnum) {
                                case 0:
                                    String valueByString = cellBean.getValue();
                                    if(valueByString != null) {
                                        orderNos.add(valueByString);
                                    }
                                    break;
                                case 1:
                                    expressCom = cellBean.getValue();
                                    break;
                                case 2:
                                    expressNum = cellBean.getValue();
                                    System.out.println(expressNum);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(1);
    }
}

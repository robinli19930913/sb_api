package com.cn.lxg.web.test.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandlerXSSF extends ExcelHandlerBase implements ExcelHandler {
    private XSSFWorkbook xssfWorkbook = null;

    public ExcelHandlerXSSF() {
    }

    private void setCellValue(XSSFCell cell, CellBean cellBean, CellStyle cellStyle) {
        XSSFCreationHelper createHelper = this.xssfWorkbook.getCreationHelper();
        if(cellBean.getValueByDate() != null) {
            XSSFCellStyle celle = this.xssfWorkbook.createCellStyle();
            String dateformat = "m/d/yy h:mm";
            if(cellBean.getDateFormat() != null) {
                dateformat = cellBean.getDateFormat();
            }

            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateformat));
            cell.setCellValue(cellBean.getValueByDate());
            cell.setCellStyle(cellStyle);
        } else if(cellBean.getValueByString() != null) {
            cell.setCellValue(createHelper.createRichTextString(cellBean.getValueByString()));
        } else if(cellBean.getValueByDouble() != null) {
            cell.setCellValue(cellBean.getValueByDouble().doubleValue());
        } else if(cellBean.getValueByBoolean() != null) {
            cell.setCellValue(cellBean.getValueByBoolean().booleanValue());
        } else if(cellBean.getValueByInteger() != null) {
            cell.setCellValue((double)cellBean.getValueByInteger().intValue());
        }

        cellStyle.setWrapText(this.wrap);
        cellStyle.setVerticalAlignment((short)1);
        cell.setCellStyle(cellStyle);
    }

    public byte[] createExcel(ExcelBean excelBean) throws IOException {
        this.initConfigure(excelBean);
        this.xssfWorkbook = new XSSFWorkbook();
        CellStyle cellStyle = this.getCellStyle(this.xssfWorkbook);
        Iterator out = excelBean.getSheetList().iterator();

        while(out.hasNext()) {
            SheetBean sheetBean = (SheetBean)out.next();
            String safeName = WorkbookUtil.createSafeSheetName(sheetBean.getValue());
            XSSFSheet XSSFsheet = this.xssfWorkbook.createSheet(safeName);
            short rowIndex = 0;

            for(Iterator var8 = sheetBean.getRowList().iterator(); var8.hasNext(); ++rowIndex) {
                RowBean rowBean = (RowBean)var8.next();
                XSSFRow XSSFrow = XSSFsheet.createRow(rowIndex);
                int cellIndex = 0;

                for(Iterator var12 = rowBean.getCellList().iterator(); var12.hasNext(); ++cellIndex) {
                    CellBean cellBean = (CellBean)var12.next();
                    XSSFCell cell = XSSFrow.createCell(cellIndex);
                    this.setCellValue(cell, cellBean, cellStyle);
                }
            }
        }

        ByteArrayOutputStream var15 = new ByteArrayOutputStream();
        this.xssfWorkbook.write(var15);
        return var15.toByteArray();
    }

    private CellStyle getCellStyle(XSSFWorkbook xSSFWorkbook) {
        XSSFCellStyle cellStyle = xSSFWorkbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern((short)1);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setAlignment((short)1);
        cellStyle.setWrapText(this.wrap);
        cellStyle.setVerticalAlignment((short)1);
        XSSFFont contentFont = this.getContentFont(this.xssfWorkbook);
        cellStyle.setFont(contentFont);
        return cellStyle;
    }

    private XSSFFont getContentFont(XSSFWorkbook xSSFWorkbook) {
        XSSFFont font = xSSFWorkbook.createFont();
        font.setBoldweight((short)400);
        return font;
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
}

package com.mcc.outify.weathers;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class LocationList {

    public static List<LocationEntity> readExcel(String filePath) {
        List<LocationEntity> locationList = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            ZipSecureFile.setMinInflateRatio(0);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            XSSFRow firstRow = sheet.getRow(0);

            int cells = firstRow.getPhysicalNumberOfCells();

            for (int rowindex = 1; rowindex < rows; rowindex++) {
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    String highAddr = row.getCell(0).getStringCellValue();
                    String midAddr = isNullCheck(row, 1);
                    String lowAddr = isNullCheck(row, 2);
                    Double longitude = row.getCell(3).getNumericCellValue();
                    Double latitude = row.getCell(4).getNumericCellValue();

                    LocationEntity locationData = new LocationEntity(highAddr, midAddr, lowAddr, longitude, latitude);
                    locationList.add(locationData);
                }
            }
            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }

    private static String isNullCheck(XSSFRow row, int cellnum) {
        String addr = " ";
        XSSFCell midAddrCell = row.getCell(cellnum);
        if (midAddrCell != null) {
            addr = midAddrCell.getStringCellValue();
        }
        return addr;
    }

}

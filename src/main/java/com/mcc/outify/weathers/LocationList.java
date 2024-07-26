package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class LocationList {

    private final LocationRepository locationRepository;

    public void readExcel(InputStream inputStream) {

        try {
            locationRepository.deleteAll();

            ZipSecureFile.setMinInflateRatio(0);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            Set<String> seenLocations = new HashSet<>();

            for (int rowindex = 1; rowindex < rows; rowindex++) {
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    String category = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String highAddr = row.getCell(2).getStringCellValue();
                    String midAddr = isNullCheck(row, 3);
                    String lowAddr = isNullCheck(row, 4);
                    Double longitude = row.getCell(5).getNumericCellValue();
                    Double latitude = row.getCell(6).getNumericCellValue();

                    String uniqueIdentifier = highAddr + "-" + midAddr + "-" + lowAddr;
                    if (!seenLocations.contains(uniqueIdentifier.trim())) {
                        seenLocations.add(uniqueIdentifier);

                        LocationEntity newLocation = new LocationEntity(category, name, highAddr, midAddr, lowAddr, longitude, latitude);
                        locationRepository.save(newLocation);
                    }
                }
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String isNullCheck(XSSFRow row, int cellnum) {
        String addr = " ";
        XSSFCell cell = row.getCell(cellnum);
        if (cell != null) {
            addr = cell.getStringCellValue();
        }
        return addr;
    }

}

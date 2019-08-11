package org.codemaker.systemdiagramgenerator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

class DependyMatrixReader {

    private String filePath;

    DependyMatrixReader(String filePath) {
        this.filePath = filePath;
    }

    DependyMatrix read() throws IOException, URISyntaxException {
        DependyMatrix result = new DependyMatrix();

        Workbook workbook = readWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        List<Sys> fromSyses = collectFromSyses(sheet);
        List<Sys> toSyses = collectToSyses(sheet);
        checkFromAndToSysesForConsistency(fromSyses, toSyses);

        int fromSysesIndex = 1;
        for (Sys fromSys : fromSyses) {
            Row row = sheet.getRow(fromSysesIndex);
            int toSysesIndex = 1;
            for (Sys toSys : toSyses) {
                Cell cell = row.getCell(toSysesIndex);
                String cellValue = cell.getStringCellValue();
                if (cellValue.equals("y")) {
                    Dependy dependy = new Dependy(fromSys, toSys);
                    result.addDependy(dependy);
                }
                toSysesIndex++;
            }
            fromSysesIndex++;
        }

        return result;
    }

    private Workbook readWorkbook() throws URISyntaxException, IOException {
        URL url = getClass().getClassLoader().getResource(filePath);
        if (url != null) {
            Path path = Paths.get(url.toURI());
            return new XSSFWorkbook(new FileInputStream(path.toFile()));
        } else {
            return null;
        }
    }

    private List<Sys> collectFromSyses(Sheet sheet) {
        List<Sys> result = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip the first row because this is the header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(0);
            result.add(new Sys(cell.getStringCellValue()));
        }
        return result;
    }

    private List<Sys> collectToSyses(Sheet sheet) {
        List<Sys> result = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        cellIterator.next();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            result.add(new Sys(cell.getStringCellValue()));
        }
        return result;
    }

    private void checkFromAndToSysesForConsistency(List<Sys> syses1, List<Sys> syses2) {
        if (syses1.size() != syses2.size()) {
            throw new RuntimeException(String.format("fromSyses and toSyses contain different number of elements: %d != %d", syses1.size(), syses2.size()));
        }
        if (!(new HashSet<>(syses1).equals(new HashSet<>(syses2)))) {
            throw new RuntimeException("fromSyses and toSyses contain different elements.");
        }
    }

}

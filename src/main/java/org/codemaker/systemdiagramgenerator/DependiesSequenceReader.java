package org.codemaker.systemdiagramgenerator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class DependiesSequenceReader {

  private String filePath;
  private InputStream inputStream;

  DependiesSequenceReader(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  DependiesSequence read() throws IOException, URISyntaxException {
    DependiesSequence result = new DependiesSequence();

    Workbook workbook = new XSSFWorkbook(inputStream);

    Map<String, Sys> sysMap = createSysMapFromWorkbook(workbook);
    List<Dependies> dependiesList = createDependiesFromWorkbook(workbook, sysMap);

    for (int i = 0; i < dependiesList.size(); i++) {
      Dependies currentDependies = dependiesList.get(i);
      result.addDependies(currentDependies);
      if (i < dependiesList.size() - 1) {
        Dependies nextDependies = dependiesList.get(i + 1);
        Dependies intermediateDependies =
                new IntermediateDependiesCalculator(currentDependies, nextDependies).calculate();
        result.addDependies(intermediateDependies);
      }
    }

    return result;
  }

  private List<Sys> collectFromSyses(Sheet sheet) {
    List<Sys> result = new ArrayList<>();
    Iterator<Row> rowIterator = sheet.rowIterator();
    rowIterator.next(); // skip the first row because this is the header row
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Cell cell = row.getCell(0);
      result.add(new Sys(cell.getStringCellValue(), "Application", MigrationState.STAYING));
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
      result.add(new Sys(cell.getStringCellValue(), "Application", MigrationState.STAYING));
    }
    return result;
  }

  private void checkFromAndToSysesForConsistency(List<Sys> syses1, List<Sys> syses2) {
    if (syses1.size() != syses2.size()) {
      throw new RuntimeException(String.format("fromSyses and toSyses contain different number of elements: %d != %d"
              , syses1.size(), syses2.size()));
    }
    if (!(new HashSet<>(syses1).equals(new HashSet<>(syses2)))) {
      throw new RuntimeException("fromSyses and toSyses contain different elements.");
    }
  }

  private Map<String, Sys> createSysMapFromWorkbook(Workbook workbook) {
    Map<String, Sys> result = new HashMap<>();

    Sheet cfgSysSheet = workbook.getSheet("cfg_systems");
    Iterator<Row> rowIterator = cfgSysSheet.rowIterator();
    rowIterator.next();
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      String sysName = row.getCell(0).getStringCellValue();
      String sysType = row.getCell(1).getStringCellValue();
      result.put(sysName, new Sys(sysName, sysType, MigrationState.STAYING));

    }

    return result;
  }

  private List<Dependies> createDependiesFromWorkbook(Workbook workbook, Map<String, Sys> sysMap) {

    final String SHEET_NAME_PREFIX = "matrix_";
    List<Dependies> result = new ArrayList<>();

    // Find the list of all relevant sheets in the workbook. A sheet is relevant if its name starts with "matrix_".
    // What follows after this "matrix_" prefix is considered to be the title of the dependies. Then create a new
    // dependies object for each of such sheets, fetch the data from the sheet, and fill the dependies object
    // accordingly.
    Iterator<Sheet> workbookIterator = workbook.sheetIterator();
    while (workbookIterator.hasNext()) {
      Sheet sheet = workbookIterator.next();
      String sheetName = sheet.getSheetName();
      if (sheetName != null && sheetName.startsWith(SHEET_NAME_PREFIX)) {
        String dependiesTitle = sheetName.substring(SHEET_NAME_PREFIX.length());
        Dependies dependies = createDependiesFromSheet(sheet, sysMap, dependiesTitle);
        result.add(dependies);
      }
    }

    return result;
  }

  private Dependies createDependiesFromSheet(Sheet sheet, Map<String, Sys> sysMap, String dependiesTitle) {
    Dependies result = new Dependies(dependiesTitle);

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
          Dependy dependy = new Dependy(fromSys, toSys, MigrationState.STAYING);
          result.addDependy(dependy);
        }
        toSysesIndex++;
      }
      fromSysesIndex++;
    }

    return result;
  }

}

package com.rybalko.ua.lorstats.exel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ExcelDocumentBuilder {

    private static final String PATTERN = "dd.MM.yyyy";
    private static final String PATTERN_FOR_FORMAT = "EEE MMM dd yyyy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN_FOR_FORMAT, Locale.US);
    private static final String FILE = "files/stats.xls";
    private static final String WIN_RATE_TAB = "Win rate";
    private static final Path DEFAULT_FILE = Paths.get(FILE);

    private int prevGameId = -1;

    public void buildDocument(JSONObject response) throws IOException {
        createIfNotExists();
        buildWinRateSheet(response);
    }

    private void createIfNotExists() throws IOException {
        if (Files.notExists(DEFAULT_FILE)) {
            Files.createDirectory(Paths.get("files"));

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Win rate");
            Row row = sheet.createRow(0);
            Cell dateCell = row.createCell(0);
            dateCell.setCellValue("Date");
            Cell winCell = row.createCell(1);
            winCell.setCellValue("Wins");
            Cell loseCell = row.createCell(2);
            loseCell.setCellValue("Loses");
            sheet.setColumnWidth(0, 25 * 256);

            try (OutputStream fileOut = new FileOutputStream(FILE)) {
                wb.write(fileOut);
            }
        }
    }

    private void buildWinRateSheet(JSONObject response) throws IOException {
        try (InputStream inp = new FileInputStream(FILE)) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheet(WIN_RATE_TAB);
            int prevRow = sheet.getLastRowNum();
            int lastRow = getLastRowInWinRates(sheet);
            int gameId = response.getInt("GameID");
            boolean gameState = response.getBoolean("LocalPlayerWon");
            createNewRowNextDay(wb, sheet, prevRow, lastRow, gameId, gameState);
            updateRow(sheet, prevRow, lastRow, gameId, gameState);
            System.out.println("printed");
            try (OutputStream fileOut = new FileOutputStream(FILE)) {
                wb.write(fileOut);
            }
        }
    }

    private void updateRow(Sheet sheet, int prevRow, int lastRow, int gameId, boolean gameState) {
        if(prevRow == lastRow && gameId != -1 && prevGameId < gameId) {
            Row row = sheet.getRow(lastRow);
            if (gameState) {
                Cell winsCell = row.getCell(1);
                winsCell.setCellValue(winsCell.getNumericCellValue() + 1);
            } else {
                Cell losesCell = row.getCell(2);
                losesCell.setCellValue(losesCell.getNumericCellValue() + 1);
            }
            prevGameId = gameId;
        }
    }

    private void createNewRowNextDay(Workbook wb, Sheet sheet, int prevRow, int lastRow, int gameId, boolean gameState) {
        if (prevRow < lastRow) {
            Row row = sheet.createRow(lastRow);
            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat(PATTERN));
            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(new Date());
            dateCell.setCellStyle(cellStyle);
            Cell winsCell = row.createCell(1);
            Cell losesCell = row.createCell(2);
            if(gameId == -1) {
                winsCell.setCellValue(0);
                losesCell.setCellValue(0);
            } else if (gameState) {
                winsCell.setCellValue(1);
                losesCell.setCellValue(0);
            } else {
                winsCell.setCellValue(0);
                losesCell.setCellValue(1);
            }
        }
    }

    private int getLastRowInWinRates(Sheet sheet) {
        int lastRow = sheet.getLastRowNum();
        if(lastRow == 0) {
            return 1;
        }
        try {
            Cell currentCell = sheet.getRow(sheet.getLastRowNum()).getCell(0);
            Date  lastDate = currentCell.getDateCellValue();
            if (Objects.nonNull(lastDate)) {
                lastDate = DATE_FORMAT.parse(lastDate.toString());
                Date tempDate = DATE_FORMAT.parse(new Date().toString());
                if (lastDate.before(tempDate)) {
                    lastRow++;
                }
            } else {
                lastRow++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastRow;
    }
}

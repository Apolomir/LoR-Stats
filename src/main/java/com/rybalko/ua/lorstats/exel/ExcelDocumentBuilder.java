package com.rybalko.ua.lorstats.exel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelDocumentBuilder {

    private static final Path DEFAULT_FILE = Paths.get("files/stats.xls");

    public void buildDocument(JSONObject response) throws IOException {
        createIfNotExists();
        buildWinRateSheet(response);
    }

    private void createIfNotExists() throws IOException {
        if(Files.notExists(DEFAULT_FILE)) {
            Files.createDirectory(Paths.get("files"));

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Win Rate");
            try  (OutputStream fileOut = new FileOutputStream("files/stats.xls")) {
                wb.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildWinRateSheet(JSONObject response) throws IOException {
        Workbook wb = WorkbookFactory.create(new File(DEFAULT_FILE.toString()));


    }
}

package com.rybalko.ua.lorstats.exel;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocumentOpener {

    public void openStats() throws IOException {
        File source = new File("files/stats.xls");
        File dest = new File("LoR Stats.xls");

        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }

        Desktop desktop = Desktop.getDesktop();
        if(dest.exists()) desktop.open(dest);
    }
}

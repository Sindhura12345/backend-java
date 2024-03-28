package com.example.word_orientation_converter;

import org.apache.poi.xwpf.usermodel.*;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordOrientationConverter {
    public static void main(String[] args) {
        try {
            // Load the input Word document
            FileInputStream fis = new FileInputStream("inn.docx");
            XWPFDocument document = new XWPFDocument(fis);

            // Check if any table extends beyond the page
            boolean extendBeyondPage = false;
            for (XWPFTable table : document.getTables()) {
                if (tableWidthInInches(table) > 8.5) {
                    extendBeyondPage = true;
                    break;
                }
            }

            // If tables extend beyond the page, convert to landscape orientation
            if (extendBeyondPage) {
                // Set landscape orientation
                CTSectPr sectPr = document.getDocument().getBody().getSectPr();
                if (sectPr == null) {
                    sectPr = document.getDocument().getBody().addNewSectPr();
                }
                CTPageSz pageSize = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr.addNewPgSz();
                pageSize.setOrient(STPageOrientation.Enum.forString("landscape"));
                pageSize.setW(java.math.BigInteger.valueOf(15840)); // 11 inches in twips
                pageSize.setH(java.math.BigInteger.valueOf(12240)); // 8.5 inches in twips
                
                // Save the modified document
                FileOutputStream fos = new FileOutputStream("output.docx");
                document.write(fos);
                fos.close();
                fis.close();
                System.out.println("Document converted to landscape successfully!");
            } else {
                System.out.println("No need to convert document to landscape. Content fits within the page.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to process the document.");
        }
    }

    private static float tableWidthInInches(XWPFTable table) {
        float widthInTwips = table.getWidth();
        return widthInTwips / 1440f; // Convert twips to inches
    }
}

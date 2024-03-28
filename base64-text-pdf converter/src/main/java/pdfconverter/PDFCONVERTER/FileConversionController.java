package pdfconverter.PDFCONVERTER;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileConversionController {
    private static final String DEFAULT_PDF_DIRECTORY = "C:\\Users\\sindhura.muthavarapu\\Documents"; // Ensure the path ends with a backslash

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertTextToPDF(@RequestPart("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String text = new String(bytes);

            byte[] pdfBytes = createPDF(text);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "converted_file.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] createPDF(String text) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            float margin = 50;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float startY = page.getMediaBox().getUpperRightY() - margin;
            float startX = margin;
            float leading = 1.5f * 12; // Adjusted leading

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);

                addTextToDocument(document, contentStream, text, startX, startY, leading, margin, width);
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        }
    }

    private void addTextToDocument(PDDocument document, PDPageContentStream contentStream, String text, float startX,
                                   float startY, float leading, float margin, float width) throws IOException {
        String[] lines = text.split("\\r?\\n");
        float currentY = startY - leading; // Adjust startY for the first line

        for (String line : lines) {
            String[] words = line.split("\\s+");
            StringBuilder lineBuilder = new StringBuilder();

            for (String word : words) {
                float size = PDType1Font.HELVETICA.getStringWidth(lineBuilder + word) / 1000 * 12;
                if (size > width) {
                    // Draw current line and move to the next line
                    contentStream.showText(lineBuilder.toString());
                    contentStream.newLineAtOffset(0, -leading);
                    currentY -= leading;
                    lineBuilder = new StringBuilder(word).append(" ");
                } else {
                    lineBuilder.append(word).append(" ");
                }

                if (currentY <= margin) {
                    contentStream.endText(); // End current page text
                    contentStream.close(); // Close current content stream properly

                    PDPage newPage = new PDPage(); // Create new page
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(startX, startY);
                    currentY = startY - leading;
                }
            }

            if (lineBuilder.length() > 0) {
                contentStream.showText(lineBuilder.toString());
                contentStream.newLineAtOffset(0, -leading);
                currentY -= leading;
            }
        }
        contentStream.endText(); // Ensure text is ended for the last page
        contentStream.close(); // Ensure content stream is closed properly
    }
}
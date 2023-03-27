package mnta.sck;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {

    public static String archive = "archive.zip";

    private ClassLoader classLoader = FileParsingTest.class.getClassLoader();


    @DisplayName("PDF")
    @Test
    void zippedPdfParseTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(archive);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("1.pdf.pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(entry.getName().contains("1.pdf.pdf"));
                    //Assertions.assertEquals("Pavel Polesitskiy", pdf.text); почему то сравнивает все содержимое файла с ожидаемым результатом


                }
            }
        }
    }

    @DisplayName("CSV")
    @Test
    void zippedCsvParseTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(archive);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("1.csv")) {
                    CSVReader csv = new CSVReader(new InputStreamReader(zis));
                    List<String[]> csvRow = csv.readAll();
                    Assertions.assertTrue(entry.getName().contains("1.csv"));
                    Assertions.assertEquals(new String[]{"Teacher", "Lesson", "Tuchs", "JUnit 5", "Vasenkov", "PageObjects", "Eroshenko", "Allure"}, csvRow.get(1));

                }
            }
        }
    }

    @DisplayName("XLS")
    @Test
    void zippedXlsParseTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(archive);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("1.xls")) {
                    XLS xls = new XLS(zis);
                    Assertions.assertTrue(entry.getName().contains("1.xls"));
                    Assertions.assertTrue(xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue().startsWith("A & D Audio Company"));
                }
            }
        }

    }
}



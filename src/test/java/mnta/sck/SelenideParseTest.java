package mnta.sck;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class SelenideParseTest {

    private ClassLoader cl = SelenideParseTest.class.getClassLoader();


    @Test
    void pdfParseTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File download = $("a[href*='junit-user-guide-5.9.2.pdf']").download();
        PDF pdf = new PDF(download);
        Assertions.assertEquals(
                "Stefan Bechtold," +
                        " Sam Brannen," +
                        " Johannes Link," +
                        " Matthias Merdes," +
                        " Marc Philipp," +
                        " Juliette de Rancourt," +
                        " Christian Stein",
                pdf.author);
    }

    @Test
    void xlsParseTest() throws Exception {
        Selenide.open("https://excelvba.ru/programmes/Teachers?ysclid=lfcu77j9j9951587711");
        File download = $("a[href*='teachers.xls']").download();
        XLS xls = new XLS(download);
        Assertions.assertTrue(
                xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue()
                        .startsWith("1. Суммарное количество часов планируемое на штатную по всем разделам плана")
        );


    }
    @Test
    void csvParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("expectedfiles/qaguru.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[] {"Tuchs", "JUnit 5"}, content.get(1));


        }


    }
    @Test
    void filesEqualsTest() throws Exception {
        Selenide.open("https://github.com/sckmnta/qaguru_files_17/blob/master/src/test/resources/expectedfiles/qaguru.csv");
        File download = $("#raw-url").download();
        try (InputStream isExpected = cl.getResourceAsStream("expectedfiles/qaguru.csv");
        InputStream downloaded = new FileInputStream(download)) {
            Assertions.assertEquals(
                    new String(isExpected.readAllBytes(), StandardCharsets.UTF_8),
                    new String(downloaded.readAllBytes(), StandardCharsets.UTF_8)
            );
        }

    }



}


package mnta.sck;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
                    Assertions.assertEquals("Kunshan Dual Adhesive Package International Trade Co., Ltd. \n" +
                            "昆山市双组份国际贸易有限公司\n" +
                            "Add: 1504, No.9 Building, Lane 231, Lvdi Road, Huaqiao Town, Kunshan city, Jiangsu Province, China\n" +
                            "Tel:+86-21-59774444 Fax: +86-21-59773775 Email: allen@nicejoon.com  Website: www.adhesive-pack.com\n" +
                            "PROFORMA INVOICE\n" +
                            "Buyer: Pavel Polesitsky Date: 20-Oct-21\n" +
                            "ATTN: Mr. Pavel Polesitsky PI NO.: KS152021-124\n" +
                            "Add.: Saint-Petersburg, Russia, Obvodniy emb, 134 PO NO.:\n" +
                            "Tel.: 7 921 361-61-16\n" +
                            "N Commodity Unit priceUSD\n" +
                            "QTY\n" +
                            "UNITS\n" +
                            "Total Amount \n" +
                            "USD\n" +
                            "1 Dual Cartridge 1500ml 1:1 $2.50 50 $125.0\n" +
                            "2 Static mixer B3 $0.44 120 $52.8\n" +
                            "3 Static mixer B4 + nozzles for B4 / 4 /\n" +
                            "Total Amount $177.80 \n" +
                            "Payment Term: EXW, 100% Paid in advance\n" +
                            "Delivery Time: In 15 days against payment received.\n" +
                            "Amount USD177.8\n" +
                            "Western Union Beneficiary\n" +
                            "First Name: HAIJIAO                 Last Name: WANG\n" +
                            "Mobile: +86-18721727500\n" +
                            "Address: No.1118 HuaTeng Rd, HuaXin Town, Qingpu District, Shanghai City 201708, China\n" +
                            "Diana Lau\n" +
                            "BUYER’S SIGNATURE SELLER’S SIGNATURE\n" +
                            "We Reserve The Right Not To Accept An Order If the Buyer Has Not Stamped & Signed Proforma Invoice\n", pdf.text);


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
                    Assertions.assertArrayEquals(new String[]{"Tuchs", "JUnit 5"}, csvRow.get(1));

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
                    Assertions.assertTrue(xls.excel.getSheetAt(0).getRow(0).getCell(1)
                            .getStringCellValue().startsWith("A & D Audio Company"));
                }
            }
        }

    }

    @DisplayName("Gson")
    @Test
    void jSonMonsterTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        try (InputStream is = classLoader.getResourceAsStream("monster.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Monster monster = gson.fromJson(isr, Monster.class);

            Assertions.assertEquals("Black Dragon", monster.monsterName);
            Assertions.assertEquals(7, monster.level);
            Assertions.assertEquals(25, monster.attack);
            Assertions.assertEquals(25, monster.defence);
            Assertions.assertEquals(300, monster.health);
            Assertions.assertEquals(15, monster.speed);
            Assertions.assertEquals(List.of("immune to all spells",
                    "hates Titans"), monster.specialAbilities);


        }
    }
}



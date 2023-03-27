package mnta.sck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFilesTest {

    @Test
    void selenideDownloadTest() throws Exception {

        open("https://github.com/qa-guru/niffler/blob/master/README.md");

        //File download = $("#raw-url").download();
        File download = $("a[href*='/qa-guru/niffler/raw/master/README.md']").download();

        InputStream is = new FileInputStream(download);
        byte[] bytes = is.readAllBytes();
        String fileAsString = new String(bytes, StandardCharsets.UTF_8);
        Assertions.assertTrue(fileAsString.contains("Технологии, использованные в Niffler"));
    }
}


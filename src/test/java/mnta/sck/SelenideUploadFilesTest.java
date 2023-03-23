package mnta.sck;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
public class SelenideUploadFilesTest {

    @BeforeAll
    static void beforeAll(){
        Configuration.browser = "Firefox";
    }

        @Test
        void selenideUploadFile () {
            open("https://fineuploader.com/demos.html");
            $("input[type='file']").uploadFromClasspath("cat.jpg");
            $("div.qq-file-info").shouldHave(Condition.text("cat.jpg"));
            $("span.qq-upload-file-selector").shouldHave(
                    Condition.attribute("title", "cat.jpg")
            );
        }
    }

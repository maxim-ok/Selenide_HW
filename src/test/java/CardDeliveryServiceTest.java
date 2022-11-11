import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardDeliveryServiceTest {


    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void inputPositive() {
        // Configuration.holdBrowserOpen = true;


        String planningDate = generateDate(3);

        open("http://localhost:9999/");
        $x("//span[@data-test-id='city']//input").val("Самара");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id='date']//input").val(planningDate);
        $x("//span[@data-test-id='name']//input").val("Иван Иванов");
        $x("//span[@data-test-id='phone']//input").val("+79998887744");
        $x("//label[@data-test-id='agreement']").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }


}

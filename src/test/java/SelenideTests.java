import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.*;

public class SelenideTests {


    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldTestForm() {
        String date = generateDate(3);

        open("http://localhost:9999/");
        SelenideElement form = $("form.form");
        form.$("[data-test-id=city] input").setValue("Казань");
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        form.$("[data-test-id=date] input").sendKeys(BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79993335522");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();

        SelenideElement notification = $("[data-test-id=notification]");
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        notification.$("div.notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
    }
}

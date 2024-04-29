import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void CompletionForm() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Белгород");
        String planningDate = generateData(3, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id = 'name'] input").setValue("Зеленский Александр");
        $("[data-test-id = 'phone'] input").setValue("+7904099405056");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void testForm() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Ря");
        $(byText("Рязань")).click();

        String planningDate = generateData(3, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id = 'name'] input").setValue("Зеленский Александр");
        $("[data-test-id = 'phone'] input").setValue("+79040994050");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void testCalendar() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Ря");
        $(byText("Рязань")).click();


        $("button.icon-button").click();
        $(byText("5")).click();
        $("[data-test-id = 'name'] input").setValue("Зеленский Александр");
        $("[data-test-id = 'phone'] input").setValue("+79040994050");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на 05.05.2024"));


    }
}

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.proxy.SelenideProxyServer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

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
        $("[data-test-id = 'phone'] input").setValue("+79040994050");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void testForm() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Во");
        $$(".menu-item__control").find(exactText("Воронеж")).click();

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
        $("[data-test-id = 'city'] input").setValue("Во");
        $$(".menu-item__control").find(exactText("Воронеж")).click();

        String planningDate = generateData(3, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").click();
        if (!generateData(3, "MM").equals(generateData(7, "MM"))){
            $("[data-step='1'] input").click();
        }
        $$(".calendar__day").findBy(Condition.text(generateData(7,"d"))).click();

        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id = 'name'] input").setValue("Зеленский Александр");
        $("[data-test-id = 'phone'] input").setValue("+79040994050");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на 08.05.2024"));


    }
}

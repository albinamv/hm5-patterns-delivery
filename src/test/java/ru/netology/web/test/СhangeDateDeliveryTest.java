package ru.netology.web.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.DataGenerator.UserInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class СhangeDateDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSendForm() {
        UserInfo user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(6, "dd.MM.yyyy");
        sendTheForm(user, date);

        String expected = "Встреча успешно запланирована на " + date;
        $("[data-test-id = success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(expected));

    }

    @Test
    public void shouldReplanDelivery() {
        UserInfo user = DataGenerator.Registration.generateUser("ru");
        String dateFirst = DataGenerator.generateDate(6, "dd.MM.yyyy");
        String dateSecond = DataGenerator.generateDate(4, "dd.MM.yyyy");

        sendTheForm(user, dateFirst);
        clearTheForm();
        sendTheForm(user, dateSecond);
        $("[data-test-id = replan-notification] .button").click();

        String expected = "Встреча успешно запланирована на " + dateSecond;
        $("[data-test-id = success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(expected));

    }

    public void sendTheForm(UserInfo user, String date) {
        $("[data-test-id = city] input").setValue(user.getCity());

        $("[data-test-id = date] input").doubleClick().sendKeys(" ");
        $("[data-test-id = date] input").setValue(date);

        $("[data-test-id = name] input").setValue(user.getName());
        $("[data-test-id = phone] input").setValue(user.getPhone());
        $("[data-test-id = agreement]").click();
        $$("form button").last().click();
    }

    public void clearTheForm() {
        $("[data-test-id = city] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id = city] input").sendKeys(Keys.BACK_SPACE);

        $("[data-test-id = date] input").doubleClick().sendKeys(" ");

        $("[data-test-id = name] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id = name] input").sendKeys(Keys.BACK_SPACE);

        $("[data-test-id = phone] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id = phone] input").sendKeys(Keys.BACK_SPACE);

        $("[data-test-id = agreement]").click();
    }

}

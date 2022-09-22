package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.DataGenerator.UserInfo;
import ru.netology.web.page.CardDeliveryPage;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class СhangeDateDeliveryTest {
    CardDeliveryPage deliveryPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        deliveryPage = new CardDeliveryPage();
    }

    @Test
    public void shouldPlanDelivery() {
        UserInfo user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(5, "dd.MM.yyyy");

        deliveryPage.sendTheForm(user, date);

        String expected = "Встреча успешно запланирована на " + date;
        deliveryPage.checkDateNotification(expected);
    }

    @Test
    public void shouldReplanDelivery() {
        UserInfo user = DataGenerator.Registration.generateUser("ru");
        String dateFirst = DataGenerator.generateDate(6, "dd.MM.yyyy");
        String dateSecond = DataGenerator.generateDate(4, "dd.MM.yyyy");

        deliveryPage.sendTheForm(user, dateFirst);
        deliveryPage.clearTheForm();
        deliveryPage.replanDeliveryDate(user, dateSecond);

        String expected = "Встреча успешно запланирована на " + dateSecond;
        deliveryPage.checkDateNotification(expected);
    }

    @Test
    @DisplayName("Should send form with user, which name contains \"Ё\"")
    public void shouldSendFormWithYoUser() {
        UserInfo user = DataGenerator.Registration.generateYoUser("ru");
        String date = DataGenerator.generateDate(5, "dd.MM.yyyy");

        deliveryPage.sendFormWithYoUser(user, date);

        String expected = "Встреча успешно запланирована на " + date;
        deliveryPage.checkDateNotification(expected);
    }

    @Test
    public void shouldNotSendFormWithWrongCity() {
        UserInfo user = DataGenerator.Registration.generateUserWithWrongCity("ru");
        String date = DataGenerator.generateDate(5, "dd.MM.yyyy");

        deliveryPage.sendFormWithWrongCity(user, date);
    }

    @Test
    public void shouldNotSendFormWithWrongPhone() {
        UserInfo user = DataGenerator.Registration.generateUserWithWrongPhone("ru");
        String date = DataGenerator.generateDate(5, "dd.MM.yyyy");

        deliveryPage.sendFormWithWrongPhone(user, date);
    }

}

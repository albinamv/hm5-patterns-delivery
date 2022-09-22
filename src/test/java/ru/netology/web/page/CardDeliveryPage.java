package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryPage {
    private SelenideElement cityField = $("[data-test-id = city] input");
    private SelenideElement dateField = $("[data-test-id = date] input");
    private SelenideElement nameField = $("[data-test-id = name] input");
    private SelenideElement phoneField = $("[data-test-id = phone] input");
    private SelenideElement agreementBox = $("[data-test-id = agreement]");
    private SelenideElement sendButton = $$("form button").last();
    private SelenideElement replanButton = $("[data-test-id = replan-notification] .button");
    private SelenideElement successNotification = $("[data-test-id = success-notification]");
    //private SelenideElement error = $("[data-test-id = error-notification]");

    public void sendTheForm(DataGenerator.UserInfo user, String date) {
        cityField.setValue(user.getCity());

        dateField.doubleClick().sendKeys(" ");
        dateField.setValue(date);

        nameField.setValue(user.getName());
        phoneField.setValue(user.getPhone());
        agreementBox.click();
        sendButton.click();
    }

    public void replanDeliveryDate(DataGenerator.UserInfo user, String date) {
        sendTheForm(user, date);
        replanButton.click();
    }

    public void sendFormWithWrongCity(DataGenerator.UserInfo user, String date) {
        cityField.setValue(user.getCity());

        dateField.doubleClick().sendKeys(" ");
        dateField.setValue(date);

        nameField.setValue(user.getName());
        phoneField.setValue(user.getPhone());
        agreementBox.click();
        sendButton.click();

        cityField.closest(".input__inner").find(".input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    public void sendFormWithWrongPhone(DataGenerator.UserInfo user, String date) {
        cityField.setValue(user.getCity());

        dateField.doubleClick().sendKeys(" ");
        dateField.setValue(date);

        nameField.setValue(user.getName());
        phoneField.setValue(user.getPhone());
        agreementBox.click();
        sendButton.click();

        phoneField.closest(".input__inner").find(".input__sub").shouldHave(text("Мобильный телефон указан неверно"));
    }

    public void sendFormWithYoUser(DataGenerator.UserInfo user, String date) {
        cityField.setValue(user.getCity());

        dateField.doubleClick().sendKeys(" ");
        dateField.setValue(date);

        nameField.setValue(user.getName());
        phoneField.setValue(user.getPhone());
        agreementBox.click();
        sendButton.click();
    }

    public void checkDateNotification(String expected) {
        successNotification.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(expected));
    }

    public void clearTheForm() {
        cityField.sendKeys(Keys.CONTROL + "A");
        cityField.sendKeys(Keys.BACK_SPACE);

        dateField.doubleClick().sendKeys(" ");

        nameField.sendKeys(Keys.CONTROL + "A");
        nameField.sendKeys(Keys.BACK_SPACE);

        phoneField.sendKeys(Keys.CONTROL + "A");
        phoneField.sendKeys(Keys.BACK_SPACE);

        agreementBox.click();
    }
}

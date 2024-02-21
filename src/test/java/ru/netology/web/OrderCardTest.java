package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class OrderCardTest {
    @Test
    void shouldOrderCard() { // Проверка формы обратной связи при правильной валидации полей
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Сергей Селиванов-Снеговой");
        form.$("[data-test-id=phone] input").setValue("+79154276543");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotOrderCardWhenNameInvalid() { // Неправильно заполнено имя
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Ivan Ivanov");
        form.$("[data-test-id=phone] input").setValue("+79154276543");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotOrderCardWhenNameIsEmpty() { // Не заполнено имя
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79154276543");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotOrderCardWhenPhoneIsInvalid() { // Неправильно заполнен номер телефона
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Сергей Селиванов-Снеговой");
        form.$("[data-test-id=phone] input").setValue("123456");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotOrderCardWhenPhoneIsEmpty() { // Не заполнен номер телефона
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Сергей Селиванов-Снеговой");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotOrderCardWhenCheckboxIsEmpty() { // Не стоит галочка на соглашении с условиями
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Сергей Селиванов-Снеговой");
        form.$("[data-test-id=phone] input").setValue("+79119592303");
        // form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=agreement].input_invalid").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}

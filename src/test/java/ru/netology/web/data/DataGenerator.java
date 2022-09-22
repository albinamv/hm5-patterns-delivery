package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static String[] cities = {"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала",
            "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь",
            "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан",
            "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск",
            "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань",
            "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск",
            "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Санкт-Петербург",
            "Липецк", "Магадан", "Красногорск", "Мурманск", "Нижний Новгород", "Великий Новгород",
            "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань",
            "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь",
            "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург",
            "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард"};

    public static String generateDate(int shift, String pattern) {
        LocalDate date = LocalDate.now().plusDays(shift);
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String generateCity(String locale) {
        int rnd = new Random().nextInt(cities.length);
        return cities[rnd];
    }

    public static String generateWrongCity(String locale) {
        return "Уссурийск";
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateYoName(String locale) {
        return "Алёна Ёркина";
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }

        public static UserInfo generateUserWithWrongCity(String locale) {
            return new UserInfo(generateWrongCity(locale), generateName(locale), generatePhone(locale));
        }

        public static UserInfo generateUserWithWrongPhone(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone("us"));
        }

        public static UserInfo generateYoUser(String locale) {
            return new UserInfo(generateCity(locale), generateYoName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

}

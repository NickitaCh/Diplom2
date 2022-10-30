package user;

public class UserGenerator {
    public static User generatorCoOne() {
        return new User("nick@mail.ru", "123321", "Nick");
    }

    public static User generatorCoTwo() {
        return new User("", "123321", "Nick");
    }

    public static User generatorCoThree() {
        return new User("fghfghg", "5436hh3w", "");
    }
}
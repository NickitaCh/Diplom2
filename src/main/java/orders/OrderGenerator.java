package orders;

public class OrderGenerator {
    public static Order correctOrder() {
        return new Order(new String[]{"61c0c5a71d1f82001bdaaa6d"});
    }

    public static Order wrongOrder() {
        return new Order(new String[]{"345345534"});
    }
}
import java.util.HashMap;

public class Bank {
    public int convert(Integer value, String key, String currency, HashMap wallet) throws Exception {
        Integer amountFromMap = (Integer) wallet.get(currency);
        if (amountFromMap == null) throw new Exception("No such currency");
        return (int) ((Math.random() * (-39)) + (value - 20));
    }
}

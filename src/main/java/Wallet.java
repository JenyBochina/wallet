import java.util.HashMap;
import java.util.Map;

public class Wallet {
    HashMap<String, Integer> wallet = new HashMap<>();
    Bank bank = new Bank();
    MoneyPrinter printer = new MoneyPrinter();

    void addMoney(String currency, Integer amount) {
        Integer amountFromMap = wallet.get(currency);
        if (amountFromMap == null) amountFromMap = amount;
        else amountFromMap += amount;
        wallet.put(currency, amountFromMap);
        printer.print("Added money", currency, amount);
    }

    void removeMoney(String currency, Integer amount) throws Exception {
        Integer amountFromMap = wallet.get(currency);
        if (amountFromMap == null) throw new Exception("No such currency");
        else if (amountFromMap - amount < 0) throw new Exception("Not enough money");
        wallet.put(currency, amountFromMap - amount);
        printer.print("Removed money", currency, amount);
    }

    Integer getMoney(String currency) {
        Integer amountFromMap = wallet.get(currency);
        if (amountFromMap == null) return 0;
        return amountFromMap;
    }

    String getCurrency() {
        String allCurrency = "";
        for (Map.Entry<String, Integer> entry : wallet.entrySet()) {
            if (entry.getValue() > 0) allCurrency += entry.getKey()+" ";
        }
        return allCurrency;
    }

    int getTotalMoney(String desiredCurrency) throws Exception {
        int allMoney = 0;
        for (Map.Entry<String, Integer> entry : wallet.entrySet()) {
            if (entry.getValue() > 0) {
                if (entry.getKey()==desiredCurrency) allMoney +=entry.getValue();
                else allMoney += bank.convert(entry.getValue(), entry.getKey(), desiredCurrency, wallet);
            }
        }
        return allMoney;
    }

    @Override
    public String toString() {
        String allCurrencyAndAmount = "{ ";
        for (Map.Entry<String, Integer> entry : wallet.entrySet()) {
            allCurrencyAndAmount += entry.getValue() + " " + entry.getKey() + ", ";
        }
        if (!allCurrencyAndAmount.equals("{ "))
            allCurrencyAndAmount = allCurrencyAndAmount.substring(0, allCurrencyAndAmount.length() - 2);
        allCurrencyAndAmount += " }";
        return allCurrencyAndAmount;
    }
}

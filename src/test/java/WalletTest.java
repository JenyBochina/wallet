import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletTest {
    @InjectMocks
    Wallet wallet;
    @Mock
    Bank bank;
    @Mock
    MoneyPrinter printer;

    @Test
    public void testAddMoney() throws Exception {
        doAnswer(invocation-> {System.out.println("Called printer");return null;}).when(printer).print(anyString(), anyString(),anyInt());
        wallet.addMoney("RUR", 100);
        wallet.addMoney("USD", 100);
        wallet.addMoney("RUR", 100);
        String result = wallet.toString();
        Assert.assertEquals("{ 100 USD, 200 RUR }", result);
    }

    @Test(expected = Exception.class)
    public void testRemoveMoney() throws Exception {
        doAnswer(invocation-> {System.out.println("Called printer");return null;}).when(printer).print(anyString(), anyString(),anyInt());
        wallet.addMoney("RUR", 100);
        wallet.addMoney("USD", 100);
        wallet.removeMoney("RUR", 50);
        Assert.assertEquals("{ 100 USD, 50 RUR }", wallet.toString());
        wallet.removeMoney("RUR", 50);
        Assert.assertEquals("{ 100 USD, 0 RUR }", wallet.toString());
        wallet.removeMoney("USD", 150);
    }

    @Test
    public void testGetMoney() throws Exception {
        wallet.addMoney("RUR", 100);
        wallet.addMoney("USD", 100);
        Integer result = wallet.getMoney("RUR");
        Assert.assertEquals(Integer.valueOf(100), result);
        wallet.removeMoney("RUR", 100);
        result = wallet.getMoney("RUR");
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testGetCurrency() throws Exception {
        wallet.addMoney("RUR", 100);
        wallet.addMoney("USD", 100);
        String result = wallet.getCurrency();
        Assert.assertEquals("USD RUR ", result);
    }

    @Test
    public void testGetTotalMoney() throws Exception {
        wallet.addMoney("RUR", 100);
        wallet.addMoney("USD", 100);
        wallet.addMoney("EUR", 100);
        when(bank.convert(anyInt(), anyString(), anyString(), any())).thenReturn(110);

        int result = wallet.getTotalMoney("RUR");
        Assert.assertEquals(320, result);
    }
}

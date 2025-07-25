import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetLocalNumber() {
        int expected = this.getLocalNumber(14);
        int actual = 2 * 7;
        Assert.assertTrue( "getLocalNumber not equal 14",actual == expected);

    }
}

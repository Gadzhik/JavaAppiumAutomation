import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{
    int number_get_class_n = this.getClassNumber();

    @Test
    public void testGetLocalNumber() {
        int expected = this.getLocalNumber(14);
        int actual = 2 * 7;
        Assert.assertTrue( "getLocalNumber not equal 14",actual == expected);

    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue( "getClassNumber less then 45", number_get_class_n > 45);
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends CoreTestCase
{
    @Before
    public void textStartTest()
    {
        System.out.println("Start Test");
    }

    @After
    public void textFinishTest()
    {
        System.out.println("Finish Test");
    }

    @Test
    public void firstTest()
    {
        System.out.println("First Test");
    }

    @Test
    public void secondTest()
    {
        System.out.println("Second Test");
    }
}

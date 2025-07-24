import org.junit.Test;

public class MainTest extends CoreTestCase
{
    public void typeStartMessage()
    {
        super.typeStartMessage();
        System.out.println("Current class is MainTest");
    }

   @Test
    public void myFirstTest() {
        this.typeStartMessage();
   }

    @Test
    public void mySecondTest() {
        this.typeStartMessage();
    }
}

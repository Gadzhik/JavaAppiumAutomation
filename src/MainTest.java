import org.junit.Test;

public class MainTest {
    @Test
    public void myFirstTest()
    {
        int a = 11;
        int b = 20;

        if(a > b) {
            System.out.println("This will never happen");
        } else {
            System.out.println("This is what will happen");
        }
    }
}

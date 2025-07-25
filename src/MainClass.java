import org.junit.Test;

public class MainClass
{
    private String class_string = "Hello, world";

    private int class_number = 20;

    public String getClassString() {
        return this.class_string;
    }

    public int getClassNumber() {
        return this.class_number;
    }

    public int getLocalNumber(int number)
    {
        return number = 14;
    }

}

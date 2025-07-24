import org.junit.Test;

public class MainTest extends CoreTestCase
{
    // Создаем объект класса MathHelper
    MathHelper Math = new MathHelper();

    // Переменные написанные вне функции - являются полями класса. Они видны в каждой функции класса. Мы пожем обратиться к полю класса через this.a
    int a = 3;
    int b = 12;

    @Test
    public void myFirstTest()
    {
        int a = Math.multiplay(3);
        System.out.println(a);

        int b = Math.multiplay(10, 17);
        System.out.println(b);
    }

}

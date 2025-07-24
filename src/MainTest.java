import org.junit.Test;

public class MainTest
{
    // Переменные написанные вне функции - являются полями класса. Они видны в каждой функции класса. Мы пожем обратиться к полю класса через this.a
    int a = 3;
    int b = 12;

    @Test
    public void myFirstTest()
    {
        int a = this.multiplay(3);
        System.out.println(a);

        int b = this.multiplay(10, 17);
        System.out.println(b);
    }

    // Добавляем функцию для вывода текста
    public int multiplay(int number)
    {
        return number * 2;
    }

    public int multiplay(int number, int multiplier)
    {
        return number * multiplier;
    }
}

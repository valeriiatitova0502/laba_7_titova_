// Объявление пакета, в котором находится класс
package org.example;

// Импорт класса Semaphore из пакета java.util.concurrent, используемого для контроля доступа к ресурсам
import java.util.concurrent.Semaphore;

// Объявление публичного класса "Tunnel", который наследуется от абстрактного класса "Stage"
public class Tunnel extends Stage {
    // Объявление семафора, который ограничивает количество автомобилей в тоннеле
    Semaphore smp = new Semaphore(Main.TUNNEL_CONSTRAINT);

    // Конструктор класса, инициализирующий длину и описание этапа
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    // Переопределение абстрактного метода "go" из класса "Stage"
    @Override
    public void go(Car c) {
        try {
            try {
                // Печать сообщения о том, что автомобиль готовится к этапу и ждет в очереди на вход в тоннель
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);

                // Получение разрешения на выполнение операции (въезд в тоннель)
                smp.acquire();

                // Печать сообщения о начале выполнения этапа (въезд автомобиля в тоннель)
                System.out.println(c.getName() + " начал этап: " + description);

                // Остановка потока на время, необходимое для прохождения этапа
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                // Отправка стека ошибки в поток вывода, если происходит прерывание во время ожидания
                e.printStackTrace();
            } finally {
                // Печать сообщения о завершении этапа (выезд из тоннеля)
                System.out.println(c.getName() + " закончил этап: " + description);

                // Освобождение разрешения (автомобиль вышел из тоннеля, место освободилось)
                smp.release();
            }
        } catch (Exception e) {
            // Отправка стека ошибки в поток вывода, если происходит исключение кроме прерывания
            e.printStackTrace();
        }
    }
}
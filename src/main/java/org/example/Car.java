package org.example;
// Импортируем классы CountDownLatch и CyclicBarrier из пакета java.util.concurrent
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

// Класс "Car", реализующий интерфейс "Runnable" (чтобы работать как отдельный поток)
public class Car implements Runnable {

    // Статические переменные: общее количество машин в гонке, флаг, указывающий на то, найден ли победитель,
    // массив для победителей и счетчик для победителей, а также объект блокировки для синхронизации
    private static int CARS_COUNT;
    private static boolean winnerFound;
    private static final Car[] winners = new Car[3];
    private static int winnerIndex = 0;
    private static final Object lock = new Object();

    // Статический блок для инициализации переменной CARS_COUNT
    static {
        CARS_COUNT = 0;
    }

    // Объявление переменных экземпляра: барьеры, гонки, скорости и имя
    private CyclicBarrier cb;
    private CountDownLatch cdl;
    private Race race;
    private int speed;
    private String name;

    // Геттер для получения имени автомобиля
    public String getName() {
        return name;
    }

    // Геттер для получения скорости автомобиля
    public int getSpeed() {
        return speed;
    }

    // Метод для проверки, был ли найден победитель
    public static boolean isWinnerFound() {
        return winnerFound;
    }

    // Конструктор класса "Car"
    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch cdl) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++; // увеличение количества машин
        this.name = "Участник #" + CARS_COUNT; // формирование имени машины
        // присваивание барьера и CountDownLatch в переменные экземпляра
        this.cb = cb;
        this.cdl = cdl;
    }

    // Переопределенный метод "run" из интерфейса "Runnable"
    @Override
    public void run() {
        try {
            // Подготовка машины к гонке
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            // Ожидание, когда все машины будут готовы
            cb.await();

            // Процесс гонки
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            // Проверка на победу
            if (!isWinnerFound()) {
                finalizePosition(this);
            }

            // Уменьшение счетчика оставшихся машин после финиша каждой машины
            cdl.countDown();
        } catch (Exception e) {
            e.printStackTrace(); // печать информации об ошибке в случае исключительной ситуации
        }
    }

    // Метод для проверки победителя
    private static void CheckWin(Car car) {
        synchronized (lock) {
            if (!winnerFound) {
                for (int i = 0; i < 3; i++) {
                    if (winners[i] == null) {
                        winners[i] = car;
                        break;
                    }
                }
                if (winners[0] != null && !winnerFound) {
                    System.out.println("WIN >>> " + winners[0].getName());
                    winnerFound = true;
                }
            }
        }
    }

    // Метод для вывода победителей
    public static void printWinners() {
        System.out.println("ПОБЕДИТЕЛИ");
        for (int i = 0; i < winnerIndex; i++) {
            if (winners[i] != null) {
                System.out.println((i+1) + " МЕСТО: " + winners[i].getName());
            }
        }
    }

    // Метод для финализации позиции
    // Метод для финализации позиции
    public static void finalizePosition(Car car) {
        synchronized (lock) {
            if (winnerIndex < 3) {
                winners[winnerIndex] = car;
                System.out.println("МЕСТО " +(winnerIndex+1)+ " >>> " + winners[winnerIndex].getName());
                winnerIndex++;
            }
        }
    }
}
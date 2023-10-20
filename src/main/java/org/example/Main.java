package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

// Главный класс, содержащий действие гонки
public class Main {
    static final int CARS_COUNT = 7; // Количество машин в гонке
    public static final int TUNNEL_CONSTRAINT = 3; // Ограничение на количество машин в туннеле


    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!"); // Сообщение о начале подготовки
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT+1); // Барьер, ожидающий подготовки всех участников гонки
        CountDownLatch cdl = new CountDownLatch(CARS_COUNT); // Защелка, ожидающая конца гонки от всех участников
        Race race = new Race(new Road(60), new Tunnel(), new Road(40)); // Создание трассы
        Car[] cars = new Car[CARS_COUNT]; // Создание массива участников гонки

        // Инициализация каждого автомобиля с случайной скоростью
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 26 + (int) (Math.random() * 5), cb, cdl);
        }
        // Запуск гонки для каждого автомобиля
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        // Ожидание начала гонки
        try {
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"); // Start race message
        } catch (Exception e) {
            e.printStackTrace();
        }
        // В конце метода main, после ожидания окончания гонки через cdl
        try {
            // Ожидание конца гонки
            cdl.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!"); // Конец гонки

            // Выводим информацию о победителях
            Car.printWinners();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
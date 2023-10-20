// Декларация пакета org.example
package org.example;

// Класс "Road" наследуется от абстрактного класса "Stage"
public class Road extends Stage {

    // Конструктор класса "Road", принимающий параметр "length" (длина дороги)
    public Road(int length) {

// Устанавливаем длину дороги
        this.length = length;

// Устанавливаем описание дороги, включающее в себя длину дороги
        this.description = "Дорога " + length + " метров";
    }

    // Переопределение метода "go" для "Road".
// Этот метод реализует логику действия автомобиля, который движется по дороге
    @Override
    public void go(Car c) {
        try {
            synchronized (System.out) {
                System.out.println(c.getName() + " начал этап: " + description);
            }

            Thread.sleep(length / c.getSpeed() * 1000);

            synchronized (System.out) {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} 
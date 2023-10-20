// Объявление пакета, в котором находится класс
package org.example;

// Объявление публичного абстрактного класса "Stage"
public abstract class Stage {
    // Защищенная переменная, хранящая длину этапа
    protected int length;

    // Защищенная переменная для хранения текстового описания этапа
    protected String description;

    // Публичный метод, возвращающий описание этапа
    public String getDescription() {
        return description;
    }

    // Абстрактный метод "go", который принимает автомобиль 
    // и определяет, как автомобиль преодолевает этот этап гонки. 
    // Будет реализован в подклассах
    public abstract void go(Car c);

}
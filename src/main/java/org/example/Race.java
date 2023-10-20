// Объявление пакета, в котором содержится класс
package org.example;

// Импорт классов ArrayList и Arrays из пакетов java.util для работы со списками и массивами соответственно
import java.util.ArrayList;
import java.util.Arrays;

// Объявление публичного класса "Race"
public class Race {
    // Создание частного переменного списка для хранения этапов гонки
    private ArrayList<Stage> stages;

    // Публичный метод возвращения списка этапов гонки
    public ArrayList<Stage> getStages() {
        return stages;
    }

    // Конструктор класса "Race", который принимает переменное количество аргументов типа "Stage" 
    // и преобразует их в ArrayList
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
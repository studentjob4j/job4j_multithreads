package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 19.05.2021
 * в данном патерне проектирования double - check -locking нужно использовать в поле inst
 * значение volatile т.к есть вероятность , что если поле inst = null то первый поток вошедший
 * в этот метод начнет создавать объект , происходит следующее
 * выделяется память под объект в куче , создается указатель на объект, создается сам объект
 * при этом есть вероятность того , что между 2 и 3 шагом другой поток перехватит указатель
 * и начнет пользоватся не полностью сконструированным объектом  и создатся 2 объекта ,
 * а используя volatile такое исключается , т.к другой поток увидит сразу ,
 * что поле inst изменилось
 */

public final class DCLSingleton {

    private volatile static DCLSingleton inst;

    private DCLSingleton() {
    }

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
}

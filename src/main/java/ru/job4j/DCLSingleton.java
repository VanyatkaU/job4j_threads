package ru.job4j;

/**
 * @author Karelkin Ivan
 * @version 1.0
 * @parameter: double check locking - блокировка с двойной проверкой
 * приводит к дополнительным накладным расходам на синхронизацию, один поток
 *  может не завершить инициализацию переменной, а другой поток (думает, что определена)
 *  возвращает ее значение без получения блокировки (прочитает не обновленное значение и
 *  работа программы может быть некорректна)
 *  После инициализации синхронизация будет избыточна, т.к. буде проводить чтение переменной.
 *  Volatile позволяет записать значение переменной сразу в основную память, что гарантирует
 *  чтение последнего записанного значения переменной.
 */

public class DCLSingleton {

    private static volatile DCLSingleton inst;

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

    private DCLSingleton() {
    }
}

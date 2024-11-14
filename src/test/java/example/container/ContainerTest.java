package example.container;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверяет корректность работы методов Container.
 */
class ContainerTest {

    Container container = new Container();
    Item item = new Item(123);

    /**
     * Тест для добавления элемента
     * Проверяет, что после добавления элемента не выкидывает ошибки
     *
     */
    @Test
    void addError() {
        container.add(item);
        assertDoesNotThrow(()->container.add(item));
    }

    /**
     * Тест для удаления элемента
     * Проверяет, что после удаления элемента не выкидывает ошибку
     */
    @Test
    void removeError() {
        container.add(item);
        container.remove(item);
        assertDoesNotThrow(()->container.add(item));

    }
}
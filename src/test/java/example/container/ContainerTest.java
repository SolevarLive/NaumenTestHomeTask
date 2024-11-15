package example.container;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверяет корректность работы методов Container.
 */
class ContainerTest {

    private final Container container = new Container();
    private final Item item = new Item(123);
    private final Item item1 = new Item(456);

    /**
     * Тест для добавления элемента
     * Проверяет, что после добавления элемента
     * - размер контейнера увеличивается на 1
     * - добавленный элемент присутствует в контейнере
     */
    @Test
    void add() {
        container.add(item);
        assertEquals(1, container.size());
        assertTrue(container.contains(item));
    }

    /**
     * Тест для удаления элемента
     * Проверяет, что после удаления элемента
     * - размер контейнера уменьшается на 1
     * - удаленный элемент отсутствует в контейнере
     */
    @Test
    void remove() {
        container.add(item);
        container.remove(item);
        assertEquals(0, container.size());
        assertFalse(container.contains(item));
    }

    /**
     * Тест добавления нескольких элементов
     * Проверяет, что после добавления нескольких элементов
     * - размер контейнера равен количеству добавленных элементов
     * - контейнер содержит все добавленные элементы
     */
    @Test
    void addMultipleItems() {
        container.add(item);
        container.add(item1);
        assertEquals(2, container.size());
        assertTrue(Set.of(item, item1).stream().allMatch(container::contains));
    }

    /**
     * Тест удаления нескольких элементов
     * Проверяет корректность удаления нескольких элементов по очереди
     */
    @Test
    void removeMultipleItems() {
        container.add(item);
        container.add(item1);
        container.remove(item);
        assertEquals(1, container.size());
        assertFalse(container.contains(item));
        assertTrue(container.contains(item1));
        container.remove(item1);
        assertEquals(0, container.size());
    }

    /**
     * Тест метода get с индексом вне границ
     * Проверяет, что при обращении к элементу с индексом вне границ выбрасывается исключение IndexOutOfBoundsException
     */
    @Test
    void testGetOutOfBounds() {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            container.get(1);
        });

        assertEquals("Index 1 out of bounds for length 0", exception.getMessage());
    }

}
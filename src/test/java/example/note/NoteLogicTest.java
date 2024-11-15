package example.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Проверяем команды NoteLogic
 */
class NoteLogicTest {

    /**
     * Экземпляр класса NoteLogic для тестирования
     */
     private final NoteLogic noteLogic = new NoteLogic();

    /**
     * Тест для команды /add и /notes
     * Проверяет, что после добавления заметки команда /notes
     * возвращает правильный ответ
     */
    @Test
    void add_and_notesTest() {
        String message = "/add Hello world!";
        String expected = "Note added!";

        String actual = noteLogic.handleMessage(message);
        assertEquals(expected, actual);

        String notesMessage = "/notes";
        String expectedNotes = "Your notes: Hello world!";
        actual = noteLogic.handleMessage(notesMessage);
        assertEquals(expectedNotes, actual);
    }

    /**
     * Тест для команды /edit и /notes
     * Проверяет, что после редактирования заметки команда /notes
     * возвращает правильный ответ
     */
    @Test
    void edit_and_notesTest() {
        String message = "/edit Hello worlds!";
        String expected = "Note edited!";

        String actual = noteLogic.handleMessage(message);
        assertEquals(expected, actual);

        String notesMessage = "/notes";
        String expectedNotes = "Your notes: Hello worlds!";
        actual = noteLogic.handleMessage(notesMessage);
        assertEquals(expectedNotes, actual);
    }

    /**
     * Тест для команды /del и /notes.
     * <br>
     * Проверяет, что после удаления заметки команда /notes
     * возвращает пустой список заметок.
     */
    @Test
    void del_and_notesTest() {
        String message = "/del Hello worlds!";
        String expected = "Note deleted!";

        String actual = noteLogic.handleMessage(message);
        assertEquals(expected, actual);

        String notesMessage = "/notes";
        String expectedNotes = "Your notes: ";
        actual = noteLogic.handleMessage(notesMessage);
        assertEquals(expectedNotes, actual);
    }

    /**
     * Тест для обработки неизвестной команды
     * Проверяет, что NoteLogic бросает IllegalArgumentException для неизвестных сообщений
     */
    @Test
    void unknownTest() {
        String message = "/unknowncommand";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            noteLogic.handleMessage(message);
        });
        assertEquals("Unknown command", exception.getMessage());
    }
}
package example.bot;

import java.util.ArrayList;
import java.util.List;

 /**
 * MockBot является реализацией интерфейса Bot, используемой для тестирования
 */
public class MockBot implements Bot {
    private final List<String> messages = new ArrayList<>();

    /**
     * Сохраняет сообщение в список отправленных сообщений
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Возвращает отправленные сообщения
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
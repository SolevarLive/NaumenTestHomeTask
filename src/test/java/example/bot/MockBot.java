package example.bot;

import java.util.ArrayList;
import java.util.List;

 /**
 * MockBot является реализацией интерфейса Bot, используемой для тестирования
 */
public class MockBot implements Bot {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

     /**
      * Возвращает сообщение по указанному индексу
      *
      * @param index Индекс сообщения в списке
      * @return Сообщение, соответствующее указанному индексу
      * @throws IndexOutOfBoundsException Если индекс выходит за границы списка сообщений
      */
     public String getMessageAtIndex(int index) {
         if (index < 0 || index >= messages.size()) {
             throw new IndexOutOfBoundsException("Индекс за пределами");
         }
         return messages.get(index);
     }

    /**
     * Возвращает отправленные сообщения
     */
    public List<String> getMessages() {
        return messages;
    }
}
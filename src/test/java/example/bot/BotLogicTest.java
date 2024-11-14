package example.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Тесты для команд бота BotLogic
 */
public class BotLogicTest {

    private BotLogic bot;
    private User user;
    private MockBot mockBot;

    /**
     * Этот метод инициализирует необходимые объекты для тестирования
     */
    @BeforeEach
    public void setUp() {
        mockBot = new MockBot();
        bot = new BotLogic(mockBot);
        user = new User(12345L);
    }

    /**
     * Тестирует команду /notify
     * Проверяет, что бот правильно устанавливает напоминание и отправляет уведомление через заданное время(имитируем ожидание)
     */
    @Test
    public void testNotify() throws InterruptedException {
        bot.processCommand(user, "/notify");
        bot.processCommand(user, "Привет!");
        bot.processCommand(user, "2");

        String actualSetReminderResponse = null;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 2000) {
            actualSetReminderResponse = mockBot.getLastMessage();
            if (actualSetReminderResponse != null && actualSetReminderResponse.contains("Сработало напоминание: 'Привет!'")) {
                break;
            }
            Thread.sleep(100);
        }

        List<String> expectedMessages = Arrays.asList(
                "Введите текст напоминания",
                "Через сколько секунд напомнить?",
                "Напоминание установлено",
                "Сработало напоминание: 'Привет!'"
        );

        List<String> actualMessages = mockBot.getMessages();
        assertEquals(expectedMessages, actualMessages);

    }


    /**
     * Тестирует команду повторения вопроса в боте.
     * Этот тест проверяет, что бот корректно обрабатывает команду "/repeat"
     */
    @Test
    public void testRepeatCommandFalse() {
        bot.processCommand(user, "/repeat");

        String expectedQuestion = "Нет вопросов для повторения";
        String actualQuestion = mockBot.getLastMessage();
        assertEquals(expectedQuestion, actualQuestion);
    }
    /**
     * Тестирует команду повторения вопроса в боте.
     * Этот тест проверяет, что бот отправляет тест на повторение (командйо "/repeat") полсе ошибочного ответа в тесте
     */
    @Test
    public void testRepeatCommandTrue(){
        bot.processCommand(user, "/test");
        bot.processCommand(user, "10");
        bot.processCommand(user, "6");
        bot.processCommand(user, "/repeat");
        bot.processCommand(user, "100");

        List<String> expectedMessages = Arrays.asList(
                "Вычислите степень: 10^2",
                "Вы ошиблись, верный ответ: 100",
                "Сколько будет 2 + 2 * 2",
                "Правильный ответ!",
                "Тест завершен",
                "Вычислите степень: 10^2",
                "Правильный ответ!",
                "Тест завершен"
        );

        List<String> actualMessages = mockBot.getMessages();
        assertEquals(expectedMessages, actualMessages);
    }

    /**
     * Проверка команды /test
     * Проверяет, что бот отправляет правильные сообщения в правильной последовательности
     * на корректные ответы
     */
    @Test
    public void testCommandTest() {
        bot.processCommand(user, "/test");
        bot.processCommand(user, "100");
        bot.processCommand(user, "6");


        List<String> expectedMessages = Arrays.asList(
                "Вычислите степень: 10^2",
                "Правильный ответ!",
                "Сколько будет 2 + 2 * 2",
                "Правильный ответ!",
                "Тест завершен"
        );

        List<String> actualMessages = mockBot.getMessages();
        assertEquals(expectedMessages, actualMessages);
    }

    /**
     * MockBot является реализацией интерфейса Bot, используемой для тестирования
     */
    class MockBot implements Bot {
        private final List<String> messages = new ArrayList<>();

        /**
         * Сохраняет сообщение в список отправленных сообщений
         */
        @Override
        public void sendMessage(Long chatId, String message) {
            messages.add(message);
        }

        /**
         * Возвращает последнее отправленное сообщение
         */
        public String getLastMessage() {
            return messages.isEmpty() ? null : messages.get(messages.size() - 1);
        }


        /**
         * Возвращает отправленные сообщения
         */
        public List<String> getMessages() {
            return new ArrayList<>(messages);
        }
    }
}
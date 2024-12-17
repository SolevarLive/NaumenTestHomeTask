package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



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
     * Проверка команды /test
     * Проверяет, что бот отправляет правильные сообщения в правильной последовательности
     * на корректные ответы
     */
    @Test
    public void testCommandTest() {
        bot.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessageAtIndex(0));
        bot.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessageAtIndex(1));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessageAtIndex(2));
        bot.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessageAtIndex(3));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessageAtIndex(4));
    }

    /**
     * Проверка команды /test
     * Проверяет, что бот отправляет правильные сообщения в правильной последовательности
     * на не корректные ответы
     */
    @Test
    public void testCommandTestIncorrectAnswers() {
        bot.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessageAtIndex(0));
        bot.processCommand(user, "10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100",
                mockBot.getMessageAtIndex(1));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessageAtIndex(2));
        bot.processCommand(user, "5");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6",
                mockBot.getMessageAtIndex(3));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessageAtIndex(4));
    }

    /**
     * Тестирует команду повторения вопроса в боте.
     * Этот тест проверяет, что бот отправляет тест на повторение (командйо "/repeat") полсе ошибочного ответа в тесте
     */
    @Test
    public void testRepeatCommand(){
        bot.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessageAtIndex(0));
        bot.processCommand(user, "10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100",
                mockBot.getMessageAtIndex(1));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessageAtIndex(2));
        bot.processCommand(user, "6");
        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessageAtIndex(5));
        bot.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessageAtIndex(6));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessageAtIndex(7));

        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Нет вопросов для повторения",
                mockBot.getMessageAtIndex(8));
    }

    /**
     * Тестирует команду /notify
     * Проверяет, что бот правильно устанавливает напоминание и отправляет уведомление через заданное время
     */
    @Test
    public void testNotify() throws InterruptedException {
        bot.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", mockBot.getMessageAtIndex(0));
        bot.processCommand(user, "Привет!");
        Assertions.assertEquals("Через сколько секунд напомнить?", mockBot.getMessageAtIndex(1));
        bot.processCommand(user, "1");
        Assertions.assertEquals("Напоминание установлено", mockBot.getMessageAtIndex(2));
        Assertions.assertEquals(3, mockBot.getMessages().size());

        Thread.sleep(1010);
        Assertions.assertEquals("Сработало напоминание: 'Привет!'", mockBot.getMessageAtIndex(3));
    }

    /**
     * Тестирование /notify при установке
     * не целого числа времени
     */
    @Test
    public void testNotifyNegativeTime() {
        bot.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания",mockBot.getMessageAtIndex(0));
        bot.processCommand(user, "Привет!");
        Assertions.assertEquals("Через сколько секунд напомнить?", mockBot.getMessageAtIndex(1));
        bot.processCommand(user, "2.5");
        Assertions.assertEquals("Пожалуйста, введите целое число", mockBot.getMessageAtIndex(2));
        bot.processCommand(user, "1");
        Assertions.assertEquals("Напоминание установлено", mockBot.getMessageAtIndex(3));
    }
}
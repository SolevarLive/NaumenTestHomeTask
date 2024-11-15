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
                mockBot.getMessages().get(0));
        bot.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessages().get(1));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessages().get(2));
        bot.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessages().get(3));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessages().get(4));
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
                mockBot.getMessages().get(0));
        bot.processCommand(user, "10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100",
                mockBot.getMessages().get(1));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessages().get(2));
        bot.processCommand(user, "5");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6",
                mockBot.getMessages().get(3));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessages().get(4));
    }

    /**
     * Тестирует команду повторения вопроса в боте.
     * Этот тест проверяет, что бот отправляет тест на повторение (командйо "/repeat") полсе ошибочного ответа в тесте
     */
    @Test
    public void testRepeatCommand(){
        bot.processCommand(user, "/test");
        bot.processCommand(user, "10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100",
                mockBot.getMessages().get(1));
        bot.processCommand(user, "6");
        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessages().get(5));
        bot.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessages().get(6));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessages().get(7));

        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Нет вопросов для повторения",
                mockBot.getMessages().get(8));
    }

    /**
     * Тестирует команду повторения вопроса в боте.
     * Этот тест проверяет, что бот отправляет тест на повторение (командйо "/repeat") полсе ошибочных ответов в тесте
     */
    @Test
    public void testRepeatCommandIncorrectAnswers(){
        bot.processCommand(user, "/test");
        bot.processCommand(user, "10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100",
                mockBot.getMessages().get(1));
        bot.processCommand(user, "5");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6",
                mockBot.getMessages().get(3));
        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2",
                mockBot.getMessages().get(5));
        bot.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessages().get(6));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                mockBot.getMessages().get(7));
        bot.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!",
                mockBot.getMessages().get(8));
        Assertions.assertEquals("Тест завершен",
                mockBot.getMessages().get(9));

        bot.processCommand(user,"/repeat");
        Assertions.assertEquals("Нет вопросов для повторения",
                mockBot.getMessages().get(10));
    }

    /**
     * Тестирует команду /notify
     * Проверяет, что бот правильно устанавливает напоминание и отправляет уведомление через заданное время
     */
    @Test
    public void testNotify() throws InterruptedException {
        bot.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", mockBot.getMessages().get(0));
        bot.processCommand(user, "Привет!");
        Assertions.assertEquals("Через сколько секунд напомнить?", mockBot.getMessages().get(1));
        bot.processCommand(user, "2");
        Assertions.assertEquals("Напоминание установлено", mockBot.getMessages().get(2));
        Assertions.assertEquals(3, mockBot.getMessages().size());

        Thread.sleep(2010);
        Assertions.assertEquals("Сработало напоминание: 'Привет!'", mockBot.getMessages().get(3));
    }

    /**
     * Тестирование ошибки при установке уведомления
     * с не целым числом значения времени
     */
    @Test
    public void testNotifyNegativeTime() {
        bot.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", mockBot.getMessages().get(0));
        bot.processCommand(user, "Привет!");
        Assertions.assertEquals("Через сколько секунд напомнить?", mockBot.getMessages().get(1));
        bot.processCommand(user, "2.5");
        Assertions.assertEquals("Пожалуйста, введите целое число", mockBot.getMessages().get(2));
    }


}
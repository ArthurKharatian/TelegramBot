package telegramBot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bot extends TelegramLongPollingBot {
    private final Logger log = Logger.getLogger("Logger");

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMessage(update.getMessage().getChatId().toString(), message);
        if(update.hasMessage()){
            ThreadClass thread = new ThreadClass(update.getMessage());
        }else if(update.hasCallbackQuery()){
            AnswerCallbackThread callbackThread  = new AnswerCallbackThread(update.getCallbackQuery());
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "BotName";
    }
    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "BotToken";
    }
    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param text Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMessage(String chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public synchronized void setButtons(SendMessage sendMessage){
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Hello");
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add("Help");
        // Добавляем все строчки клавиатуры в список
        // и устанваливаем этот список нашей клавиатуре
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void setInline(){
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        buttons1.add(new InlineKeyboardButton().setText("Button").setCallbackData("17"));
        buttons.add(buttons1);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(buttons);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}

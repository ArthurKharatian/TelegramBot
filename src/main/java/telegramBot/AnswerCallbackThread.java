package telegramBot;

import org.telegram.telegrambots.api.objects.CallbackQuery;

public class AnswerCallbackThread {
    private CallbackQuery callbackQuery;

    public AnswerCallbackThread(CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
    }
}

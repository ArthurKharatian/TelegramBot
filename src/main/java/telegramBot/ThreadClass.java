package telegramBot;

import org.telegram.telegrambots.api.objects.Message;

public class ThreadClass {
    private Message message;
    public ThreadClass(Message message) {
        this.message = message;
    }
}

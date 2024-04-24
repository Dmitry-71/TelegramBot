package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import static org.example.Utils.getURL;

public class TelegramBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String NASA_URL = "https://api.nasa.gov/planetary/apod?" +
            "api_key=9CrDyWmZPruI6VdlOB11UQqWFbPd8KgJin8XeCW3";

    public TelegramBot(String botName, String botToken) throws TelegramApiException {
        BOT_NAME = botName;
        BOT_TOKEN = botToken;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String[] separatedText = text.split(" ");

            switch (separatedText[0]) {
                case "/help":
                    sendMessage("I'm Roscosmos bot, enter /image or /картинка", chatId);
                    break;
                case "/image":
                    try {
                        String image = Utils.getURL(NASA_URL);
                        sendMessage(image, chatId);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                case "/date":
                    try {
                        String image = Utils.getURL(NASA_URL + "&data=" + separatedText[1]);
                        sendMessage(image, chatId);
                        break;
                    } catch (IOException e) {
                        System.out.println("Server unable");;
                    }

                default:
                    sendMessage("Не понял", chatId);
            }
        }
    }

    void sendMessage(String msgText, long chatId) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText(msgText);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            System.out.println("Error");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}

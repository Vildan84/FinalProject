package org.example.telegram;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;


public class MyBot extends TelegramLongPollingBot {

    Logger log = Logger.getLogger(MyBot.class);


    String token = "5592303329:AAEiqI2f6F62Zv8I7oFuy2bQLqvEHy8yMUM";
    String username = "vildan84Bot";

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        DownloadFile file = new DownloadFile();
        String id = update.getMessage().getDocument().getFileId();

        try {
            file.GetFile(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


//        if(update.hasMessage() && update.getMessage().getText().equals("/start")) {
//            String chatId = update.getMessage().getChatId().toString();
//
//            SendMessage sm = new SendMessage();
//            sm.setChatId(chatId);
//            sm.setText("Upload CSV file");
//
//            try {
//                execute(sm);
//            } catch (TelegramApiException e) {
//                //todo add logging to the project.
//                e.printStackTrace();
//            }
//
//
//        }

    }
}

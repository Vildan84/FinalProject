package my.project.CSVlogBot.service;


import lombok.extern.slf4j.Slf4j;
import my.project.CSVlogBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    static final String HELP_Text = "Здесь будет инструкция по ипользованию бота";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Click to start bot"));
        listOfCommands.add(new BotCommand("/help", "Click to get help"));

        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            log.error("Error setting BotCommand list: " + e.getMessage());
        }


    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            switch (messageText){
                case "/start" :
                    startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help" :
                    sendMessage(chatId, HELP_Text);
                    break;
                default: sendMessage(chatId, "Command not supported");
                break;
            }
        }
    }

    private void startCommandRecieved(Long chatID, String name){

        String answer = "Привет, "+ name +", пркрепи нужный LOG формата VCDS";
        log.info("Replied to user: " + name);
        sendMessage(chatID, answer);


    }

    private void sendMessage(Long chatID, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }
}

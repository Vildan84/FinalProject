package my.project.CSVlogBot.service;


import lombok.extern.slf4j.Slf4j;
import my.project.CSVlogBot.config.BotConfig;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
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
        listOfCommands.add(new BotCommand("/ready", "Click to confirm file download"));
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

            switch (messageText) {
                case "/start" -> startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                case "/ready" -> sendMessage(chatId, "pass");
                case "/help" -> sendMessage(chatId, HELP_Text);
                default -> sendMessage(chatId, "Command not supported");
            }
        }
        else if(update.hasMessage() && update.getMessage().hasDocument()){
            String fileId = update.getMessage().getDocument().getFileId();
            DownloadFile file = new DownloadFile(config);
            try {
                file.GetFile(fileId);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startCommandRecieved(Long chatID, String name){

        String answer = "Привет, "+ name +", пркрепи нужный LOG формата VCDS и нажми ready";
        log.info("Replied to user: " + name);
        sendMessage(chatID, answer);
    }

    private void sendMessage(Long chatID, String  text){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(text);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }
}

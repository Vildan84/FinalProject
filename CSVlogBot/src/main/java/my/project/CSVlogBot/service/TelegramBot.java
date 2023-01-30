package my.project.CSVlogBot.service;

import lombok.extern.slf4j.Slf4j;
import my.project.CSVlogBot.config.BotConfig;
import my.project.CSVlogBot.csv.Column;
import my.project.CSVlogBot.csv.CreateColumn;
import my.project.CSVlogBot.csv.CreateColumnButtons;
import my.project.CSVlogBot.csv.ReadCSV;
import my.project.CSVlogBot.jfreechart.Chart;
import my.project.CSVlogBot.jfreechart.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    static final String HELP_Text = "Здесь будет инструкция по использованию бота";
    LinkedList<String> columnNames = new LinkedList<>();
    LinkedList<String[]> list = new LinkedList<>();

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

        ReadCSV cs = new ReadCSV();
        CreateColumn columns = new CreateColumn();
        CreateColumnButtons buttons = new CreateColumnButtons();
        XYDataset dataset = new XYDataset();
        Chart chart = new Chart(config);
        XYSeriesCollection xyCollection;

        if(update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start" -> startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                case "/help" -> sendMessage(chatId, HELP_Text);
                default -> sendMessage(chatId, "Command not supported");
            }
        }
        else if(update.hasMessage() && update.getMessage().hasDocument()){
            Long chatID = update.getMessage().getChatId();
            String fileId = update.getMessage().getDocument().getFileId();
            DownloadFile file = new DownloadFile(config);
            sendKeyboard(chatID, readyButton(), "Нажмите READY");
            try {
                file.GetFile(fileId, chatID);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(update.hasCallbackQuery()){
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callbackData = update.getCallbackQuery().getData();
            if(callbackData.equals("READY")){
                list = cs.ReadFile(config.getPath() + chatId);
                LinkedList<Column> columnsList = new LinkedList<>(columns.Columns(list));
                sendKeyboard(chatId, buttons.createButtons(columnsList), "Выберите необходимые колонки для построения графика");
            }
            else if(callbackData.equals("MAKE_CHART")){
                list = cs.ReadFile(config.getPath() + chatId);
                LinkedList<Column> columnsList = new LinkedList<>(columns.Columns(list));
                xyCollection = dataset.createDataset(columnsList, columnNames);
                try {
                    chart.chart(xyCollection, columnNames, columnsList);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sendingPhoto(chatId);
                } catch (MalformedURLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                columnNames.clear();

            }
            else{
                String columnName = update.getCallbackQuery().getData();
                columnNames.add(columnName);
            }
        }

    }

    private void startCommandRecieved(Long chatID, String name){

        String answer = "Привет, "+ name +", прикрепи нужный LOG, формата VCDS и нажми ready";
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

    private void sendKeyboard(Long chatID, InlineKeyboardMarkup markup, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(text);
        message.setReplyMarkup(markup);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void sendingPhoto(Long chatID) throws MalformedURLException, FileNotFoundException {
        SendPhoto photo = new SendPhoto();
        File file = ResourceUtils.getFile(config.getPath() + config.getPhoto());
        InputFile inputFile = new InputFile(file);
        photo.setChatId(chatID);
        photo.setPhoto(inputFile);

        try{
            execute(photo);
        }
        catch (TelegramApiException e){
            System.out.println("file is empty");
        }
    }

    private InlineKeyboardMarkup readyButton(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        var button = new InlineKeyboardButton("READY");
        button.setCallbackData("READY");
        buttons.add(button);
        list.add(buttons);
        markup.setKeyboard(list);
        return markup;
    }


}

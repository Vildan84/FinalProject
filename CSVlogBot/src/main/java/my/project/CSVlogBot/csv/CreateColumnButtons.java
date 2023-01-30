package my.project.CSVlogBot.csv;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CreateColumnButtons {
    public InlineKeyboardMarkup createButtons(LinkedList<Column> colList){

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        for(Column c: colList){
            List<InlineKeyboardButton> singleList = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(c.getName());
            button.setCallbackData(c.getName());
            singleList.add(button);
            list.add(singleList);
        }
        List<InlineKeyboardButton> singleList = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("MAKE CHART");
        button.setCallbackData("MAKE_CHART");
        singleList.add(button);
        list.add(singleList);

        markup.setKeyboard(list);

        return markup;
    }
}

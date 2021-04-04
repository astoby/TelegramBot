package Telegram;

import Exceptions.Calendar.MonthException;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.Calendar.Calendar;
import service.HibernateService.UserService;
import service.Telegram.TelegramService;
import service.Weather.WeatherBot;
import utils.Commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelegramMethods extends TelegramService {

    public static void sendMsg(Message message, BotTelegram botTelegram) throws TelegramApiException {
//        user = UserService.getUser(message.getChatId());
        messageOptions(message);
//        if (!UserService.checkChatIdUser(message.getChatId())) {
//            user.setUserName(message.getChat().getFirstName() + " " + message.getChat().getLastName());
//            user.setChatId(message.getChatId());
//            UserService.addUser(user);
//        }
        writeMsg(message, botTelegram);

    }

    private static void writeMsg(Message message, BotTelegram botTelegram) {
        try {
            setButtons(sendMessage);
//            if (message.hasLocation()) {
//                sendMessage.setText(weatherParser.getReadyForecast(parceGeo(message)));
//            } else if (Commands.fromString(message.getText()).isPresent()) {
//                chosenCommand(message);
//            } else {
//                switch (checkMode(message.getChatId())) {
//                    case "WEATHER":
                        sendMessage.setText(weatherParser.getReadyForecast(message.getText(),1))
                                .setReplyMarkup(Calendar.createHours());
//                        break;
//                    default:
//                        sendMessage.setText("Выберите режим");
//                        break;
//                }

            //}
            botTelegram.execute(sendMessage);
        } catch (TelegramApiException | MonthException e) {
            e.printStackTrace();
        }
    }



    private static void chosenCommand(Message message) throws MonthException {
        int currentMonth = LocalDate.now().getMonth().getValue();
                sendMessage.setText("Выберите число")
                        .setReplyMarkup(Calendar.createHours());

    }

    public static void sendMsgFromCallBack(CallbackQuery callbackQuery, BotTelegram botTelegram) throws TelegramApiException, MonthException {
        messageOptions(callbackQuery.getMessage());
        editMessageOptions(callbackQuery.getMessage());
//        editMessageText.setText("Выберите месяц")
//                .setReplyMarkup(chooseAnswer(callbackQuery));
//        botTelegram.execute(editMessageText);
        if (callbackQuery.getData().split("'")[0].equals("day")){
            String city = callbackQuery.getMessage().getText().split(":")[0];
            int days = Integer.parseInt(callbackQuery.getData().split("'")[1]);
            editMessageText.setText(weatherParser.getReadyForecast(city,days))
                    .setReplyMarkup((InlineKeyboardMarkup) Calendar.createHours());

        }
        botTelegram.execute(editMessageText);
    }

    private static void setButtons(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Погода"));
        keyboardFirstRow.add(new KeyboardButton("Календарь"));

        keyboardRows.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private static void setGeoLocationButton(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Отправить свою локацию").setRequestLocation(true));

        keyboardRows.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}

import DAO.HibernateController;
import Telegram.BotTelegram;
import model.User;
import org.apache.log4j.PropertyConfigurator;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;
import service.HibernateService.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class main {
    static BotSession session = null;
    static BotTelegram botTelegram = null;

    public static void main(String[] args) {
//        test();
        startBot();
    }

    private static void startBot() {
        PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.properties");
        ApiContextInitializer.init();
        botTelegram = new BotTelegram("KVV_WeatherBot", "1736075260:AAEtt974NbptA6rC6h8CA5HEdGY9l1wM8js");
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            session = botsApi.registerBot(botTelegram);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        System.out.println("run");
    }

    private static void test() {
//        User user = (User) HibernateController.getRowByField(new User(),"chatId", 100354L);
//        System.out.println(UserService.getMode(100354L));
        LocalDateTime date = LocalDateTime.now();
    }
}

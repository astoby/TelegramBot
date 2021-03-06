package service.HibernateService;

import DAO.HibernateController;
import model.User;
import utils.Actions;

public class UserService {

    public static void addUser(User user){
        HibernateController.doHibernateAction(user, Actions.SAVE);
    }

    public static void deleteUser(User user){
        HibernateController.doHibernateAction(user, Actions.DELETE);
    }

    public static void updateUser(User user){
        HibernateController.doHibernateAction(user, Actions.UPDATE);
    }

    public static boolean checkChatIdUser(Long chatId){
        return HibernateController.getColumnByField(new User(),"chatId").contains(chatId);
    }

    public static User getUser(Long chatId){
        return (User) HibernateController.getRowByField(new User(),"chatId",chatId);
    }

    public static String getMode(Long chatId){
        User user = (User) HibernateController.getRowByField(new User(),"chatId",chatId);
        return user.getMode();
    }
}

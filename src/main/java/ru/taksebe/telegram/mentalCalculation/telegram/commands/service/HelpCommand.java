package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.taksebe.telegram.mentalCalculation.Utils;


public class HelpCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String toLog = getCommandIdentifier() + " || " + getDescription() + " || " + user.toString() + " || " + chat.toString() + " || " + absSender.toString();
        System.out.println(toLog);
        DBConn.logIn(toLog);
        String userName = Utils.getUserName(user);
        System.out.println(userName+ "вызов хелп");
        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        String text = "Не поможет кек ";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                text);
        DBConn.logOut(text + " || " + toLog);
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
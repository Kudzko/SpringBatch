package by.kudko.batch;

import by.kudko.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class BathSomeProcessor implements ItemProcessor<User, User> {
    @Override
    public User process(User user) throws Exception {
        String name = user.getName();
        user.setNickName(name + "PROCESSED");
        log.info("User: " + user + "PROCESSED");
        return user;
    }
}

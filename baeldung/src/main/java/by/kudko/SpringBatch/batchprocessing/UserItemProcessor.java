package by.kudko.SpringBatch.batchprocessing;

import by.kudko.SpringBatch.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class UserItemProcessor implements ItemProcessor <User, User> {
    @Override
    public User process(final User user) throws Exception {
        final String firstName = user.getName().toUpperCase();
        final String surname = user.getSurname().toUpperCase();
        final String nickName = user.getNickName().toUpperCase();
        final User transformedUser = new User(firstName, surname, nickName);

        log.info("Converting " + user + " into " + transformedUser);

        return transformedUser;
    }
}

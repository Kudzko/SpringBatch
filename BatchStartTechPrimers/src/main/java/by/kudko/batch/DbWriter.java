package by.kudko.batch;

import by.kudko.model.User;
import by.kudko.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class DbWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {
        log.info("Data Saved for Users : " + users);
        userRepository.saveAll(users);

    }
}

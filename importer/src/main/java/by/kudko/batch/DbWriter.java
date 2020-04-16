package by.kudko.batch;

import by.kudko.model.TempEntity;
import by.kudko.repository.TempEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class DbWriter implements ItemWriter<TempEntity> {

    @Autowired
    private TempEntityRepository tempEntityRepository;

    @Override
    public void write(List<? extends TempEntity> tempEntities) throws Exception {
        log.info("Data Saved for tempEntities : " + tempEntities);
        tempEntityRepository.saveAll(tempEntities);

    }
}

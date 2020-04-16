package by.kudko.batch;

import by.kudko.model.TempEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class BatchSomeProcessor implements ItemProcessor<TempEntity, TempEntity> {
    @Override
    public TempEntity process(TempEntity tempEntity) throws Exception {
//        String name = tempEntity.getName();
//        tempEntity.setName(name + "PROCESSED");
        log.info("BatchSomeProcessor: PROCESSED");
        return tempEntity;
    }
}

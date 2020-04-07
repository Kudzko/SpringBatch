package by.kudko.batch;

import by.kudko.model.Hotel;
import by.kudko.repository.HotelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class DBWriter2 implements ItemWriter<Hotel> {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void write(List<? extends Hotel> hotels) throws Exception {
        log.info("Data Saved for Users : " + hotels);
        hotelRepository.saveAll(hotels);

    }
}

package by.kudko.repository;

import by.kudko.model.TempEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempEntityRepository extends JpaRepository<TempEntity, Integer> {
}

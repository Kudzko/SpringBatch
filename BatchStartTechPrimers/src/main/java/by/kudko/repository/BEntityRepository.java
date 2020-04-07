package by.kudko.repository;

import by.kudko.model.BEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BEntityRepository extends JpaRepository<BEntity,Integer> {
}

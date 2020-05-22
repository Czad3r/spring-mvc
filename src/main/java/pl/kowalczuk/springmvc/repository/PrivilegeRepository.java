package pl.kowalczuk.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalczuk.springmvc.domain.entities.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
}

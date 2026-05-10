package education.dao_hibernate.repository;

import education.dao_hibernate.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // CRUD методы

    List<Person> findByCityOfLiving(String city);

    List<Person> findByAgeLessThanOrderByAgeAsc(Integer age);

    Optional<Person> findFirstByNameAndSurname(String name, String surname);
}
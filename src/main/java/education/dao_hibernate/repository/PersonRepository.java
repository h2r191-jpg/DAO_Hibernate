package education.dao_hibernate.repository;

import education.dao_hibernate.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getPersonsByCity(String city) {
        List<Person> allPersons = entityManager.createQuery(
                "SELECT p FROM Person p",
                Person.class
        ).getResultList();

        return allPersons.stream()
                .filter(person -> city.equalsIgnoreCase(
                        person.getCityOfLiving()))
                .collect(Collectors.toList());
    }
}
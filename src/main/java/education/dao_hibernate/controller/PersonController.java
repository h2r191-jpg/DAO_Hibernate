package education.dao_hibernate.controller;

import education.dao_hibernate.Person;
import education.dao_hibernate.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // CRUD: получить все
    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // CRUD: получить по ID
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
    }

    // CRUD: создать новую запись
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        // id генерируется автоматически, поле id игнорируем
        person.setId(null);
        return personRepository.save(person);
    }

    // CRUD: обновить существующую
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        person.setId(id);
        return personRepository.save(person);
    }

    // CRUD: удалить
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        personRepository.deleteById(id);
    }

    // поиск по городу
    @GetMapping("/by-city")
    public List<Person> getPersonsByCity(@RequestParam String city) {
        return personRepository.findByCityOfLiving(city);
    }

    // поиск младше возраста
    @GetMapping("/younger-than")
    public List<Person> getPersonsYoungerThan(@RequestParam Integer age) {
        return personRepository.findByAgeLessThanOrderByAgeAsc(age);
    }

    // поиск по имени и фамилии
    @GetMapping("/by-name")
    public ResponseEntity<Person> getPersonByNameAndSurname(@RequestParam String name,
                                                            @RequestParam String surname) {
        return personRepository.findFirstByNameAndSurname(name, surname)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
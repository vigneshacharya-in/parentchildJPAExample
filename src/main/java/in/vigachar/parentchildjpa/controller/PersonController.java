package in.vigachar.parentchildjpa.controller;

import in.vigachar.parentchildjpa.dtos.PersonDTO;
import in.vigachar.parentchildjpa.entity.Person;
import in.vigachar.parentchildjpa.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MalkeithSingh on 11-09-2019
 */
@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonRepo personRepo;

    @GetMapping
    public List<Person> getAllDetails() {
        return personRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getAllDetails(@PathVariable("id") Long id) {
        return personRepo.findById(id).map(mapToPersonDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/siblings")
    public ResponseEntity<Set<PersonDTO>> getAllSiblings(@PathVariable("id") Long id) {
        return personRepo.findById(id).map(findSiblings).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Function<Person, Set<PersonDTO>> findSiblings = person -> person.getParent().getChildren().stream()
            .map(p -> PersonDTO.builder().id(p.getId()).fullName(p.getFullName()).build()).collect(Collectors.toSet());

    private Function<Person, PersonDTO> mapToPersonDTO = p -> PersonDTO.builder().id(p.getId()).fullName(p.getFullName()).parent(p.getParent()).children(p.getChildren()).build();
}

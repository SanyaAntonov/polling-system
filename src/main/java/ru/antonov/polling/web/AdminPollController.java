package ru.antonov.polling.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.antonov.polling.model.Poll;
import ru.antonov.polling.repository.PollRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/poll")
@AllArgsConstructor
@Slf4j
@Tag(name = "Admin Poll Controller")
public class AdminPollController {

    private final PollRepository repository;

    @GetMapping("{id}")
    public ResponseEntity<Poll> get(@PathVariable("id") int id) {
        log.info("get poll {}", id);
        Poll found = repository.get(id)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAll() {
        log.info("get all polls");
        List<Poll> all = repository.getAll()
                .orElseThrow(() -> new NotFoundException("Polls not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete poll {}", id);
        repository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody Poll poll) {
        log.info("create poll {}", poll);
        Poll created = repository.save(poll);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Poll> update(@RequestBody Poll poll,
                                       @PathVariable("id") int id) {
        log.info("update Poll {}", id);
        Poll found = repository.get(id)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        if (found.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        poll.setId(id);
        Poll updated = repository.save(poll);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

package ru.antonov.polling.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.antonov.polling.model.Poll;
import ru.antonov.polling.model.Question;
import ru.antonov.polling.repository.PollRepository;
import ru.antonov.polling.repository.QuestionRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/poll/{pollId}/question")
@AllArgsConstructor
@Slf4j
@Tag(name = "Admin Question Controller")
public class AdminQuestionController {

    private final QuestionRepository questionRepository;
    private final PollRepository pollRepository;

    @GetMapping("{id}")
    public ResponseEntity<Question> get(@PathVariable("pollId") int pollId,
                                        @PathVariable("id") int id) {
        log.info("get poll {}", id);
        Question found = questionRepository.get(pollId, id)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAll(@PathVariable("pollId") int pollId) {
        log.info("get all polls");
        List<Question> all = questionRepository.getAll(pollId)
                .orElseThrow(() -> new NotFoundException("Questions not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("pollId") int pollId,
                       @PathVariable("id") int id) {
        log.info("delete question {}", id);
        questionRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Question> create(@PathVariable("pollId") int pollId,
                                           @RequestBody Question question) {
        log.info("create question {}", question);

        Poll found = pollRepository.get(pollId)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        if (found.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Question created = questionRepository.save(question);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Question> update(@RequestBody Question question,
                                           @PathVariable("pollId") int pollId,
                                           @PathVariable("id") int id) {
        log.info("update Poll {}", id);

        Poll found = pollRepository.get(pollId)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        if (found.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        questionRepository.get(pollId, id)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        question.setId(id);
        Question updated = questionRepository.save(question);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

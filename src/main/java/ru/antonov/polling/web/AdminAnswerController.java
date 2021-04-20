package ru.antonov.polling.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.antonov.polling.model.Answer;
import ru.antonov.polling.model.Poll;
import ru.antonov.polling.model.Question;
import ru.antonov.polling.repository.AnswerRepository;
import ru.antonov.polling.repository.PollRepository;
import ru.antonov.polling.repository.QuestionRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/poll/{pollId}/question/{questId}/answer")
@AllArgsConstructor
@Slf4j
@Tag(name = "Admin Answer Controller")
public class AdminAnswerController {

    private final QuestionRepository questionRepository;
    private final PollRepository pollRepository;
    private final AnswerRepository answerRepository;

    @GetMapping("{id}")
    public ResponseEntity<Answer> get(@PathVariable("pollId") int pollId,
                                      @PathVariable("questId") int questId,
                                      @PathVariable("id") int id) {
        log.info("get answer {}", id);
        Answer found = answerRepository.get(pollId, questId, id)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAll(@PathVariable("pollId") int pollId,
                                               @PathVariable("questId") int questId) {
        log.info("get all answers");
        List<Answer> all = answerRepository.getAll(questId)
                .orElseThrow(() -> new NotFoundException("Questions not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("pollId") int pollId,
                       @PathVariable("questId") int questId,
                       @PathVariable("id") int id) {

        log.info("delete answer {}", id);
        answerRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Answer> create(@RequestBody Answer answer,
                                         @PathVariable("pollId") int pollId,
                                         @PathVariable("questId") int questId) {
        log.info("create question {}", answer);

        Poll found = pollRepository.get(pollId)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        if (found.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Question question = questionRepository.findById(questId)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        answer.setQuestion(question);

        Answer created = answerRepository.save(answer);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Answer> update(@RequestBody Answer answer,
                                         @PathVariable("pollId") int pollId,
                                         @PathVariable("questId") int questId,
                                         @PathVariable("id") int id) {

        log.info("update answer {}", id);
        Poll found = pollRepository.get(pollId)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        if (found.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        Question question = questionRepository.get(pollId, id)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        answer.setQuestion(question);

        answerRepository.get(pollId, questId, id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));

        answer.setId(id);
        Answer updated = answerRepository.save(answer);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

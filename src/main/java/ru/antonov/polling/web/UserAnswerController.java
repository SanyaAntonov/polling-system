package ru.antonov.polling.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.antonov.polling.AuthUser;
import ru.antonov.polling.model.*;
import ru.antonov.polling.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/poll")
@AllArgsConstructor
@Slf4j
@Tag(name = "User Answer Controller")
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;
    private final PollRepository pollRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Poll>> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all polls for User {}", authUser.id());
        List<Poll> all = pollRepository.getAllForUser(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Polls not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Poll> get(@PathVariable("id") int id) {
        log.info("get poll {}", id);
        Poll found = pollRepository.getForUser(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<UserAnswer>> getAllHistory(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all polls history for User {}", authUser.id());
        List<UserAnswer> all = userAnswerRepository.getAll(authUser.id())
                .orElseThrow(() -> new NotFoundException("Polls not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<UserAnswer> get(@AuthenticationPrincipal AuthUser authUser,
                                          @PathVariable("id") int id) {
        log.info("get poll in history for user {}", id);
        UserAnswer found = userAnswerRepository.get(authUser.id(), id)
                .orElseThrow(() -> new NotFoundException("Poll not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }
    @GetMapping("{pollId}/question")
    public ResponseEntity<List<Question>> getAll(@PathVariable("pollId") int pollId) {
        log.info("get all polls");
        List<Question> all = questionRepository.getAll(pollId)
                .orElseThrow(() -> new NotFoundException("Questions not found"));

        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @GetMapping("{pollId}/question/{id}")
    public ResponseEntity<Question> get(@PathVariable("pollId") int pollId,
                                        @PathVariable("id") int id) {
        log.info("get poll {}", id);
        Question found = questionRepository.get(pollId, id)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("{pollId}/question/{questId}/answer")
    public ResponseEntity<UserAnswer> writeAnswer(@AuthenticationPrincipal AuthUser authUser,
                                                  @RequestBody Answer answer,
                                                  @PathVariable("pollId") int pollId,
                                                  @PathVariable("questId") int questId) {
        log.info("create UserAnswer {}", answer);

        User user = userRepository.findById(authUser.id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Question question = questionRepository.findById(questId)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        answer.setQuestion(question);
        answerRepository.save(answer);

        UserAnswer userAnswer = new UserAnswer(user, answer, question);

        UserAnswer created = userAnswerRepository.save(userAnswer);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("{pollId}/question/{questId}/answer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAnswer> voteForAnswer(@AuthenticationPrincipal AuthUser authUser,
                                                    @PathVariable("pollId") int pollId,
                                                    @PathVariable("questId") int questId,
                                                    @PathVariable("id") int id) {
        log.info("vote for Answer {}", id);

        User user = userRepository.findById(authUser.id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Question question = questionRepository.findById(questId)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));

        UserAnswer userAnswer = new UserAnswer(user, answer, question);

        UserAnswer created = userAnswerRepository.save(userAnswer);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

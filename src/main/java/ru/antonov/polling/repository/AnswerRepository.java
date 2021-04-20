package ru.antonov.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.antonov.polling.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("SELECT a FROM Answer a where a.question.id=:questId")
    Optional<List<Answer>> getAll(@Param("questId") int questId);

    @Query("SELECT a FROM Answer a WHERE a.question.poll.id=:pollId and a.question.id=:questId and a.id=:id")
    Optional<Answer> get(@Param("pollId") int pollId,
                         @Param("questId") int questId,
                         @Param("id") int id);
}

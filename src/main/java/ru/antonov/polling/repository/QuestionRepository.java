package ru.antonov.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.antonov.polling.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q where q.poll.id=:id")
    Optional<List<Question>> getAll(@Param("id") int id);

    @Query("SELECT q FROM Question q WHERE q.poll.id=:pollId and q.id=:id")
    Optional<Question> get(@Param("pollId") int pollId, @Param("id") int id);
}

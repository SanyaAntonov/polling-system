package ru.antonov.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.antonov.polling.model.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    @Query("SELECT a FROM UserAnswer a where a.user.id=:id order by a.question.poll.startDate desc")
    Optional<List<UserAnswer>> getAll(@Param("id") int id);

    @Query("SELECT a FROM UserAnswer a WHERE a.user.id=:userId and a.id=:id")
    Optional<UserAnswer> get(@Param("userId") int userId, @Param("id") int id);
}

package ru.antonov.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.antonov.polling.model.Poll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Integer> {

    @Query("select p from Poll p order by p.startDate desc")
    Optional<List<Poll>> getAll();

    @Query("SELECT p FROM Poll p WHERE p.id=:id")
    Optional<Poll> get(@Param("id") int id);
    @Query("SELECT p FROM Poll p WHERE p.id=:id and p.startDate <=:date and p.endDate > :date")
    Optional<Poll> getForUser(@Param("id") int id, @Param("date") LocalDate localDate);

    @Query("select p from Poll p where p.startDate <=:date and p.endDate > :date")
    Optional<List<Poll>> getAllForUser(@Param("date") LocalDate localDate);
}

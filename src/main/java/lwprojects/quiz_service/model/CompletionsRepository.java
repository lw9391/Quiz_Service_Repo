package lwprojects.quiz_service.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionsRepository extends JpaRepository<CompletionsEntity, Long> {
    @Query("SELECT q FROM Completions q WHERE q.user.email = ?1")
    Page<CompletionsEntity> findAllByUserEmail(String userEmail, Pageable pageable);
}
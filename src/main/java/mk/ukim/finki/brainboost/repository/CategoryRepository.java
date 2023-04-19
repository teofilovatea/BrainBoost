package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

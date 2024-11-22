package Energeenot.TestTaskFromSber.repository;

import Energeenot.TestTaskFromSber.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Student}.
 * <p>
 *     Представляет стандартные CRUD операции, унаследованные от {@link JpaRepository}
 * </p>
 *
 * @see Student
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}

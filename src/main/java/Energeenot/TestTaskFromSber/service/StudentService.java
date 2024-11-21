package Energeenot.TestTaskFromSber.service;

import Energeenot.TestTaskFromSber.model.Student;
import Energeenot.TestTaskFromSber.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления объектами {@link Student}.
 * <p>
 *     Этот класс предоставляет методы для выполнения CRUD операций над сущностью {@link Student}.
 *     Операции включают: получение всех студентов, получение студента по ID, добавление,
 *     обновление и удаление студента.
 * </p>
 * @see Student
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    /**
     * Репозиторий для работы с сущностью {@link Student}.
     * @see StudentRepository
     */
    private final StudentRepository studentRepository;

    /**
     * Возвращает список всех студентов.
     *
     * @return список объектов {@link Student}.
     */
    public List<Student> getAllStudents() {
        log.info("log: Вызван метод getAllStudents");
        return studentRepository.findAll();
    }

    /**
     * Возвращает студента по его идентификатору.
     *<p>
     *     @param id идентификатор студента.
     *     @return объект {@link Student}.
     *     @throws ResponseStatusException если студент с указанным идентификатором не найден.
     *</p>
     */
    public Student getStudentById(int id) {
        log.info("log: Вызван метод getStudentById с id {}", id);
        return studentRepository.findById(id).orElseThrow(() ->{
            log.error("log: Студент с id {} не найден", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        });

    }

    /**
     * Добавляет нового студента.
     *
     * @param student объект {@link Student} для добавления.
     * @return добавленный объект {@link Student}.
     */
    public Student addStudent(Student student) {
        log.info("log: Попытка добавления нового студента {}", student.toString());
        return studentRepository.save(student);
    }

    /**
     * Обновляет информацию о студенте.
     *
     * @param id идентификатор студента.
     * @param updatedStudent объект {@link Student} с обновленными данными.
     * @return обновленный объект {@link Student}.
     * @throws ResponseStatusException если студент с указанным идентификатором не найден.
     */
    public Student updateStudent(int id, Student updatedStudent) {
        log.info("log: Вызван метод updateStudent и передан студент {} с id {}", updatedStudent.toString(), id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("log: При обновлении сущности: студент с id {} не найден", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
                });

        Optional.ofNullable(updatedStudent.getSurname()).ifPresent(student::setSurname);
        Optional.ofNullable(updatedStudent.getName()).ifPresent(student::setName);
        Optional.ofNullable(updatedStudent.getPatronymic()).ifPresent(student::setPatronymic);
        Optional.of(updatedStudent.getAge()).ifPresent(student::setAge);
        Optional.ofNullable(updatedStudent.getAverageMark()).ifPresent(student::setAverageMark);
        return studentRepository.save(student);
    }

    /**
     * Удаляет студента по его идентификатору.
     *
     * @param id идентификатор студента.
     * @throws ResponseStatusException если студент с указанным идентификатором не найден.
     */
    public void deleteStudent(int id) {
        log.info("log: Вызван метод deleteStudent для удаления студента с id {}", id);
        studentRepository.deleteById(id);
    }
}

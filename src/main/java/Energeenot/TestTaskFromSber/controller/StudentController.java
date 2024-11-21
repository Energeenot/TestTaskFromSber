package Energeenot.TestTaskFromSber.controller;

import Energeenot.TestTaskFromSber.model.Student;
import Energeenot.TestTaskFromSber.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Контроллер для обработки HTTP-запросов, связанных с управлением студентами.
 * <p>
 * Этот контроллер предоставляет методы для создания, чтения, обновления и удаления данных о студентах.
 * Он взаимодействует с {@link StudentService} для выполнения CRUD операций.
 * </p>
 */
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    /**
     * Получение списка всех студентов.
     * <p>
     *     Метод обрабатывает HTTP GET запросы по пути {@code /students} и возвращает список всех студентов,
     *     хранящихся в базе данных.
     * </p>
     *
     * @return Список всех студентов.
     */
    @GetMapping
    public List<Student> getAllStudents() {
        log.info("log: Вызван метод getAllStudents");
        return studentService.getAllStudents();
    }

    /**
     * Получение информации о студенте по его ID.
     * <p>
     *     Метод обрабатывает HTTP GET запросы по пути {@code /students/{id}}, где {@code id} — это идентификатор студента.
     *     Если студент с таким ID найден, возвращается информация о нем.
     *     В случае отсутствия студента выбрасывается исключение.
     * </p>
     *
     * @param id Идентификатор студента, информацию о котором нужно получить.
     * @return Студент с указанным ID.
     * @throws ResponseStatusException Если студент с таким ID не найден.
     */
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        log.info("log: Вызван метод getStudentById с id {}", id);
        return studentService.getStudentById(id);
    }

    /**
     * Создание нескольких студентов.
     * <p>
     *     Метод обрабатывает HTTP POST запросы по пути {@code /students}.
     *     Принимает список студентов в теле запроса и сохраняет их в базе данных.
     *     В случае успеха возвращает сохранённый список студентов.
     * </p>
     *
     * @param students Список студентов для добавления.
     * @return Список добавленных студентов.
     * @throws ResponseStatusException Если произошла ошибка при сохранении студентов.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Student> createStudent(@RequestBody List<Student> students) {
        try {
            log.info("log: Пришёл запрос на создание студентов {}", students.toString());
            return studentService.addStudent(students);
        } catch (Exception e) {
            log.error("log: Произошла ошибка при сохранении студентов {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удаётся создать студентов");
        }
    }

    /**
     * Обновление данных студента.
     * <p>
     *     Метод обрабатывает HTTP PATCH запросы по пути {@code /students/{id}}.
     *     Обновляет данные студента с указанным ID.
     * </p>
     *
     * @param id      Идентификатор студента, данные которого нужно обновить.
     * @param student Обновлённая информация о студенте.
     * @return Обновлённый студент.
     * @throws ResponseStatusException Если студент с таким ID не найден.
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        log.info("log: Пришёл запрос на редактирование студента с id {}", id);
        return studentService.updateStudent(id, student);
    }

    /**
     * Удаление студента.
     * <p>
     *     Метод обрабатывает HTTP DELETE запросы по пути {@code /students/{id}}.
     *     Удаляет студента с указанным ID. Если удаление прошло успешно, возвращает статус 204 (NO_CONTENT).
     * </p>
     *
     * @param id Идентификатор студента, которого нужно удалить.
     * @throws ResponseStatusException Если произошла ошибка при удалении студента.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        try {
            log.info("log: Пришёл запрос на удаление студента с id {}", id);
            studentService.deleteStudent(id);
            log.info("log: Студент с id {} успешно удалён", id);
        } catch (Exception e) {
            log.error("log: Ошибка при удалении студента {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удаётся удалить студента");
        }
    }
}

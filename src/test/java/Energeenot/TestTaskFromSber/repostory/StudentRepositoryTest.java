package Energeenot.TestTaskFromSber.repostory;

import Energeenot.TestTaskFromSber.model.Student;
import Energeenot.TestTaskFromSber.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = Student.builder()
                .surname("Иванов")
                .name("Иван")
                .patronymic("Иванович")
                .age(21)
                .averageMark(4.50)
                .build();
        studentRepository.save(testStudent);
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void saveAllShouldSaveStudent() {
        Student firstStudent = Student.builder()
                .surname("Сидоров")
                .name("Алексей")
                .patronymic("Александрович")
                .age(22)
                .averageMark(4.0)
                .build();
        Student secondStudent = Student.builder()
                .surname("Петров")
                .name("Пётр")
                .patronymic("Петрович")
                .age(29)
                .averageMark(3.40)
                .build();
        List<Student> students = List.of(firstStudent, secondStudent);
        List<Student> savedStudent = studentRepository.saveAll(students);

        assertEquals("Сидоров", savedStudent.get(0).getSurname());
        assertEquals(29,savedStudent.get(1).getAge());
    }

    @Test
    void findAllShouldReturnStudents() {
        assertEquals(List.of(testStudent).size(), studentRepository.findAll().size());
    }

    @Test
    void findByIdShouldReturnStudent() {
        Optional<Student> student = studentRepository.findById(testStudent.getId());
        assertTrue(student.isPresent());
        assertEquals(testStudent, student.get());
    }

    @Test
    void updateShouldUpdateStudentAndThenCanBeFoundByIdWithUpdatedData() {
        testStudent.setName("Пётр");
        testStudent.setAge(23);
        studentRepository.save(testStudent);

        Student updatedStudent = studentRepository.findById(testStudent.getId()).orElse(null);

        assertNotNull(updatedStudent);
        assertEquals("Пётр", updatedStudent.getName());
        assertEquals(23, updatedStudent.getAge());
    }

    @Test
    void deleteShouldDeleteStudent() {
        assertTrue(studentRepository.existsById(testStudent.getId()));
        studentRepository.deleteById(1);
        assertFalse(studentRepository.existsById(1));
    }
}

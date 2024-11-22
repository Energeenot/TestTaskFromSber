package Energeenot.TestTaskFromSber.service;

import Energeenot.TestTaskFromSber.model.Student;
import Energeenot.TestTaskFromSber.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void getAllStudentsShouldReturnAllStudents() {
        List<Student> students = Arrays.asList(
                Student.builder()
                        .id(1)
                        .surname("Иванов")
                        .name("Иван")
                        .patronymic("Иванович")
                        .age(20)
                        .averageMark(4.5)
                        .build(),
                Student.builder()
                        .id(2)
                        .surname("Петров")
                        .name("Пётр")
                        .patronymic("Петрович")
                        .age(22)
                        .averageMark(4.2)
                        .build()
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("Иванов", result.get(0).getSurname());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentShouldReturnCorrectStudent() {
        Student student = Student.builder()
                        .id(1)
                        .surname("Иванов")
                        .name("Иван")
                        .patronymic("Иванович")
                        .age(20)
                        .averageMark(4.5)
                        .build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(student.getId());
        assertNotNull(result);
        assertEquals("Иванов", result.getSurname());
        verify(studentRepository, times(1)).findById(student.getId());
    }

    @Test
    void getStudentShouldThrowsExceptionIfStudentNotExist() {
        int id = 1;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.getStudentById(id));
        assertEquals("404 NOT_FOUND \"Студент не найден\"", exception.getMessage());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void addStudentShouldSaveStudents() {
        List<Student> students = Arrays.asList(
                Student.builder()
                        .id(1)
                        .surname("Иванов")
                        .name("Иван")
                        .patronymic("Иванович")
                        .age(20)
                        .averageMark(4.5)
                        .build(),
                Student.builder()
                        .id(2)
                        .surname("Петров")
                        .name("Пётр")
                        .patronymic("Петрович")
                        .age(22)
                        .averageMark(4.2)
                        .build()
        );
        when(studentRepository.saveAll(students)).thenReturn(students);
        List<Student> result = studentService.addStudent(students);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).saveAll(students);
    }

    @Test
    void updateStudentShouldReturnUpdatedStudent() {
        Student existingStudent = Student.builder()
                .id(1)
                .surname("Иванов")
                .name("Иван")
                .patronymic("Иванович")
                .age(20)
                .averageMark(4.5)
                .build();
        Student updatedStudent = Student.builder()
                .patronymic("Петрович")
                .age(21)
                .build();

        when(studentRepository.findById(existingStudent.getId())).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student result = studentService.updateStudent(existingStudent.getId(), updatedStudent);

        assertEquals("Петрович", result.getPatronymic());
        assertEquals(21, result.getAge());
        assertEquals(4.5, result.getAverageMark());
        verify(studentRepository, times(1)).findById(existingStudent.getId());
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void updateStudentShouldThrowsExceptionIfStudentNotExist() {
        int id = 1;
        Student updatedStudent = Student.builder()
                .patronymic("Петрович")
                .age(21)
                .build();
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.updateStudent(id, updatedStudent));
        assertEquals("404 NOT_FOUND \"Студент не найден\"", exception.getMessage());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void deleteStudentShouldDeleteStudent() {
        int id = 1;
        studentService.deleteStudent(id);
        verify(studentRepository, times(1)).deleteById(id);
    }
}

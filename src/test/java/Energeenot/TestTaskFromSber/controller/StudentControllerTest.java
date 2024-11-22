package Energeenot.TestTaskFromSber.controller;

import Energeenot.TestTaskFromSber.model.Student;
import Energeenot.TestTaskFromSber.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllStudentsShouldReturnAllStudent() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of());
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentByIdShouldFindStudent() throws Exception {
        int id = 1;
        Student student = Student.builder()
                .id(id)
                .surname("Петров")
                .name("Пётр")
                .patronymic("Петрович")
                .age(20)
                .build();
        when(studentService.getStudentById(id)).thenReturn(student);
        mockMvc.perform(get("/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname").value("Петров"))
                .andExpect(jsonPath("$.name").value("Пётр"));

        verify(studentService, times(1)).getStudentById(id);
    }

    @Test
    void getStudentByIdShouldThrowException() throws Exception {
        int id = 1;
        when(studentService.getStudentById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/students/{id}", id))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).getStudentById(id);
    }

    @Test
    void createStudentShouldReturnCreatedStudent() throws Exception {
        List<Student> savedStudents = Arrays.asList(
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

        when(studentService.addStudent(anyList())).thenReturn(savedStudents);
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"surname\":\"Иванов\",\"name\":\"Иван\",\"age\":20,\"averageMark\":4.5}," +
                        "{\"surname\":\"Петров\",\"name\":\"Пётр\",\"age\":22,\"averageMark\":4.2}]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
        verify(studentService, times(1)).addStudent(anyList());
    }

    @Test
    void updateStudentShouldThrowException() throws Exception {
        when(studentService.addStudent(anyList())).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"surname\":\"Иванов\",\"name\":\"Иван\",\"age\":20,\"averageMark\":4.5}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertEquals("500 INTERNAL_SERVER_ERROR \"Не удаётся создать студентов\"", result.getResolvedException().getMessage()));
        verify(studentService, times(1)).addStudent(anyList());
    }

    @Test
    void deleteStudentShouldReturnNoContent() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/students/{id}", id))
                .andExpect(status().isNoContent());
        verify(studentService, times(1)).deleteStudent(id);
    }



}

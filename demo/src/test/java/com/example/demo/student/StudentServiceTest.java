package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceTest {

    //which service we want to test
    @InjectMocks
    private StudentService studentService;

    //declare dependencies
    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_student() {
        //Given
        StudentDto dto = new StudentDto("John","Doe","john@doe.com",1);

        Student student = new Student("John","Doe","john@doe.com",20);

        //Mock the calls
        Mockito.when(studentMapper.toStudent(dto)).thenReturn(student);
        Mockito.when(repository.save(student)).thenReturn(student);
        Mockito.when(studentMapper.toStudentResponseDto(student)).thenReturn(
                new StudentResponseDto("John","Doe","john@doe.com")
        );

        //When
        StudentResponseDto responseDto = studentService.saveStudent(dto);

        //Then
        assertEquals(dto.firstname(),responseDto.firstname());
        assertEquals(dto.lastname(),responseDto.lastname());
        assertEquals(dto.email(),responseDto.email());

        Mockito.verify(repository, Mockito.times(1)).save(student);
        Mockito.verify(studentMapper, Mockito.times(1)).toStudent(dto);
        Mockito.verify(studentMapper, Mockito.times(1)).toStudentResponseDto(student);
    }

    @Test
    public void should_return_all_students() {
        //Given
        List<Student> students = new ArrayList<>();
        students.add(new Student("John","Doe","john@doe.com",1));


        //Mock the calls
        Mockito.when(repository.findAll()).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class))).thenReturn(
                new StudentResponseDto("John","Doe","john@doe.com")
        );

        //When
        List<StudentResponseDto> responseDtos = studentService.findAllStudents();

        //Then
        assertEquals(students.size(),responseDtos.size());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void should_return_student_by_id() {
        //Given
        Integer studentId = 1;
        Student student = new Student("John","Doe","john@doe.com",1);

        //Mock the calls
        Mockito.when(repository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class))).thenReturn(
                new StudentResponseDto("John","Doe","john@doe.com"));

        //When
        StudentResponseDto responseDto = studentService.findStudentById(studentId);

        //Then
        assertEquals(responseDto.firstname(),student.getFirstname());
        assertEquals(responseDto.lastname(),student.getLastname());
        assertEquals(responseDto.email(),student.getEmail());

        Mockito.verify(repository, Mockito.times(1)).findById(studentId);
    }

    @Test
    public void should_return_student_by_name() {
        //Given
        String studentName = "jo";
        Student student = new Student("John","Doe","john@doe.com",1);
        List<Student> students = new ArrayList<>();
        students.add(student);

        //Mock the calls
        Mockito.when(repository.findAllByFirstnameContaining(studentName)).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class))).thenReturn(
                new StudentResponseDto("John","Doe","john@doe.com"));

        //When
        List<StudentResponseDto> responseDtos =  studentService.findStudentsByName(studentName);

        //Then
        assertEquals(students.size(),responseDtos.size());

        Mockito.verify(repository, Mockito.times(1)).findAllByFirstnameContaining(studentName);
    }

    @Test
    public void should_successfully_delete_student() {
        // Given
        Integer studentId = 1;

        // Mock the delete call with doNothing()
        Mockito.doNothing().when(repository).deleteById(studentId);

        // When
        studentService.delete(studentId);

        // Then
        Mockito.verify(repository, Mockito.times(1)).deleteById(studentId);
    }

    @Test
    public void should_throw_exception_when_student_not_found() {
        // Given
        Integer studentId = 1;

        // Mock the exception on deleteById
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(studentId);

        // When & Then
        assertThrows(EmptyResultDataAccessException.class, () -> studentService.delete(studentId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(studentId);
    }
}
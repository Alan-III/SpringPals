package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto dto = new StudentDto("John","Doe","john@doe.com",1);

        Student student = mapper.toStudent(dto);

        assertEquals(dto.firstname(),student.getFirstname());
        assertEquals(dto.lastname(),student.getLastname());
        assertEquals(dto.email(),student.getEmail());
        assertNotNull(student.getSchool());
        assertEquals(dto.schoolId(),student.getSchool().getId());

    }

    @Test
    public void should_throw_null_pointer_exception_when_studentDto_is_null() {
        var exp = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
        assertEquals("The student dto should not be null",exp.getMessage());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        Student student = new Student("John","Doe","john@doe.com",15);

        StudentResponseDto dto = mapper.toStudentResponseDto(student);

        assertEquals(dto.firstname(),student.getFirstname());
        assertEquals(dto.lastname(),student.getLastname());
        assertEquals(dto.email(),student.getEmail());
    }

}
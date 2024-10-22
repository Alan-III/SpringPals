package com.example.demo.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public StudentResponseDto saveStudent(
            @Valid @RequestBody StudentDto dto
    ) {
        return studentService.saveStudent(dto);
    }


    @GetMapping("/students")
    public List<StudentResponseDto> findAllStudents() {
        return studentService.findAllStudents();
    }
    @GetMapping("/students/{student-id}")
    public StudentResponseDto findStudentById(
            @PathVariable("student-id") Integer id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("/students/search/{student-name}")
    public List<StudentResponseDto> findStudentsByName(
            @PathVariable("student-name") String name) {
        return studentService.findStudentsByName(name);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("student-id") Integer id) {
        studentService.delete(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp){
        var erros = new HashMap<String,String>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }
    //PREVIOUS LESSONS FOR REFERENCE
/*
    @PostMapping("/post-order")
    public String post(
            @RequestBody Order order
    ) {
        return "Request Accepted and order is: " + order.toString();
    }

    @PostMapping("/post-order-record")
    public String postRecord(
            @RequestBody OrderRecord order
    ) {
        return "Request Accepted and order is: " + order.toString();
    }

    @GetMapping("/hello/{user-name}")
    public String pathVar(
            @PathVariable("user-name") String userName
    ) {
        return "my value = "+ userName;
    }

    @GetMapping("/hello")
    public String paramVar(
            @RequestParam("name") String userName,
            @RequestParam("lastname") String userLastName
    ) {
        return "my value = "+ userName + " " + userLastName;
    }*/
}

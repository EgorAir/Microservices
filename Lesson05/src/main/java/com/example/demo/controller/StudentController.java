package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    public StudentController() {

    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) throws NotFoundException {
        return studentService.getById(id);
    }

    @PostMapping
    public Student addStudent(@RequestParam(value = "firstName") String firstName,
                              @RequestParam(value = "secondName") String secondName)
    {
        Student student = new Student(firstName, secondName);
        studentService.addStudent(student);
        return student;
    }

    @PutMapping("/{id}")
    public Student put(@PathVariable Long id,
                       @RequestBody Student student) throws NotFoundException
    {
        studentService.update(id, student);
        return studentService.getById(id);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        studentService.deleteById(id);
    }
}

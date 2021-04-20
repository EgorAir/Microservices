package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public Student getById(Long id) throws NotFoundException {
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public Student update(Long id, Student student) throws NotFoundException {
        Student studentUpdate = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        studentUpdate.setFirstName(student.getFirstName());
        studentUpdate.setSecondName(student.getSecondName());
        addStudent(studentUpdate);
        return getById(id);
    }
}

package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.service.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentServiceTest {
  @TestConfiguration
  static class StudentServiceTestConfig {
    @Bean
    public StudentService studentServiceTest() {
      return new StudentService();
    }
  }

  @Autowired
  public StudentService studentService;

  public Student addNew() {
    Student student = new Student();
    student.setFirstName("default");
    student.setSecondName("default");
    return studentService.addStudent(student);
  }

  @Test
  public void add() {
    Student newStudent = addNew();

    Assert.assertNotEquals(newStudent.getId(), Long.valueOf(0));
    Assert.assertEquals(newStudent.getFirstName(), "default");
    Assert.assertEquals(newStudent.getSecondName(), "default");
  }

  @Test
  public void getAll() {
    add();
    Assert.assertTrue(studentService.getAllStudents().size() > 0);
  }

  @Test
  public void getById() throws NotFoundException {
    Student newStudent = addNew();
    Student student = studentService.getById(newStudent.getId());
    Assert.assertEquals(student.getId(), newStudent.getId());
    Assert.assertEquals(student.getFirstName(), "default");
    Assert.assertEquals(student.getSecondName(), "default");
  }

  @Test
  public void update() throws NotFoundException {
    Student newStudent = addNew();
    Student student = new Student();
    student.setFirstName("default");
    student.setSecondName("default");
    Student updstudent = studentService.update(newStudent.getId(), student);
    Assert.assertEquals(updstudent.getId(), newStudent.getId());
    Assert.assertEquals(updstudent.getFirstName(), "default");
    Assert.assertEquals(updstudent.getSecondName(), "default");
  }

  @Test(expected = NotFoundException.class)
  public void deleteById() {
    Student newStudent = addNew();
    studentService.deleteById(newStudent.getId());
    studentService.getById(newStudent.getId());
  }

}

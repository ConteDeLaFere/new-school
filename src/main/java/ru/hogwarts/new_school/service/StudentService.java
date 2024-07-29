package ru.hogwarts.new_school.service;

import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student addStudent(Student student);
    Student findStudent(Long id);
    Student updateStudent(Student student);
    void deleteStudent(Long id);

    Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge);

    Faculty getFacultyByStudentId(Long studentId);

    Long getAllStudentsCount();
    Double getAllStudentsAverageAge();
    List<Student> getLastFiveStudents();
}

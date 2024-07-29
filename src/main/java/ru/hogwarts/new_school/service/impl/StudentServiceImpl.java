package ru.hogwarts.new_school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.repository.StudentRepository;
import ru.hogwarts.new_school.service.StudentService;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFacultyByStudentId(Long studentId) {
        Student student = findStudent(studentId);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    @Override
    public Long getAllStudentsCount() {
        return studentRepository.getAllStudentsCount();
    }

    @Override
    public Double getAllStudentsAverageAge() {
        return studentRepository.getAllStudentsAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}

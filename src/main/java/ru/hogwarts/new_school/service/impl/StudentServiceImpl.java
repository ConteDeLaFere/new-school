package ru.hogwarts.new_school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method to add a student");
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Was invoked method to find a student with id {}", id);
        logger.warn("The student with id {} may not be found, if he doesn't exist", id);

        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.error("Student with id {} was not found", id);
        }
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Was invoked method to update a student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method to delete a student with id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method to find students in age between {} and {}", minAge, maxAge);
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFacultyByStudentId(Long studentId) {
        logger.info("Was invoked method to find faculty by student id {}", studentId);
        Student student = findStudent(studentId);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    @Override
    public Long getAllStudentsCount() {
        logger.info("Was invoked method to count the amount of students");
        return studentRepository.getAllStudentsCount();
    }

    @Override
    public Double getAllStudentsAverageAge() {
        logger.info("Was invoked method to calculate the average age of students");
        return studentRepository.getAllStudentsAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method to find last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<String> findStudentNamesBeginningWithLetterA() {
        List<Student> students = getAllStudents();
        return students.stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @Override
    public Double calculateAverageAge() {
        List<Student> students = getAllStudents();
        double ageSum = students.stream()
                .mapToDouble(Student::getAge)
                .sum();

        return ageSum / students.size();
    }


}

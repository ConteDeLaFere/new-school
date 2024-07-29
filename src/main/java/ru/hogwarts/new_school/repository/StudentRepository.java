package ru.hogwarts.new_school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.new_school.model.Student;

import java.util.Collection;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentsByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT(id) as count FROM student", nativeQuery = true)
    Long getAllStudentsCount();

    @Query(value = "SELECT AVG(age) as average_age FROM student", nativeQuery = true)
    Double getAllStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}

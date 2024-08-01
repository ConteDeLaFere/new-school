package ru.hogwarts.new_school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.new_school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findFacultyByNameOrColor(String name, String color);
    List<Faculty> findAll();
}

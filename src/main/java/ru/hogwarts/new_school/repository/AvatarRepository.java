package ru.hogwarts.new_school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.new_school.model.Avatar;


import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByStudentId(Long id);

}

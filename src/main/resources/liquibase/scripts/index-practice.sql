-- liquibase formatted sql

-- changeset nick:1
CREATE INDEX student_name_index ON student (name);

-- changeset nick:2
CREATE INDEX faculty_name_and_color_index ON faculty (name, color);


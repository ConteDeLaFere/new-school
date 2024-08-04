ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age > 16),
ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
ADD CONSTRAINT color_unique UNIQUE (color);

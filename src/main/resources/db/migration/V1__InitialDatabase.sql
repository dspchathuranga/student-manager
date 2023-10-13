CREATE TABLE student (
  student_id serial PRIMARY KEY,
  student_first_name VARCHAR(50),
  student_last_name VARCHAR(50),
  student_age INT
);

CREATE TABLE subject (
  subject_id serial PRIMARY KEY,
  subject_name VARCHAR(100)
);

CREATE TABLE student_subject (
    student_id INT REFERENCES student(student_id),
    subject_id INT REFERENCES subject(subject_id),
    PRIMARY KEY (student_id, subject_id)
);

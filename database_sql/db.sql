-- 创建 course 表
CREATE TABLE course (
                        course_id   VARCHAR(4) NOT NULL PRIMARY KEY,
                        course_name VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL
) CHARSET = utf8mb4;

-- 创建 person 表
CREATE TABLE person (
                        id     VARCHAR(20) NOT NULL PRIMARY KEY,
                        name   VARCHAR(50) NOT NULL,
                        gender VARCHAR(2)  NOT NULL,
                        type   VARCHAR(10) NOT NULL
) CHARSET = utf8mb4;

-- 创建 student 表
CREATE TABLE student (
                         student_id VARCHAR(20) NOT NULL PRIMARY KEY,
                         major      VARCHAR(50) CHARSET utf8 NULL,
                         person_id  VARCHAR(20) NOT NULL,  -- 关联 person 表
                         CONSTRAINT fk_student_person FOREIGN KEY (person_id) REFERENCES person(id)
) CHARSET = utf8mb4;

-- 创建 teacher 表
CREATE TABLE teacher (
                         teacher_id VARCHAR(20) NOT NULL PRIMARY KEY,
                         title      VARCHAR(50) CHARSET utf8 NULL,
                         person_id  VARCHAR(20) NOT NULL,  -- 关联 person 表
                         CONSTRAINT fk_teacher_person FOREIGN KEY (person_id) REFERENCES person(id)
) CHARSET = utf8mb4;

-- 创建 class 表
CREATE TABLE class (
                       class_id       VARCHAR(5)  NOT NULL PRIMARY KEY,
                       class_name     VARCHAR(50) CHARSET utf8 NULL,
                       semester       VARCHAR(10) CHARSET utf8 NULL,
                       teacher_id     VARCHAR(20) NULL,  -- 关联 teacher 表
                       course_id      VARCHAR(4)  NULL,  -- 关联 course 表
                       total_students INT          NULL,
                       CONSTRAINT fk_class_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
                       CONSTRAINT fk_class_course  FOREIGN KEY (course_id) REFERENCES course(course_id)
) CHARSET = utf8mb4;

-- 创建 grade 表
CREATE TABLE grade (
                       student_id    VARCHAR(20) NOT NULL,
                       class_id      VARCHAR(5)  NOT NULL,
                       regular_score INT         NULL,
                       midterm_score INT         NULL,
                       lab_score     INT         NULL,
                       final_score   INT         NULL,
                       total_score   INT         NULL,
                       grade_date    DATE        NULL,
                       PRIMARY KEY (student_id, class_id),
                       CONSTRAINT fk_grade_student FOREIGN KEY (student_id) REFERENCES student(student_id),
                       CONSTRAINT fk_grade_class   FOREIGN KEY (class_id) REFERENCES class(class_id)
) CHARSET = utf8mb4;

-- 创建索引
CREATE INDEX idx_class_id ON grade(class_id);

-- 创建 user 表
CREATE TABLE `user` (
                        `user_id`       VARCHAR(20) NOT NULL PRIMARY KEY,   -- 用户唯一标识，建议使用邮箱或用户名
                        `username`      VARCHAR(50) NOT NULL,               -- 登录名
                        `password_hash` VARCHAR(255) NOT NULL,              -- 密码哈希
                        `role`          ENUM('student', 'teacher', 'admin') NOT NULL,  -- 用户角色
                        `person_id`     VARCHAR(20) NOT NULL,               -- 关联 person 表
                        `created_at`    DATETIME DEFAULT CURRENT_TIMESTAMP, -- 账户创建时间
                        `last_login_at` DATETIME NULL,                      -- 最后登录时间
                        CONSTRAINT fk_user_person FOREIGN KEY (person_id) REFERENCES person(id)
) CHARSET = utf8mb4;

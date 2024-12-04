create table class
(
    class_id       varchar(5)               not null
        primary key,
    class_name     varchar(50) charset utf8 null,
    semester       varchar(10) charset utf8 null,
    teacher_id     varchar(20)              null,
    course_id      varchar(4)               null,
    total_students int                      null,
    constraint class_ibfk_2
        foreign key (course_id) references course (course_id),
    constraint class_ibfk_3
        foreign key (teacher_id) references teacher (teacher_id)
)
    charset = latin1;


create table grade
(
    student_id    varchar(20) not null,
    class_id      varchar(5)  not null,
    regular_score int         null,
    midterm_score int         null,
    lab_score     int         null,
    final_score   int         null,
    total_score   int         null,
    grade_date    date        null,
    primary key (student_id, class_id),
    constraint grade_ibfk_2
        foreign key (class_id) references class (class_id),
    constraint grade_ibfk_3
        foreign key (student_id) references student (student_id)
)
    charset = latin1;

create index class_id
    on grade (class_id);


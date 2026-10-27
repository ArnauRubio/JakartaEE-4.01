package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

    @Entity
    public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idCourse", nullable = false)
        private Integer idCourse;
        @NotNull
        private String title;
        @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "teacher_id", referencedColumnName = "idTeacher")
        private Teacher teacher;

        public Course() {
        }

        public Course(String title, Teacher teacher) {
            this.title = title;
            this.teacher = teacher;
        }

        public Integer getIdCourse() {
            return idCourse;
        }

        public Teacher getTeacher() {
            return teacher;
        }
    }

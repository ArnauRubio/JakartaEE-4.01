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
        @ManyToOne(optional = false)
        @JoinColumn(name = "teacher_id", referencedColumnName = "idTeacher")
        private Teacher teacher;
        @OneToOne(mappedBy = "course")
        private CourseMaterial material;

        public Course() {
        }

        public Course(String title, Teacher teacher) {
            this.title = title;
            this.teacher = teacher;
        }

        public Integer getIdCourse() {
            return idCourse;
        }

        public void setIdCourse(Integer idCourse) {
            this.idCourse = idCourse;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }

        public CourseMaterial getMaterial() {
            return material;
        }

        public void setMaterial(CourseMaterial material) {
            this.material = material;
            if (material != null && material.getCourse() != this) {
                material.setCourse(this);
            }
        }

        @Override
        public String toString() {
            return "Course{" +
                    "idCourse=" + idCourse +
                    ", title='" + title + '\'' +
                    ", teacher=" + teacher +
                    '}';
        }
    }

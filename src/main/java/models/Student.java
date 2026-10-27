package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTStudent", nullable = false)
    private Integer idStudent;
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private boolean wantsNewsletter;
}
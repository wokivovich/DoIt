package com.wokivovich.tm.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private boolean isCompleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;

    @JoinColumn(name = "usr")
    @ManyToOne
    private User user;
}

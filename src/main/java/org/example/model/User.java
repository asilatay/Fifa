package org.example.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="user", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
    @Column(name = "ID")
    @Id
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;
}

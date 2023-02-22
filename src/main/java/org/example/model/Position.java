package org.example.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="position")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @SequenceGenerator(name = "position_id_seq", sequenceName = "position_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "position_id_seq")
    @Column(name = "ID")
    @jakarta.persistence.Id
    private long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;
}

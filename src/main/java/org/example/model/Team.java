package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="team")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @SequenceGenerator(name = "team_id_seq", sequenceName = "team_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "team_id_seq")
    @Column(name = "ID")
    @jakarta.persistence.Id
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICK_NAME")
    private String nickname;

    @Column(name = "ESTABLISHMENT_YEAR")
    private Integer establishmentYear;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;
}

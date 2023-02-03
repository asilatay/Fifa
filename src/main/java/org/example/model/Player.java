package org.example.model;



import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="player")
public class Player {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "player_id_seq")
    @Column(name = "ID")
    @jakarta.persistence.Id
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "IS_ACTIVE")
    private Boolean isactive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;

}

package org.example.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="player")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @SequenceGenerator(name = "player_id_seq", sequenceName = "player_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "player_id_seq")
    @Column(name = "ID")
    @jakarta.persistence.Id
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NICK_NAME")
    private String nickname;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;

    @Column(name = "RATING")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;


    @OneToMany
    @JoinTable
            (
                    name="player_position",
                    joinColumns={ @JoinColumn(name="PLAYER_ID", referencedColumnName="ID") },
                    inverseJoinColumns={ @JoinColumn(name="POSITION_ID", referencedColumnName="ID", unique=true) }
            )
    private List<Position> positions = new ArrayList<>();

}

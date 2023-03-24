package org.example.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="mix_operation", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MixOperation {

    @SequenceGenerator(name = "mix_operation_id_seq", sequenceName = "mix_operation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mix_operation_id_seq")
    @Column(name = "ID")
    @Id
    private long id;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;

    @Column(name = "MIN_PLAYER_RATING")
    private int minPlayerRating;

    @Column(name = "MAX_PLAYER_RATING")
    private int maxPlayerRating;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany
    private List<MixOperationDetail> details = new ArrayList<>();

}

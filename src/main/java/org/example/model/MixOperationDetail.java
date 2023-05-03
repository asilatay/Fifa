package org.example.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="mix_operation_detail", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MixOperationDetail {

    @SequenceGenerator(name = "mix_operation_detail_id_seq", sequenceName = "mix_operation_detail_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mix_operation_detail_id_seq")
    @Column(name = "ID")
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mix_operation_id")
    private MixOperation mixOperation;

}

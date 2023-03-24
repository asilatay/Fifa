package org.example.repository;

import org.example.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository <Player,Long> {

    @Query(value = "select p.* from player p" +
            "  where p.id  not in (" +
            "       select modet.player_id from mix_operation_detail modet" +
            "       join mix_operation mo on mo.id = modet.mix_operation_id" +
            "       where mo.is_active = true" +
            "  ) and p.is_active = true and p.rating >= :minPlayerRating and p.rating <= :maxPlayerRating", nativeQuery = true)
    List<Player> getAvailablePlayers(@Param("minPlayerRating") int minPlayerRating, @Param("maxPlayerRating") int maxPlayerRating);
}

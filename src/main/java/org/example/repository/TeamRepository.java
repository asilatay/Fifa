package org.example.repository;

import org.example.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query(value = "select t.* from team t" +
            "   where t.id not in (" +
            "       select mo.team_id from mix_operation mo" +
            "       where mo.is_active = true" +
            "   ) and t.is_active = true", nativeQuery = true)
    List<Team> getAvailableTeams();
}

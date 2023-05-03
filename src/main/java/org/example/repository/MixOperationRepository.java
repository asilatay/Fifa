package org.example.repository;
import org.example.model.MixOperation;
import org.example.model.Player;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MixOperationRepository extends JpaRepository <MixOperation,Long> {

}

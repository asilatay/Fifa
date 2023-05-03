package org.example.repository;
import org.example.model.MixOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MixOperationRepository extends JpaRepository <MixOperation,Long> {

}

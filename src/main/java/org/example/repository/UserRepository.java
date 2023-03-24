package org.example.repository;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {

    @Query(value = "SELECT u.* FROM public.user u" +
            "   WHERE u.id not in (" +
            "       SELECT mo.user_id FROM mix_operation mo" +
            "       WHERE mo.is_active = true" +
            "       )" +
            "   AND u.is_active = true", nativeQuery = true)
    List<User> getAvailableUserList();
}

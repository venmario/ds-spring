package com.duniasteak.service.user.repo;

import com.duniasteak.service.user.model.Fcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmRepository extends JpaRepository<Fcm, String> {
}

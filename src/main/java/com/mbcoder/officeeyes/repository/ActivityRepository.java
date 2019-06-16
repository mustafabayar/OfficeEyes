package com.mbcoder.officeeyes.repository;

import com.mbcoder.officeeyes.model.entity.ActivityTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityTime, Long> {
}

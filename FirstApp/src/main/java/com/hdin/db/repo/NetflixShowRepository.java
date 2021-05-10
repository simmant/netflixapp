package com.hdin.db.repo;

import com.hdin.db.model.NetflixShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NetflixShowRepository extends JpaRepository<NetflixShow,Integer> {
}

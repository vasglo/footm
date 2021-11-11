package com.example.futm.repo;

import com.example.futm.repo.entity.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepo extends CrudRepository<Team, Integer> {
}

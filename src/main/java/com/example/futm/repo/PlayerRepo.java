package com.example.futm.repo;

import com.example.futm.repo.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepo extends CrudRepository<Player, Integer> {
}

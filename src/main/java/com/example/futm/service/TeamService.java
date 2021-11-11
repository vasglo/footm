package com.example.futm.service;

import com.example.futm.dto.PlayerDto;
import com.example.futm.dto.TeamDto;

import java.util.Set;

public interface TeamService {
    TeamDto create(String name, Integer money, Integer commission);

    TeamDto getTeamById(Integer id);

    Set<TeamDto> getAll();

    TeamDto update(Integer Id, String name, Integer money, Integer commission);

    void delete(int teamId);

    Set<PlayerDto> getTeamPlayers(Integer teamId);
}

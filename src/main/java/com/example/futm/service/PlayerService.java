package com.example.futm.service;

import com.example.futm.dto.PlayerDto;

import java.util.Set;

public interface PlayerService {
    PlayerDto create(String firstName, String lastName, Integer experience, Integer age, Integer teamId);

    PlayerDto update(Integer playerId, String firstName, String lastName, Integer experience, Integer age, Integer teamId);

    PlayerDto moveToOtherTeam(int oldTeamId, int newTeamId, int playerId);

    void deletePlayerById(int playerId);

    Set<PlayerDto> getAllPlayers();

    PlayerDto getPlayerById(int playerId);

    PlayerDto deletePlayerFromTeam(int playerId, int teamId);

    PlayerDto addPlayerToTeamById(int teamId, int playerId);

}

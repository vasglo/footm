package com.example.futm.service;

import com.example.futm.dto.PlayerDto;
import com.example.futm.dto.TeamDto;
import com.example.futm.exception.TeamNotFoundException;
import com.example.futm.repo.PlayerRepo;
import com.example.futm.repo.TeamRepo;
import com.example.futm.repo.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepo;
    private final PlayerRepo playerRepo;

    public TeamServiceImpl(TeamRepo teamRepo, PlayerRepo playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @Override
    public TeamDto create(String name, Integer money, Integer commission) {
        name = name.trim();
        if (!Objects.equals(name, "") && commission < 11 && commission >= 0) {
            Team team = new Team();
            team.setName(name);
            team.setMoney(money);
            team.setCommission(commission);
            teamRepo.save(team);
            log.info("Create team {}", name);
            return TeamDto.map(team);
        }
        log.error("You can not set {}% commission. It have to be from 0 to 10%!", commission);
        return null;
    }

    @Override
    public TeamDto getTeamById(Integer id) {
        log.info("Received team {} by id {}", teamRepo.findById(id).orElseThrow(TeamNotFoundException::new).getName(), id);
        return TeamDto.map(teamRepo.findById(id).orElseThrow(TeamNotFoundException::new));
    }

    @Override
    public Set<TeamDto> getAll() {
        Set<TeamDto> teamDtos = new HashSet<>();
        teamRepo.findAll().forEach(team -> teamDtos.add(TeamDto.map(team)));
        log.info("Received set of all teams");
        return teamDtos;
    }

    @Override
    public TeamDto update(Integer id, String name, Integer money, Integer commission) {
        Team team = teamRepo.findById(id).orElseThrow(TeamNotFoundException::new);
        name = name.trim();
        if (!Objects.equals(name, "") && commission < 11 && commission >= 0) {
            team.setName(name);
            team.setMoney(money);
            team.setCommission(commission);
            teamRepo.save(team);
            log.info("Update team {}", team.getName());
            return TeamDto.map(team);
        } else if (name.equals("")) {
            log.error("You can not set empty name");
        } else if (commission > 11 || commission < 0) {
            log.error("You can not set {}% commission. It have to be from 0 to 10%!", commission);
        }
        return TeamDto.map(team);
    }

    @Override
    public void delete(int teamId) {
        log.info("Delete team {}", teamRepo.findById(teamId).orElseThrow(TeamNotFoundException::new).getName());
        teamRepo.deleteById(teamRepo.findById(teamId).orElseThrow(TeamNotFoundException::new).getId());
    }

    @Override
    public Set<PlayerDto> getTeamPlayers(Integer teamId) {
        Set<PlayerDto> playerDtoSet = new HashSet<>();
        playerRepo.findAll().forEach(player -> {
            if (player.getTeamId() == teamId) {
                playerDtoSet.add(PlayerDto.map(player));
            }
        });
        log.info("Received players from team {}", teamRepo.findById(teamId).orElseThrow(TeamNotFoundException::new).getName());
        return playerDtoSet;
    }
}

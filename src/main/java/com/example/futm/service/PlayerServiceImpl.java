package com.example.futm.service;

import com.example.futm.dto.PlayerDto;
import com.example.futm.exception.PlayerNotFoundException;
import com.example.futm.exception.TeamNotFoundException;
import com.example.futm.repo.PlayerRepo;
import com.example.futm.repo.TeamRepo;
import com.example.futm.repo.entity.Player;
import com.example.futm.repo.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final TeamRepo teamRepo;
    private final PlayerRepo playerRepo;

    public PlayerServiceImpl(TeamRepo teamRepo, PlayerRepo playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @Override
    public PlayerDto create(String firstName, String lastName, Integer experience, Integer age, Integer teamId) {
        firstName = firstName.trim();
        lastName = lastName.trim();
        String finalFirstName = firstName;
        String finalLastName = lastName;

        if (!firstName.equals("") && !lastName.equals("") && age > 6) {
            Player player = new Player();
            for (Player p : playerRepo.findAll()) {
                if (Objects.equals(p.getFirstName(), finalFirstName) && Objects.equals(p.getLastName(), finalLastName)
                        && p.getAge() == age) {
                    log.error("Player already exist");
                    return PlayerDto.map(p);
                }
            }
            player.setFirstName(firstName);
            player.setLastName(lastName);
            player.setExperience(experience);
            player.setAge(age);
            player.setTeamId(teamId);
            playerRepo.save(player);
            log.info("Create player {}", player.getFirstName() + " " + player.getLastName());
            return PlayerDto.map(player);
        }
        log.error("Write correct values");
        return null;
    }

    @Override
    public PlayerDto update(Integer playerId, String firstName, String lastName, Integer experience, Integer age, Integer teamId) {
        Player player = playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new);
        firstName = firstName.trim();
        lastName = lastName.trim();
        if (!firstName.equals("") && !lastName.equals("") && age > 6) {
            player.setFirstName(firstName);
            player.setLastName(lastName);
            player.setExperience(experience);
            player.setAge(age);
            player.setTeamId(teamId);
            playerRepo.save(player);
            log.info("Update player {}", player.getFirstName() + " " + player.getLastName());
            return PlayerDto.map(player);
        }
        log.error("Write correct values");
        return PlayerDto.map(player);
    }

    @Override
    @Transactional
    public PlayerDto moveToOtherTeam(int oldTeamId, int newTeamId, int playerId) {
        Team oldPlayersTeam = teamRepo.findById(oldTeamId).orElseThrow(TeamNotFoundException::new);
        Team newTeam = teamRepo.findById(newTeamId).orElseThrow(TeamNotFoundException::new);
        Player player = playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new);
        int fullPrice = (((player.getExperience() * 100000) / player.getAge()) * oldPlayersTeam.getCommission()) / 100;
        if (oldPlayersTeam.getId().equals(player.getTeamId()) && oldPlayersTeam != newTeam && player.getTeamId() != newTeam.getId() && newTeam.getMoney() >= fullPrice) {
            newTeam.setMoney(newTeam.getMoney() - fullPrice);
            player.setTeamId(newTeam.getId());
            oldPlayersTeam.setMoney(oldPlayersTeam.getMoney() + fullPrice);
            playerRepo.save(player);
            teamRepo.save(oldPlayersTeam);
            teamRepo.save(newTeam);
            log.info("Team {} bought player {} from {} for {} hryvnias", newTeam.getName(), player.getFirstName() + " "
                    + player.getLastName(), oldPlayersTeam.getName(), fullPrice);
            return PlayerDto.map(player);
        } else if (newTeam.getMoney() < fullPrice && !(player.getTeamId() == newTeam.getId())) {
            log.error("Team {} dont have enough money to buy {}", newTeam.getName(), player.getFirstName() + " " + player.getLastName());
        } else if (player.getTeamId() == newTeam.getId()) {
            log.error("Player {} already in team {}", player.getFirstName() + " " + player.getLastName(), newTeam.getName());
        }
        return PlayerDto.map(player);
    }

    @Override
    public void deletePlayerById(int playerId) {
        log.info("Deleting player {}", playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new).getFirstName()
                + " " + playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new).getLastName());
        playerRepo.deleteById(playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new).getId());
    }

    @Override
    public Set<PlayerDto> getAllPlayers() {
        Set<PlayerDto> playerDtos = new HashSet<>();
        playerRepo.findAll().forEach(player -> playerDtos.add(PlayerDto.map(player)));
        log.info("Received set of all players");
        return playerDtos;
    }

    @Override
    public PlayerDto getPlayerById(int playerId) {
        log.info("Receive player by id");
        return PlayerDto.map(playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new));
    }


    @Override
    public PlayerDto deletePlayerFromTeam(int playerId, int teamId) {
        Player player = playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new);
        Team team = teamRepo.findById(teamId).orElseThrow(TeamNotFoundException::new);
        if (player.getTeamId() == team.getId()) {
            player.setTeamId(0);
            playerRepo.save(player);
            log.info("Player {} removed from team {}", player.getFirstName() + " " + player.getLastName(), team.getName());
            return PlayerDto.map(player);
        }
        log.error("Player {} isn't on team {}", player.getFirstName() + " " + player.getLastName(), team.getName());
        return PlayerDto.map(player);
    }

    @Override
    public PlayerDto addPlayerToTeamById(int teamId, int playerId) {
        Player player = playerRepo.findById(playerId).orElseThrow(PlayerNotFoundException::new);
        Team team = teamRepo.findById(teamId).orElseThrow(TeamNotFoundException::new);
        player.setTeamId(team.getId());
        playerRepo.save(player);
        log.info("Added player {} to team {}", player.getFirstName() + " " + player.getLastName(), team.getName());
        return PlayerDto.map(player);
    }
}

package com.example.futm.controller;


import com.example.futm.dto.PlayerDto;
import com.example.futm.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public PlayerDto createNewPlayer(@RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam Integer experience,
                                     @RequestParam Integer age,
                                     @RequestParam Integer teamId) {
        return playerService.create(firstName, lastName, experience, age, teamId);
    }

    @PutMapping("/update")
    public PlayerDto updatePlayer(@RequestParam Integer playerId,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam Integer experience,
                                  @RequestParam Integer age,
                                  @RequestParam Integer teamId) {
        return playerService.update(playerId, firstName, lastName, experience, age, teamId);
    }

    @PutMapping("/transfer")
    public PlayerDto movePlayerToOtherTeam(@RequestParam int oldTeamId,
                                           @RequestParam int newTeamId,
                                           @RequestParam int playerId) {
        return playerService.moveToOtherTeam(oldTeamId, newTeamId, playerId);
    }

    @DeleteMapping("/delete")
    public void deletePlayerById(@RequestParam int playerId) {
        playerService.deletePlayerById(playerId);
    }


    @DeleteMapping("/deletePlayerFromTeam")
    PlayerDto deletePlayerFromTeam(@RequestParam int playerId,
                                   @RequestParam int teamId) {
        return playerService.deletePlayerFromTeam(playerId, teamId);
    }

    @PutMapping("/addPlayerToTeamById")
    PlayerDto addPlayerToTeamById(@RequestParam Integer teamId,
                                  @RequestParam Integer playerId) {
        return playerService.addPlayerToTeamById(teamId, playerId);
    }

    @GetMapping("/all")
    public Set<PlayerDto> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("{playerId}")
    public PlayerDto getPlayerById(@PathVariable Integer playerId) {
        return playerService.getPlayerById(playerId);
    }
}

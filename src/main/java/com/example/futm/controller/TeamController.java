package com.example.futm.controller;

import com.example.futm.dto.PlayerDto;
import com.example.futm.dto.TeamDto;
import com.example.futm.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public TeamDto create(@RequestParam String name,
                          @RequestParam Integer money,
                          @RequestParam Integer commission) {
        return teamService.create(name, money, commission);
    }

    @GetMapping("/all")
    public Set<TeamDto> getAll() {
        return teamService.getAll();
    }

    @GetMapping("/{teamId}")
    public TeamDto getTeamById(@PathVariable Integer teamId) {
        return teamService.getTeamById(teamId);
    }

    @PutMapping
    public TeamDto update(@RequestParam Integer teamId,
                          @RequestParam String name,
                          @RequestParam Integer money,
                          @RequestParam Integer commission) {
        return teamService.update(teamId, name, money, commission);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam int teamId) {
        teamService.delete(teamId);
    }

    @GetMapping({"/{teamId}/players"})
    public Set<PlayerDto> getTeamPlayers(@PathVariable Integer teamId) {
        return teamService.getTeamPlayers(teamId);
    }


}
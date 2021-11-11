package com.example.futm.dto;

import com.example.futm.repo.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class TeamDto {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private int money;
    @NotBlank
    @Positive
    private int commission;

    public static TeamDto map(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .money(team.getMoney())
                .commission(team.getCommission())
                .build();
    }

    public static Team map(TeamDto teamDto) {
        return Team.builder()
                .id(teamDto.getId())
                .name(teamDto.getName())
                .money(teamDto.getMoney())
                .commission(teamDto.getCommission())
                .build();
    }

}

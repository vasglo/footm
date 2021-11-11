package com.example.futm.dto;

import com.example.futm.repo.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class PlayerDto {
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Positive
    private Integer experience;
    @NotBlank
    @Positive
    private Integer age;
    private int teamId;


    public static PlayerDto map(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .experience(player.getExperience())
                .age(player.getAge())
                .teamId(player.getTeamId())
                .build();
    }

    public static Player map(PlayerDto player) {
        return Player.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .experience(player.getExperience())
                .age(player.getAge())
                .teamId(player.getTeamId())
                .build();
    }

}



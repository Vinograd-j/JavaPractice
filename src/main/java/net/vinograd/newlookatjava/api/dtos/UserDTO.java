package net.vinograd.newlookatjava.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private Integer id;

    @NotNull
    private String login;

    @NotNull
    private List<AccountDTO> accounts;

}

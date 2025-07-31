package net.vinograd.newlookatjava.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @NotNull
    private int id;

    @NotNull
    private double moneyAmount;

}
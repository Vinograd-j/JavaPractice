package net.vinograd.newlookatjava.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WithdrawDTO {

    @NotNull
    private Integer accountId;

    @NotNull
    private Double amount;

}

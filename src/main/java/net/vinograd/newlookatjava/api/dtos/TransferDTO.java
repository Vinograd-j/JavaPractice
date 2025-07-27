package net.vinograd.newlookatjava.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferDTO {

    @NotNull
    private Integer senderAccountId;

    @NotNull
    private Integer receiverAccountId;

    @NotNull
    private Double amount;

}

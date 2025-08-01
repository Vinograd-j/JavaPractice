package net.vinograd.newlookatjava.console.handlers;

import net.vinograd.newlookatjava.api.exception.errors.NotEnoughMoney;
import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferCommand implements CommandExecutor {

    private final Scanner scanner;

    private final AccountService accountService;

    public AccountTransferCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Input sender account id: ");
        int senderId = scanner.nextInt();

        Account senderAccount = accountService.findAccountById(senderId).orElseThrow(() -> new IllegalArgumentException("This account does not exists"));

        System.out.println("Input receiver account id");
        int receiverId = scanner.nextInt();

        Account receiverAccount = accountService.findAccountById(receiverId).orElseThrow(() -> new IllegalArgumentException("This account does not exists"));

        System.out.println("Input amount of money");
        double amount = scanner.nextDouble();

        try{
            accountService.transferMoney(senderAccount, receiverAccount, amount);
        }catch (NotEnoughMoney e){
            System.out.println("Sender does not have enough money");
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ACCOUNT_TRANSFER;
    }
}

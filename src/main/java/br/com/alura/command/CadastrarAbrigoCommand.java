package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;

import java.io.IOException;
import java.util.Scanner;

public class CadastrarAbrigoCommand implements Command{

    @Override
    public void execute() {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        Scanner scanner = new Scanner(System.in);
        AbrigoService abrigoService = new AbrigoService(client, scanner);
        try {
            abrigoService.cadastrarAbrigo();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

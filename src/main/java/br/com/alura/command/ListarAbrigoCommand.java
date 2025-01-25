package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;

import java.io.IOException;

public class ListarAbrigoCommand implements Command{
    @Override
    public void execute() {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        AbrigoService abrigoService = new AbrigoService(client);
        try {
            abrigoService.listarAbrigos();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.PetService;

import java.io.IOException;

public class ImportarPetsCommand implements Command{
    @Override
    public void execute() {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        PetService petService = new PetService(client);
        try {
            petService.importarPetsDoAbrigo();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package br.com.alura.service;


import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class PetSerice {

    private ClientHttpConfiguration client;
    private Scanner scanner;

    public PetSerice(ClientHttpConfiguration client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public void listarPetsDoAbrigo() throws IOException, InterruptedException {

        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        String uri = "http://localhost:8080/abrigos/" +idOuNome +"/pets";
        HttpResponse<String> response = client.dispararRequisicaoGet(uri);

        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
            return;
        }
        String responseBody = response.body();
        System.out.println("Pets cadastrados:");
        Pet[] pets = new ObjectMapper().readValue(responseBody, Pet[].class);

        Arrays.stream(pets).forEach(pet ->
                System.out.println(pet.getId() +" - " + pet.getTipo() +" - " + pet.getNome()
                        +" - " + pet.getRaca() +" - " + pet.getIdade() + " ano(s)"));

    }

    public void importarPetsDoAbrigo() throws IOException, InterruptedException {

        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = scanner.nextLine();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
            return;
        }
        String line;
        while ((line = reader.readLine()) != null) {

            Pet pet = getPet(line);

            String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";
            HttpResponse<String> response = client.dispararRequisicaoPost(uri, pet);

            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + pet.getNome());
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + pet.getNome());
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }

    private Pet getPet(String line) {
        String[] campos = line.split(",");
        Pet pet = new Pet();

        if (campos[0].equalsIgnoreCase(TipoPet.GATO.toString())){
            pet.setTipo(TipoPet.GATO);
        }else if (campos[0].equalsIgnoreCase(TipoPet.CACHORRO.toString())){
            pet.setTipo(TipoPet.CACHORRO);
        }
        pet.setNome(campos[1]);
        pet.setRaca(campos[2]);
        pet.setIdade(Integer.parseInt(campos[3]));
        pet.setCor(campos[4]);
        pet.setPeso(Float.parseFloat(campos[5]));

        return  pet;
    }

}

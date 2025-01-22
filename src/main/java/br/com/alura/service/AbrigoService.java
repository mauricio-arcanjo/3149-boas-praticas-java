package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class AbrigoService {

    private ClientHttpConfiguration client;
    private Scanner scanner;

    public AbrigoService(ClientHttpConfiguration client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public void listarAbrigos() throws IOException, InterruptedException {

        String uri = "http://localhost:8080/abrigos";
        HttpResponse<String> response = client.dispararRequisicaoGet(uri);
        String responseBody = response.body();
        Abrigo[] abrigos = new ObjectMapper().readValue(responseBody, Abrigo[].class);

        if (Arrays.toString(abrigos).equals("[]")){
            System.out.println("Não há abrigos cadastrados!");
        } else {
            System.out.println("Abrigos cadastrados:");
            Arrays.stream(abrigos).forEach(abrigo ->
                    System.out.println(abrigo.getId() + " - " + abrigo.getNome()));
        }
    }

    public void cadastrarAbrigo() throws IOException, InterruptedException {

        Abrigo abrigo = new Abrigo();
        System.out.println("Digite o nome do abrigo:");
        abrigo.setNome(scanner.nextLine());
        System.out.println("Digite o telefone do abrigo:");
        abrigo.setTelefone(scanner.nextLine());
        System.out.println("Digite o email do abrigo:");
        abrigo.setEmail(scanner.nextLine());

        String uri = "http://localhost:8080/abrigos";
        HttpResponse<String> response = client.dispararRequisicaoPost(uri, abrigo);

        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
        }
            System.out.println(responseBody);
    }

}

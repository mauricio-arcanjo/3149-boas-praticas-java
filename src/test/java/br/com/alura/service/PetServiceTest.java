package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
//    private Scanner scanner= new Scanner(System.in);

    private PetService petService = new PetService(client);
    private HttpResponse<String> response = mock(HttpResponse.class);
    private BufferedReader bufferedReader = mock(BufferedReader.class);

//    @Test
//    public void deveVerificarSePetFoiImportado () throws IOException {
//        String expected = "Pet cadastrado com sucesso: Rex";
//
//        //Conjunto de métodos para recuperar bytes impressos pelo system out print para que seja possível fazer a comparacao no teste
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(baos);
//        System.setOut(printStream);
//
//
//        when(bufferedReader.readLine())
//                .thenReturn("cachorro,Rex,Poodle,5,Marrom,10.5")
//                .thenReturn(null);;
//
//    }
@Test
public void deveVerificarSeDispararRequisicaoPostSeraChamado() throws IOException, InterruptedException {
    /*
        ATENÇÃO: Para esse teste funcionar, foi necessário usar a instancia scanner2 no método
        importarPetsDoAbrigo porque o scanner só pode ser instanciado após a atribuição do método
        System.setIn(bais)
     */
    // Simulando as entradas do usuário para ambos os prompts
    String userInput = String.format("Abrigo1%spets.csv", System.lineSeparator());
//    String userInput = "Abrigo1" + System.lineSeparator() + "pets.csv";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);

    // Mockando o comportamento do client e da resposta
    HttpResponse<String> response = mock(HttpResponse.class);
    when(response.statusCode()).thenReturn(200);
    when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);

    // Executando o método que será testado
    petService.importarPetsDoAbrigo();

    // Verificando se o método disparar Requisicao Post foi chamado pelo menos uma vez
    verify(client, atLeast(1)).dispararRequisicaoPost(anyString(), any());
}


}

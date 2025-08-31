package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    /*
     *  Função principal de execução
     * 
     *  
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String diretorioString="";
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        ArrayList<NFA> listaNFA = new ArrayList<NFA>();

        /*
         * Pensei de fazer assim pois dessa forma dá para tratar um possivel erro no diretório
         * fora da classe NFA.
         * Mas também é possivel criar a função dentro da classe NFA passando como parametro o
         * diretório do .json ao invés do JSONObject.
         * 
         * Fica a critério qual usaremos.
         */
        System.out.print("Informe o diretório do JSON: ");
        diretorioString = sc.nextLine();

        try {

            Object objetoJSon = parser.parse(new FileReader(diretorioString));
            JSONArray listaNFAJsonArray;

            if(objetoJSon instanceof JSONObject) {

                listaNFAJsonArray = new JSONArray();
                listaNFAJsonArray.add(objetoJSon);

            } else
                listaNFAJsonArray = (JSONArray) objetoJSon;

            // Itera JSONArray e converte em NFA segundo método definido na própria classe
            for (Object obj : listaNFAJsonArray) {

                jsonObject = (JSONObject) obj;
                NFA nfaExemplo = new NFA();
                nfaExemplo.NFAfromJSON(jsonObject);
                listaNFA.add(nfaExemplo);

            }

            for(int i=0; i < listaNFA.size(); i++){

                System.out.printf("========= NFA %d =========\n", i+1);
                System.out.print(listaNFA.get(i));

            }

        } catch (FileNotFoundException f) {

            System.out.print("O diretório: \"" + diretorioString + "\" não foi encontrado.\n" +
            "Deseja tentar outro?(S | N): ");
            
            switch (sc.nextLine().toUpperCase()) {
                case "S":
                     //Chamada para recomeçar programa
                    System.out.print("\nRecomeçando programa\n");
                    break;
                    
                case "N":
                    System.out.print("\nFIM!\n");
                    break;
                
                default:
                    System.out.print("\nOpção não existente!\n");
                    break;
            } 

        } catch (Exception e) {

                System.out.print("Não sei qual erro!");

        } finally {

            sc.close();
            
        }

    }

}

/**
 *
 * @author Gabriel Alexandre
 */

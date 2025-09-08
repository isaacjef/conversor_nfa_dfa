package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String diretorioString="C:\\Users\\Pichau\\Desktop\\conversor_nfa_dfa\\";
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        ArrayList<NFA> listaNFA = new ArrayList<NFA>();
        ArrayList<DFA> listaDFA = new ArrayList<DFA>();

        /*
         * Pensei de fazer assim pois dessa forma dá para tratar um possivel erro no diretório
         * fora da classe NFA.
         * Mas também é possivel criar a função dentro da classe NFA passando como parametro o
         * diretório do .json ao invés do JSONObject.
         * 
         * Fica a critério qual usaremos.
         */
        //System.out.print("Informe o diretório do JSON: ");
        //diretorioString = sc.nextLine();

        try {
            Object objetoJSON = parser.parse(new FileReader(diretorioString));
            JSONArray listaNFAJsonArray = new JSONArray();
            
            if(objetoJSON instanceof JSONArray) {
                listaNFAJsonArray = (JSONArray) objetoJSON;
            } else if(objetoJSON instanceof JSONObject){
                listaNFAJsonArray.add((JSONObject) objetoJSON);
            }
            
            // Itera JSONArray e converte em NFA segundo método definido na própria classe
            for (Object obj : listaNFAJsonArray) {
                jsonObject = (JSONObject) obj;
                NFA nfaExemplo = new NFA();
                DFA dfaExemplo = new DFA(); 

                List<String> chavestestar = Arrays.asList("alphabet", "states", "transiction", "initial_state", "end_state");;

                //Verifica se contém todas as chaves necessárias para ser considerado NFA
                if (jsonObject.keySet().containsAll(chavestestar)){
                    nfaExemplo.NFAfromJSON(jsonObject);
                    dfaExemplo.DFAfromNFA(nfaExemplo);
                } else {
                    System.out.println("Não é um arquivo de json válido!");
                    return; //Encerra o programa aqui mesmo
                }

                listaNFA.add(nfaExemplo);
                listaDFA.add(dfaExemplo);
            }

            for(int i=0; i < listaNFA.size(); i++){
                System.out.printf("========= NFA %d =========\n", i+1);
                System.out.print(listaNFA.get(i));

                System.out.printf("========= DFA %d =========\n", i+1);
                System.out.print(listaDFA.get(i));
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
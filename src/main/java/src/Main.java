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
    //@SuppressWarnings("unchecked")
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String diretorioString="";
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        ArrayList<NFA> listaNFA = new ArrayList<NFA>();
        ArrayList<DFA> listaDFA = new ArrayList<DFA>();
        //System.out.println(ababa.gerarConjunto(a));

        /*
         * Pensei de fazer assim pois dessa forma dá para tratar um possivel erro no diretório
         * fora da classe NFA.
         * Mas também é possivel criar a função dentro da classe NFA passando como parametro o
         * diretório do .json ao invés do JSONObject.
         * 
         * Fica a critério qual usaremos.
         */
        System.out.print("\nInforme o diretório do JSON: ");
        diretorioString = sc.nextLine();

        try {
            // Object aaaa = parser.parse(new FileReader("exemploNFA.json"));
            // JSONObject obj = (JSONObject) aaaa;
            // nfa.NFAfromJSON(obj);
            // dfa.DFAfromNFA(nfa);
            // //tratar key
            // JSONArray listaT = (JSONArray) obj.get("transiction");


            Object objetoJSON = parser.parse(new FileReader(diretorioString));
            JSONArray listaNFAJsonArray = new JSONArray();
            
            if(objetoJSON instanceof JSONArray) 
                listaNFAJsonArray = (JSONArray) objetoJSON;

            else if(objetoJSON instanceof JSONObject)
                listaNFAJsonArray.add((JSONObject) objetoJSON);
            
            // Itera JSONArray e converte em NFA segundo método definido na própria classe
            for (Object obj : listaNFAJsonArray) {
                jsonObject = (JSONObject) obj;
                NFA nfaExemplo = new NFA();
                DFA dfaExemplo = new DFA(); 

                //
                /* Verifica se contém todas as chaves necessárias para ser considerado NFA
                 * verifica também se não existem chaves a mais no arquivo json
                 */
                //chavestestar.containsAll(jsonObject.keySet());
                nfaExemplo.NFAfromJSON(jsonObject);
                dfaExemplo.DFAfromNFA(nfaExemplo);

                listaNFA.add(nfaExemplo);
                listaDFA.add(dfaExemplo);
            }

            for(int i=0; i < listaNFA.size(); i++){
                System.out.printf("\n========= NFA %d =========\n", i+1);
                System.out.print(listaNFA.get(i));

                System.out.printf("\n========= DFA %d =========\n", i+1);
                System.out.print(listaDFA.get(i));
            }

        } catch (FileNotFoundException f) {

            System.out.print("O diretório: \"" + diretorioString + "\" não foi encontrado.\n" +
            "Deseja tentar outro?(S | N): ");
            
            switch (sc.nextLine().toUpperCase()) {
                case "S":
                     //Chamada para recomeçar programa
                    System.out.print("\nRecomeçando programa...\n");
                    System.out.println();
                    main(args);
                break;
                    
                case "N":
                    System.out.print("\n      FIM!         \n");
                    System.out.println("      ^.^ bye!         ");
                break;
                
                default:
                    System.out.print("\nOpção não existente!\n");
                    System.out.println("      ^.^ bye!         ");
                    //main(args);
                break;
            } 

        } catch (IndexOutOfBoundsException e) {

            System.out.println("\nMensagem de erro:\nNão é um arquivo de json válido!");
            System.out.printf("O formato do \"%s\" não segue o padrão estabelecido\n\n", diretorioString);

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
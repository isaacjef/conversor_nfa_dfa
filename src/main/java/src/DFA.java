package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFA implements AutomatoFinito {

    //Declaração dos atributos do DFA
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> end_state = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private String initial_state="";
    private Map<String, Map<String, List<String>>> transiction = new HashMap<>();

    //Contrutor padrão do DFA
    public DFA(ArrayList<String> alphabet,
               ArrayList<String> end_state,
               ArrayList<String> states,
               String initial_state,
               Map<String, Map<String, List<String>>> transiction){

        this.alphabet = alphabet;
        this.end_state = end_state;
        this.states = states;
        this.initial_state = initial_state;
        this.transiction = transiction;
    }

    // Construtor para inicializar DFA vazio
    public DFA(){
    }

    /*
     * Consversor do DFA a partir de um NFA
     * @param NFA que deseja converter em um DFA
     */
    public void DFAfromNFA(NFA nfa1){
    //public Map<Object, Map<String, List<String>>> DFAfromNFA(NFA nfa1){
        /*
         * Desenvolver a linha de código responsavel pela converção
         * do NFA em um DFA. Seguindo os passos da sala de aula.
         */

        // Atribuição dos valores comuns entre NFA e DFA
        this.alphabet = nfa1.getAlphabet();
        this.end_state = nfa1.getEnd_state();
        this.states = nfa1.getStates();
        this.initial_state = nfa1.getInitial_state();

        // Passo 2:  Criar todas as combinações possíveis entre os estados
        ArrayList<Object> teste = gerarConjunto(nfa1.getStates());
    
        // Passo 1: Definir a quantidade de estados do DFA
        teste.size();

        // Passo 3: Definir a transição de cada estado
        Map<Object, Map<String, List<String>>> matriz = new HashMap<>();
        ArrayList<String> estadosInitialEnd = new ArrayList<>();

        for (Object chavePrincipal : teste) {

            Map<String, List<String>> matrizAux = new HashMap<>();

            for (String alpha : this.alphabet) {
                Set<String> endList = new HashSet<>();

                // Verifica se chaveprincipal de busca é do tipo List, para tratar quando "null"
                if (chavePrincipal instanceof List) {
                    List<String> chavePrincList = (List<String>) chavePrincipal;
                    List<String> end = new ArrayList<>();

                    // Percorre cada valor contido na matriz/tabela de estados possiveis
                    for (String estadoNFA : chavePrincList) {
                        end = 
                        nfa1.getTransiction().getOrDefault(estadoNFA, Collections.emptyMap()).get(alpha);

                        if (end != null) {
                            endList.addAll(end);
                        } 
                    }
                }

                List<String> destinoFinal = new ArrayList<>(endList);
                Collections.sort(destinoFinal);

                matrizAux.put(alpha, destinoFinal);

            }

            matriz.put(chavePrincipal, matrizAux);

            // Passo 4: Definir o estado inicial e os estados finais ----
            if (chavePrincipal instanceof List) {
                boolean isInitial = false;
                boolean isFinal = false;

                if (!Collections.disjoint((List<String>) chavePrincipal, this.end_state)) 
                    isInitial = true;

                else if (((List<String>) chavePrincipal).equals(Arrays.asList(this.initial_state)))
                    isFinal = true;

                if (isInitial && isFinal) 
                    estadosInitialEnd.add("->*");
                
                else if (isInitial)
                    estadosInitialEnd.add("->");

                else if (isFinal)
                    estadosInitialEnd.add("*");

                else
                    estadosInitialEnd.add("");
            } 
        }

        // Passo 5: Renomear o conjunto de estados
        Map<Object, String> tabelaRenomeada = new HashMap<>();
        int i = 0;

        for (Object chavePrincipal : teste) {
            String name = "A" + i;
            tabelaRenomeada.put(chavePrincipal, name);
            i++;
        }
        System.out.println(tabelaRenomeada);

        // Passo 6: Descartar os estados inacessíveis
        /*

         * Fazendo a leitura de forma adequada teremos o retorno do tipo:
         * 
          Estado         | 0         | 1         |
          ---------------+-----------+-----------+
          null           | null      | null      |
          [q0]           | [q0, q1]  | [q0]      |
          [q1]           | null      | [q2]      |
          [q2]           | null      | null      |
          [q0, q1]       | [q0, q1]  | [q0, q2]  |
          [q0, q2]       | [q0, q1]  | [q0]      |
          [q1, q2]       | null      | [q2]      |
          [q0, q1, q2]   | [q0, q1]  | [q0, q2]  |
         *
         * 
         * f(list_rename.isEmpty()) {
                    tabelaRenomeda.put(alphabet, teste)
                }/
         * 
         */
        Map<String, Map<String, String>> passo6 = new HashMap<>();
        String renomear = "";
        String chave_estado="";
        //{ Código Teste Isaac
        //Análogo ao FOR de cima
        for (Object chavePrincipal : teste) {

            Map<String, String> matrizAux2 = new HashMap<>();

            for (String alpha : this.alphabet) {

                //System.out.println("aba: " + aba);
                List<String> list_aux = matriz.get(chavePrincipal).get(alpha);
                //System.out.println("aba: " + matriz.get(chavePrincipal));
                //System.out.println("List aux: " + list_aux);
                
                renomear = (tabelaRenomeada.get(list_aux));
                //System.out.println("List aux: " + list_aux);
                //Map<String, List<String>> matrizAux2 = new HashMap<>();
                // if (tabelaRenomeada.containsKey(list_aux)) {
                //     renomear = (tabelaRenomeada.get(list_aux));
                //     //System.out.println("List aux: " + renomear);
                // }
                if (chavePrincipal == null || list_aux.contains("")) {
                    renomear = "A0";
                    matrizAux2.put(alpha, renomear);
                } else {
                    renomear = (tabelaRenomeada.get(list_aux));
                    matrizAux2.put(alpha, renomear);
                }

                //System.out.println("matriz aux: " + matrizAux2);
                //passo6.put(aba, matrizAux2);
            }

            chave_estado = tabelaRenomeada.get(chavePrincipal);
            passo6.put(chave_estado, matrizAux2);
        }
        
        //Passo 7: Atribuir resultado ao atributo this.transiction

        //System.out.println("Todas as combinações: " + teste);
        System.out.println("Estados possiveis, entradas e saídas: " + matriz);
        //System.out.println("Estados inicial final: " + estadosInitalEnd);
        //System.out.println("\nTabela renomeada: " + tabelaRenomeada);
        System.out.println("\nResultado final: " + passo6);//*/
    }

    // Conversor de DFa em Json, ao final deve gerar o arquivo json
    public void DFAtoJson(){

    }

    //Criar método para vizualização do DFA no console
    @Override
    public String toString() {

        StringBuilder text = new StringBuilder();

        //text.append("========= NFA =========\n"); //Adicionar titulo do NFA do for de leitura
        text.append("Alfabeto: ").append(alphabet).append("\n");
        text.append("Estados: ").append(states).append("\n");
        text.append("Transições:\n");
        text.append("------------------------\n");
        /*
         * Aninha todos os pares ESTADO, SIMBOLO → ESTADOS de forma ter uma melhor visualização
         */
        transiction.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .forEach(entry -> {
            entry.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey())
            .forEach(trans -> {
                String saida = trans.getValue().isEmpty() ? "null" : trans.getValue().toString();
                text.append(String.format("| (%s, %s) | %s\n", entry.getKey(), trans.getKey(), saida));});
        });
        text.append("\r------------------------\n");
        text.append("Estado Inicial: ").append(initial_state).append("\n");
        text.append("Estados Finais: ").append(end_state).append("\n");        
        text.append("========================\n\n");

        return text.toString();

    }

    /*
     * Função geradora do Conjunto das parte de determinado NFA
     * @param Estados do NFA a ser convertido
     */
    public ArrayList<Object> gerarConjunto(ArrayList<String> states) {
    //public ArrayList<String> gerarConjunto(ArrayList<String> states) {

        ArrayList<List<String>> conjuntoDasPartes = new ArrayList<>();
        conjuntoDasPartes.add(new ArrayList<>()); //Inicializa como conjunto vazio

        // Percorre cada elemento do ArrayList inicial "states"
        for (String state : states) {

            List<List<String>> conjuntoAux = new ArrayList<>();

            // Percorre cada subconjunto já existente no ArrayList princial "conjuntosDasPartes"
            for (List<String> conjuntoAux_2 : conjuntoDasPartes) {

                List<String> conjuntoAux_3 = new ArrayList<>(conjuntoAux_2);
                conjuntoAux_3.add(state);
                conjuntoAux.add(conjuntoAux_3);

            }

            conjuntoDasPartes.addAll(conjuntoAux);

        }

        // Organiza cada elemento com relação ao tamanha do array que o compõem.
        conjuntoDasPartes.sort(Comparator.comparingInt(List::size));

        ArrayList<Object> conjuntoOrganizado = new ArrayList<Object>();
        for (List<String> aux : conjuntoDasPartes) {
            if (aux.isEmpty())
                conjuntoOrganizado.add(null);
            else
                conjuntoOrganizado.add(aux);
        }
        //conjuntoDasPartes.forEach(aux -> aux.isEmpty() ? resultadoFormatado.add("null"): resultadoFormatado.add(aux));

        return conjuntoOrganizado;
    }

    /*
     * Divisão do código para gets e seters
     */
    @Override
    public ArrayList<String> getAlphabet(){
        return this.alphabet;
    }

    @Override
    public void setAlphabet(ArrayList<String> alphabet){
        this.alphabet = alphabet;
    }

    @Override
    public ArrayList<String> getEnd_state() {
        return this.end_state;
    }

    @Override
    public void setEnd_state(ArrayList<String> end_state) {
        this.end_state = end_state;
    }

    @Override
    public ArrayList<String> getStates() {
        return this.states;
    }

    @Override
    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    @Override
    public String getInitial_state() {
        return this.initial_state;
    }

    @Override
    public void setInitial_state(String initial_state) {
        this.initial_state = initial_state;
    }

    @Override
    public Map<String, Map<String, List<String>>> getTransiction() {
        return this.transiction;
    }

    @Override
    public void setTransiction(Map<String, Map<String, List<String>>> transiction) {
        this.transiction = transiction;
    }
}

/**
 * Classe NFA concluída
 * @author Gabriel Alexandre
 * @author github.com/isaacjef/
 */

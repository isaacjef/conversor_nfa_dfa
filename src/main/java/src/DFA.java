package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    Map<Object, String> tabelaRenomeada;

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
        ArrayList<Object> combinacoes = gerarConjunto(nfa1.getStates());
    
        // Passo 1: Definir a quantidade de estados do DFA
        combinacoes.size();

        // Passo 3: Definir a transição de cada estado
        Map<Object, Map<String, List<String>>> matriz = new HashMap<>();

        for (Object chavePrincipal : combinacoes) {

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
        }

        this.renomear_estados(combinacoes, matriz);
    }

    public void renomear_estados(ArrayList<Object> combinacoes, Map<Object, Map<String, List<String>>> matriz) {
        // Passo 5: Renomear o conjunto de estados

        //Map<Object, String> tabelaRenomeada
        tabelaRenomeada = new HashMap<>();
        int i = 0;

        for (Object chavePrincipal : combinacoes) {
            String name = "A" + i;
            tabelaRenomeada.put(chavePrincipal, name);
            i++;
        }

        Map<String, Map<String, String>> passo5 = new HashMap<>();
        String renomear = "";
        String chave_estado = "";
  
        //Análogo ao FOR de cima
        for (Object chavePrincipal : combinacoes) {

            Map<String, String> matrizAux2 = new HashMap<>();

            for (String alpha : this.alphabet) {
                List<String> list_aux = matriz.get(chavePrincipal).get(alpha);

                renomear = tabelaRenomeada.get(list_aux);

                if (renomear == null) {
                    matrizAux2.put(alpha, "A0");
                } else {
                    matrizAux2.put(alpha, renomear);
                }
            }

            chave_estado = tabelaRenomeada.get(chavePrincipal);
            passo5.put(chave_estado, matrizAux2);
        }
        
        //System.out.println("Todas as combinações: " + teste);
        //System.out.println("Estados possiveis, entradas e saídas: " + matriz);
        //System.out.println("Estados inicial final: " + estadosInitalEnd);
        //System.out.println("\nTabela renomeada: " + tabelaRenomeada);

        //return passo5;
        this.deletar_inacessiveis(passo5);
    }

    public void deletar_inacessiveis(Map<String, Map<String, String>> passo5) {
        // Passo 6: Descartar os estados inacessíveis
        /*
         * Exemplo de acesso a tipo de estado, se é inicial e/ou final:
         * for (int i; i < n; i++){
         *      estadosInitialEnd.get(i);
         *      matriz.get(i);
         * }
         * 
         * Como eles são atribuídos igualmente durante as iterações do for (Object chavePrincipal : teste)
         * Seus valores ja estão alinhados e se referem a mesma linha da própria matriz. 
         * Fazendo a leitura de forma adequada teremos o retorno do tipo:
         * 
          Estado         | 0         | 1         |
          ---------------+-----------+-----------+
          null           | null      | null      |
          -> [q0]        | [q0, q1]  | [q0]      |
          [q1]           | null      | [q2]      |
          * [q2]         | null      | null      |
          [q0, q1]       | [q0, q1]  | [q0, q2]  |
          * [q0, q2]     | [q0, q1]  | [q0]      |
          * [q1, q2]     | null      | [q2]      |
          * [q0, q1, q2] | [q0, q1]  | [q0, q2]  |
         *
         * 
         * f(list_rename.isEmpty()) {
                    tabelaRenomeda.put(alphabet, teste)
                }/
         * 
        */
        List<String> novos_estados = new ArrayList<>(tabelaRenomeada.values());
        //List<String> estados_aux = new ArrayList<>(novos_estados);
        System.out.println("\novos estados: " + novos_estados);

        for (String estado : novos_estados) {
            
            for (String alpha : this.alphabet) {
                Iterator<String> iterator = novos_estados.iterator();
                int ax = 0;
                int i = 0;
                while (i < novos_estados.size()) {
                    //iterator.hasNext()
                    //String elemento = iterator.next();

                    //Ex: A0 -> {0, A0} {1, A0}, false, false.
                    if (estado.equals(passo5.get(novos_estados.get(i)).get(alpha))) {
                        System.out.println("As entradas bateram!");
                    } else {
                        System.out.println("As entradas não batem!");
                        ax++;
                    }

                    if (ax == novos_estados.size()) {
                        novos_estados.remove(estado);
                    }
                    i++;
                    System.out.println("Ax: " + ax);
                }

                // Iterator<String> iterator = lista.iterator();
                // String elemento = iterator.next();
                // estados_aux.forEach(var -> {
                //     if (estado.equals(passo5.get(var).get(alpha))) {
                //         System.out.println("As entradas bateram! " + estado);
                //         //System.out.println("As entradas bateram! " + estado);
                //     } else {
                //         System.out.println("As entradas não batem!");

                //     }
                // });

                /*
                 * estados_aux.forEach(var -> {
                        if (estado.equals(passo5.get(estado).get(alpha))) {
                            System.out.println("As entradas bateram!");
                        } else {
                            System.out.println("As entradas não batem!");
                            ax = 1;
                        }
                    });
                 */

            }
        }

        System.out.println("\nResultado final: " + passo5);

        //Passo 7: Atribuir resultado ao atributo this.transiction
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

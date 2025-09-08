package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    private Map<String, Map<String, String>> transiction = new HashMap<>();

    //Contrutor padrão do DFA
    public DFA(Object alphabet,
               ArrayList<String> end_state,
               ArrayList<String> states,
               String initial_state,
               Map<String, Map<String, String>> transiction){
            
        try {
            setAlphabet(alphabet);
            setEnd_state(end_state);
            setStates(states);
            setInitial_state(initial_state);
            setTransictionD(transiction);
        } catch (IllegalArgumentException e){
            System.out.println("Impossivel criar DFA!");
        }
        
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
        // Tratativa de erro para tipo de parametro errado
        try{
            setAlphabet(nfa1.getAlphabet());
            setEnd_state(nfa1.getEnd_state());
            setInitial_state(nfa1.getInitial_state());
        } catch (IllegalArgumentException e) {
            System.out.println("Valor ilegal no atributo do DFA");
        }
 
        // try{
        //     this.alphabet = new ArrayList<>(nfa1.getAlphabet());
        //     this.alphabet = "oi";
        //     this.end_state = new ArrayList<>(nfa1.getEnd_state());
        //     this.initial_state = "" + nfa1.getInitial_state();
        // } catch(){

        // }
        

        // Passo 2:  Criar todas as combinações possíveis entre os estados
        ArrayList<Object> teste = gerarConjunto(nfa1.getStates());
        //System.out.println(teste + "\n" + teste.size());

    
        // Passo 1: Definir a quantidade de estados do DFA
        teste.size();

        // Passo 3: Definir a transição de cada estado
        Map<Object, Map<String, List<String>>> matriz = new HashMap<>();

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

        }

        // Passo 5: Renomear o conjunto de estados
        Map<Object, String> estadosRenomeados = new HashMap<>();
        int i = 0;

        for (Object chavePrincipal : teste) {
            String name = "A" + i;
            estadosRenomeados.put(chavePrincipal, name);
            i++;
        }

        Map<String, Map<String, String>> tabelaRenomeada = new HashMap<>();
        String renomear = "";
        String chave_estado= "";
        ArrayList<String> trava_endstate = new ArrayList<>(this.end_state);
        String trava_initalstate = "" + this.initial_state;
        this.end_state.clear();

        for (Object chavePrincipal : teste) {

            // Passo 4: : Definir o estado inicial e os estados finais
            if (chavePrincipal instanceof List) {

                List<String> estadoAtual = (List<String>) chavePrincipal;
                //System.out.println("Estado inicial: " + trava_initalstate);
                if (estadoAtual.equals(Arrays.asList(trava_initalstate))){
                    
                    setInitial_state(estadosRenomeados.get(Arrays.asList(this.initial_state)));
                }
                
                for (String end_stateAux : trava_endstate)

                    if (estadoAtual.contains(end_stateAux)) {
                        this.end_state.add(estadosRenomeados.get(chavePrincipal));
                        break;
                    }

            }

            Map<String, String> matrizAux2 = new HashMap<>();

            for (String alpha : this.alphabet) {

                List<String> list_aux = matriz.get(chavePrincipal).get(alpha);


                // Verificaçõa condicional para encontrar ocorrencias de "null" e substituir pelo seu nome
                if (list_aux.size()==0) {
                    renomear = (estadosRenomeados.get(null));
                } else {
                    renomear = (estadosRenomeados.get(list_aux));
                }

                matrizAux2.put(alpha, renomear);

            }

            chave_estado = estadosRenomeados.get(chavePrincipal);
            this.states.add(chave_estado);
            tabelaRenomeada.put(chave_estado, matrizAux2);
        }

        // Passo 6: Descartar os estados inacessíveis
        ArrayList<String> estadosAcessiveis = new ArrayList<>();
        Set<String> statesVisitado = new HashSet<String>();
        List<String> statesNaoPercorridos = new ArrayList<String>();
        statesNaoPercorridos.add(this.initial_state);
        statesVisitado.add(this.initial_state);

        for (int ij = 0; ij < statesNaoPercorridos.size(); ij++) {
            String currentState = statesNaoPercorridos.get(ij);

            // Verifica se o estado atual tem transições definidas no grafo.
            if (tabelaRenomeada.containsKey(currentState)) {
                // Pega a coleção de todos os possíveis estados seguintes.
                Collection<String> nextStates = tabelaRenomeada.get(currentState).values();

                // Itera sobre cada estado seguinte usando um enhanced for-loop (forEach).
                for (String nextState : nextStates) {
                    // Se o estado seguinte ainda não foi visitado...
                    if (!statesVisitado.contains(nextState)) {
                        // ...o adicionamos ao conjunto de visitados e à lista para ser processado mais tarde.
                        statesVisitado.add(nextState);
                        statesNaoPercorridos.add(nextState);
                    }
                }

            }
        }
        estadosAcessiveis.addAll(statesVisitado);

        //System.out.println("\nEstados renomeada: " + estadosRenomeados);
        System.out.println("\nTabela renomeada: " + tabelaRenomeada);
        System.out.println("\nAcessiveis: " + estadosAcessiveis);
        

        for (String chavePrincipal : this.states) {
            
            if(!estadosAcessiveis.contains(chavePrincipal)) {
                tabelaRenomeada.remove(chavePrincipal);
                this.end_state.remove(chavePrincipal);
            }

        }
        //System.out.println(tabelaRenomeada);

        setEnd_state(estadosAcessiveis);
        setTransictionD(tabelaRenomeada);

        //System.out.println("Todas as combinações: " + teste);
        //System.out.println("Estados possiveis, entradas e saídas: " + matriz);
        //System.out.println("Estados inicial final: " + estadosInitalEnd);
        //System.out.println("\nTabela renomeada: " + estadosRenomeados);
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

    /*errado
     * Divisão do código para gets e seters
     */
    @Override
    public ArrayList<String> getAlphabet(){
        return this.alphabet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAlphabet(Object alphabet) {

        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (alphabet instanceof List){
            ArrayList<?> alphabet0 = (ArrayList<?>) alphabet;
            int cont=0;
            for (Object precorrer : alphabet0)
                if(precorrer instanceof String)
                    cont++;

            if(cont==alphabet0.size())
                this.alphabet = (ArrayList<String>) alphabet0;
            else
                throw new IllegalArgumentException();
            
        } else
            throw new IllegalArgumentException();
    }

    @Override
    public ArrayList<String> getEnd_state() {
        return this.end_state;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setEnd_state(Object end_state) {

        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (end_state instanceof List){
            ArrayList<?> end_state0 = (ArrayList<?>) end_state;
            int cont=0;
            for (Object precorrer : end_state0)
                if(precorrer instanceof String)
                    cont++;

            if(cont==end_state0.size())
                this.end_state = (ArrayList<String>) end_state0;
            else
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ArrayList<String> getStates() {
        return new  ArrayList<>(this.states);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setStates(Object states) {

        /*
         * Nota: É de extrema importancia que esta função não seja modificada
         * sua estrutura da forma como esta é chave para conversão NFA -> DFA
         * caso julgue necessário criar outra função setStatesX()
         */

        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (states instanceof List){
            ArrayList<?> states0 = (ArrayList<?>) states;
            int cont=0;
            for (Object precorrer : states0)
                if(precorrer instanceof String)
                    cont++;

            if(cont==states0.size())
                this.states = (ArrayList<String>) states0;
            else
                throw new IllegalArgumentException();
        }

    }

    @Override
    public String getInitial_state() {
        return this.initial_state;
    }

    @Override
    public void setInitial_state(Object initial_state) {

        if(initial_state instanceof String) {
                this.initial_state = (String) initial_state;
        }
        else {
            throw new IllegalArgumentException();
        }
            
        
    }

    public Map<String, Map<String, String>> getTransiction() {
        return this.transiction;
    }

    public void setTransictionD(Map<String, Map<String, String>> transiction) {
        this.transiction = transiction;
    }
}

/**
 * Classe NFA concluída
 * @author Gabriel Alexandre
 * @author github.com/isaacjef/
 */

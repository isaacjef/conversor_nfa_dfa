package src;

import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
               Map<String, Map<String, String>> transiction) {
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
        try {
            setAlphabet(nfa1.getAlphabet());
            setEnd_state(nfa1.getEnd_state());
            setInitial_state(nfa1.getInitial_state());
        } catch (IllegalArgumentException e) {
            System.out.println("Valor ilegal no atributo do DFA");
        }

        // PASSO 2:  Criar todas as combinações possíveis entre os estados
        ArrayList<Object> combinacoes = gerarConjunto(nfa1.getStates());
    
        // PASSO 1: Definir a quantidade de estados do DFA
        combinacoes.size();

        // PASSO 3: Definir a transição de cada estado
        Map<Object, Map<String, List<String>>> matriz = new HashMap<>();

        for (Object chavePrincipal : combinacoes) {

            Map<String, List<String>> matrizAux = new HashMap<>();

            for (String alpha : this.alphabet) {
                Set<String> endList = new HashSet<>();

                // Verifica se chaveprincipal de busca é do tipo List, para tratar quando "null"
                if (chavePrincipal instanceof List) {
                    List<String> chavePrincList = (List<String>) chavePrincipal;
                    List<String> end;

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
        
        //Leva ao passo 5 e passo 6
        this.renomear_estados(combinacoes, matriz);
    }

    // PASSO 5: Renomear o conjunto de estados
    public void renomear_estados(ArrayList<Object> combinacoes, Map<Object, Map<String, List<String>>> matriz) {
        Map<Object, String> estadosRenomeados = new HashMap<>();
        int i = 0;

        for (Object chavePrincipal : combinacoes) {
            String name = "A" + i;
            estadosRenomeados.put(chavePrincipal, name);
            i++;
        }

        Map<String, Map<String, String>> tabelaRenomeada = new HashMap<>();
        String renomear = "";
        String chave_estado = "";
        ArrayList<String> trava_endstate = new ArrayList<>(this.end_state);
        String trava_initalstate = "" + this.initial_state;
        this.end_state.clear();

        for (Object chavePrincipal : combinacoes) {

            // Passo 4: : Definir o estado inicial e os estados finais
            if (chavePrincipal instanceof List) {

                List<String> estadoAtual = (List<String>) chavePrincipal;
                if (estadoAtual.equals(Arrays.asList(trava_initalstate)))
                    setInitial_state(estadosRenomeados.get(Arrays.asList(this.initial_state)));
                
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
                if (list_aux.isEmpty()) {
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
        //System.out.println("Estados renomeados; " + estadosRenomeados);
        //Leva ao Passo 6
        this.deletar_inacessiveis(tabelaRenomeada);
        
    }

    //PASSO 6: Descartar os estados inacessíveis
    public void deletar_inacessiveis(Map<String, Map<String, String>> tabelaRenomeada) {
  
        ArrayList<String> estadosAcessiveis = new ArrayList<>();
        Set<String> statesVisitado = new HashSet<>();
        List<String> statesNaoPercorridos = new ArrayList<>();
        statesNaoPercorridos.add(this.initial_state);
        statesVisitado.add(this.initial_state);

        for (int ij = 0; ij < statesNaoPercorridos.size(); ij++) {
            
            String stateAtual = statesNaoPercorridos.get(ij);

            if (tabelaRenomeada.containsKey(stateAtual)) {

                Collection<String> ProximoStates = tabelaRenomeada.get(stateAtual).values();

                for (String proximoState : ProximoStates) {

                    if (!statesVisitado.contains(proximoState)) {
                        statesVisitado.add(proximoState);
                        statesNaoPercorridos.add(proximoState);
                    }

                }
            }
        }
        estadosAcessiveis.addAll(statesVisitado);

        
        // System.out.println("\nTabela renomeada: " + tabelaRenomeada);
        // System.out.println("\nAcessiveis: " + estadosAcessiveis);
        

        for (String chavePrincipal : this.states) {
            
            if(!estadosAcessiveis.contains(chavePrincipal)) {
                tabelaRenomeada.remove(chavePrincipal);
                this.end_state.remove(chavePrincipal);
            }

        }
        //System.out.println(tabelaRenomeada);

        setStates(estadosAcessiveis);
        setTransictionD(tabelaRenomeada);
        //System.out.println("\nEstados renomeada: " + estadosRenomeados);

        //System.out.println("\nResultado final: " + tabelaRenomeada);

    }

    // Conversor de DFa em Json, ao final deve gerar o arquivo json
    //@SuppressWarnings("unchecked")
    public void DFAtoJson(String nome) {
        
        FileWriter writeFile = null;

        Map<String, Object> dfaMap = new HashMap<>();
        
        dfaMap.put("alphabet", this.getAlphabet());
        dfaMap.put("states", this.states);
        dfaMap.put("transiction", this.converteTransiction());
        dfaMap.put("initial_state", this.initial_state);
        dfaMap.put("end_state", this.end_state);

		try {

            String nomeClass = this.getClass().getSimpleName();
            writeFile = new FileWriter(String.format("%s%sConvertido.json", nomeClass.toLowerCase(), nome));
            writeFile.write(JSONValue.toJSONString(dfaMap));
			writeFile.close();
	
		}

		//Trata as exceptions que podem ser lançadas no decorrer do processo

		catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
            
        }
    }

    // Método auxiliar para salvar manter organização do código
    @SuppressWarnings("unchecked")
    private JSONArray converteTransiction() {
        
        Map<String, Map<String, String>> transictionMap = this.getTransiction();
        
        JSONArray transictionJsonArray = new JSONArray();

        System.out.println(transictionMap.keySet());
        for (String chave : transictionMap.keySet()) {

            for (String alpha : transictionMap.get(chave).keySet()) {

                JSONObject transictionJsonObject = new JSONObject();
                //System.out.println("Simbolo: " + alpha);

                transictionJsonObject.put("initial", chave);
                transictionJsonObject.put("end", transictionMap.get(chave).get(alpha));
                transictionJsonObject.put("symbol", alpha);
                transictionJsonArray.add(transictionJsonObject);
            }
            //transictionJsonArray.add(transictionJsonObject);
        }
        
        //System.out.println(transictionJsonArray);
        //transictionJsonArray.add(transictionJsonObject);

        return transictionJsonArray;

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
                String saida = trans.getValue().isEmpty() ? "null" : trans.getValue();
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

        ArrayList<Object> conjuntoOrganizado = new ArrayList<>();
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
        return new ArrayList<String>(this.alphabet);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAlphabet(Object alphabet) {

        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (alphabet instanceof List){
            ArrayList<?> alphabet0 = (ArrayList<?>) alphabet;
            int cont=0;
            for (Object percorrer : alphabet0)
                if(percorrer instanceof String)
                    cont++;
            if(cont==alphabet0.size())
                this.alphabet = (ArrayList<String>) alphabet0;
            else
                throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ArrayList<String> getEnd_state() {
        return new ArrayList<String>(this.end_state);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setEnd_state(Object end_state) {
        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (end_state instanceof List){
            ArrayList<?> end_state0 = (ArrayList<?>) end_state;
            int cont=0;
            for (Object percorrer : end_state0)
                if(percorrer instanceof String)
                    cont++;
            if(cont==end_state0.size())
                this.end_state = (ArrayList<String>) end_state0;
            else
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ArrayList<String> getStates() {
        return new ArrayList<String>(this.states);
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
            for (Object percorrer : states0)
                if(percorrer instanceof String)
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
        if(initial_state instanceof String string) {
                this.initial_state = string;
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

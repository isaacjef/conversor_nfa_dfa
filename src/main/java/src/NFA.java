package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NFA implements AutomatoFinito {

    //Declaração dos atributos do NFA
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> end_state = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private String initial_state="";
    private Map<String, Map<String, List<String>>> transiction = new HashMap<>();

    //Contrutor padrão do NFA
    public NFA(ArrayList<String> alphabet,
               ArrayList<String> end_state,
               ArrayList<String> states,
               String initial_state,
               Map<String, Map<String, List<String>>> transiction){

        setAlphabet(alphabet);
        setEnd_state(end_state);
        setStates(states);
        setInitial_state(initial_state);
        setTransiction(transiction);
    }

    //Construtor para inicializar NFA vazio
    public NFA(){
    }

    /*
     * Função de conversão JSONObject para classe NFA
     * @param JSONObject
     */
    @SuppressWarnings("unchecked")
    public void NFAfromJSON(JSONObject json) {
        
        // Atribuindo valores aos atributos da classe
        // Verificar questão da TIPAGEM das variaveis para mitigação de erros
        // Caso de haver garantia na tipagem do json não se faz necessário corrigir

        try{
            setAlphabet(new ArrayList<>((JSONArray) json.get("alphabet")));
            setStates(new ArrayList<>((JSONArray) json.get("states")));
            setInitial_state((String) json.get("initial_state"));
            setEnd_state(new ArrayList<>((JSONArray) json.get("end_state")));
            setTransiction(parametrizarTransiction((JSONArray) json.get("transiction")));
        } catch (IllegalArgumentException e) {
            System.out.println("Valor ilegal no atributo do NFA");

            // Inicializando NFA com valores vazios:
            this.alphabet = new ArrayList<>();
            this.end_state = new ArrayList<>();
            this.states = new ArrayList<>();
            this.initial_state="";
            this.transiction = new HashMap<>();
        }
    }

    //Método para vizualização direta do NFA no console
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

    private Map<String, Map<String, List<String>>> parametrizarTransiction(JSONArray jsonArray){

        Map<String, Map<String, List<String>>> transiction = new HashMap<>();

        for (Object objRegra : jsonArray) {

            JSONObject regra = (JSONObject) objRegra;
            String initial = (String) regra.get("initial");
            String simbolo = (String) regra.get("symbol");
            Object endObj = regra.get("end");
            
            // Ignora função de transição cuja entrada é ínvalida
            if(!this.getStates().contains(initial) || !this.getAlphabet().contains(simbolo)){
                throw new IllegalArgumentException();
            }
            List<String> listaEstadosFinais = new ArrayList<>();

            if (endObj instanceof JSONArray){
                // Tratar a TIPAGEM da variavel para mitigação de erros
                JSONArray endArray = (JSONArray) endObj;

                if(!this.getStates().containsAll(endArray)){
                    throw new IllegalArgumentException();
                }

                //Remove o "null" caso dentro de um array, exemplo: ["q0","null"]
                for (Object estadoFinal : endArray) {

                    if (estadoFinal != null && !"null".equals(estadoFinal.toString())) {
                        listaEstadosFinais.add(estadoFinal.toString());
                    }
                }
            }

            transiction.computeIfAbsent(initial, k -> new HashMap<>())
            .put(simbolo, listaEstadosFinais);
            /*
             * Explicação: método computeIfAbsent() → Método da interface Map e propõe a obtenção
             * de um valor e caso ele não exista então crie ele primeiro.
             * 
             * .put(...) → método da interface Map e serve para a adicição de um determinado valor
             * ao Map em questão. Requisitando de 2 parametros: chave e valor.
             * 
             * Em resumo: de acordo com initial e k (simbolos associados os estado em questão)
             * "q0" atribui a esse como chave do Map externo e
             * "0", "["q0", "q1"]" e "1", "["q0"]" como valores do Map externo
             * Enquanto os mesmos "0", "["q0", "q1"]", são chave e valor, respectivamente, do Map interno
             * O mesmo vale para "1", "["q0"]".
             */
        }

        return transiction;

    }

    /*
     * Divisão do código para gets e seters
     */
    @Override
    public ArrayList<String> getAlphabet(){
        return new ArrayList<>(this.alphabet);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAlphabet(Object alphabet){

        // Verificações condicionais para tudo, garantido tipagem correta dos valores
        if (alphabet instanceof List){
            ArrayList<?> alphabet0 = (ArrayList<?>) alphabet;
            int cont=0;
            for (Object precorrer : alphabet0){
                if(precorrer instanceof String) {
                    cont++;
                } else {
                    throw new IllegalArgumentException();
                }

            }

            if(cont==alphabet0.size()) {
                this.alphabet = (ArrayList<String>) alphabet0;
            }
            
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
            for (Object precorrer : end_state0)
                if(precorrer instanceof String)
                    if(this.getStates().contains(precorrer))
                        cont++;

            if(cont==end_state0.size()){
                this.end_state = (ArrayList<String>) end_state0;
                //System.out.println(this.end_state);
            }
            else
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ArrayList<String> getStates() {
        return new ArrayList<>(this.states);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setStates(Object states) {


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
        return "" + this.initial_state;
    }

    @Override
    public void setInitial_state(Object initial_state) {
        
        if(initial_state instanceof String) {
            if(this.getStates().contains((String) initial_state))
                this.initial_state = (String) initial_state;
            else
                throw new IllegalArgumentException();
        } else
            throw new IllegalArgumentException();

    }

    public Map<String, Map<String, List<String>>> getTransiction() {
        return this.transiction;
    }

    public void setTransiction(Map<String, Map<String, List<String>>> transiction) {
        this.transiction = transiction;
    }
    
}

/**
 * Classe NFA concluída
 * @author Gabriel Alexandre
 */
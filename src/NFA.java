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

        this.alphabet = alphabet;
        this.end_state = end_state;
        this.states = states;
        this.initial_state = initial_state;
        this.transiction = transiction;
    }

    //Construtor para inicializar NFA vazio
    public NFA(){
    }

    // Passar no parametro da função o Json convertido em String ou Objeto
    // criar metodos para adicionar os valores a cada atributo corretamente
    /*
     * 
     * 
     */
    public void NFAfromJSON(JSONObject json) {
        
        // Atribuindo valores aos atributos da classe
        // Verificar questão da TIPAGEM das variaveis para mitigação de erros
        setAlphabet(new ArrayList<>((JSONArray) json.get("alphabet")));
        setStates(new ArrayList<>((JSONArray) json.get("states")));
        setInitial_state((String) json.get("initial_state"));
        setEnd_state(new ArrayList<>((JSONArray) json.get("end_state")));
        setTransiction(parametrizarTransiction((JSONArray) json.get("transiction")));
        
    }

    //Criar método para vizualização do NFA no console
    @Override
    public String toString(){

        StringBuilder text = new StringBuilder();

        //text.append("========= NFA =========\n"); //Adicionar titulo do NFA do for de leitura
        text.append("Alfabeto: ").append(alphabet).append("\n");
        text.append("Estados: ").append(states).append("\n");
        text.append("Transições:\n");
        text.append("------------------------\n");

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

    private static Map<String, Map<String, List<String>>> parametrizarTransiction(JSONArray jsonArray){

        Map<String, Map<String, List<String>>> transiction = new HashMap<>();

        for (Object objRegra : jsonArray) {

            JSONObject regra = (JSONObject) objRegra;
            String estadoInicial = (String) regra.get("initial");
            String simbolo = (String) regra.get("symbol");
            Object estadosFinaisObj = regra.get("end");

            List<String> listaEstadosFinais = new ArrayList<>();

            if (estadosFinaisObj instanceof JSONArray){
                // Tratar a TIPAGEM da variavel para mitigação de erros
                JSONArray endArray = (JSONArray) estadosFinaisObj;

                //Remove o "null" caso dentro de um array, exemplo: ["q0","null"]
                for (Object estadoFinal : endArray) {

                    if (estadoFinal != null && !"null".equals(estadoFinal.toString())) {
                        listaEstadosFinais.add(estadoFinal.toString());
                    }

                }
            }

            transiction.computeIfAbsent(estadoInicial, k -> new HashMap<>())
            .put(simbolo, listaEstadosFinais);
        }

        return transiction;

    }

    /*
     * Divisão do código para gets e seters
     */
    public ArrayList<String> getAlphabet(){
        return this.alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet){
        this.alphabet = alphabet;
    }

    public ArrayList<String> getEnd_state() {
        return this.end_state;
    }

    public void setEnd_state(ArrayList<String> end_state) {
        this.end_state = end_state;
    }

    public ArrayList<String> getStates() {
        return this.states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public String getInitial_state() {
        return this.initial_state;
    }

    public void setInitial_state(String initial_state) {
        this.initial_state = initial_state;
    }

    public Map<String, Map<String, List<String>>> getTransiction() {
        return this.transiction;
    }

    public void setTransiction(Map<String, Map<String, List<String>>> transiction) {
        this.transiction = transiction;
    }
    
}


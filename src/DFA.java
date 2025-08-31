package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Consversor do DFA a partir de um NFA
    public void DFAfromNFA(NFA nfa1){
        /*
         * Desenvolver a linha de código responsavel pela converção
         * do NFA em um DFA. Seguindo os passos da sala de aula.
         */

        
        // Passo 2
        ArrayList<Object> teste = gerarConjunto(nfa1.getStates());

        // Passo 1
        teste.size();
    }

    // Conversor de DFa em Json, ao final deve gerar o arquivo json
    public void DFAtoJson(){

    }

    //Criar método para vizualização do DFA no console
    @Override
    public String toString(){
        return "";
    }

    /*
     * Função geradora do Conjunto das parte de determinado NFA
     * @param Estados do NFA a ser convertido
     */
    public static ArrayList<Object> gerarConjunto(ArrayList<String> states) {

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
                conjuntoOrganizado.add("null");
            else
                conjuntoOrganizado.add(aux);

        }
        //conjuntoDasPartes.forEach(aux -> aux.isEmpty() ? resultadoFormatado.add("null"): resultadoFormatado.add(aux));

        return conjuntoOrganizado;
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

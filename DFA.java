import java.util.ArrayList;
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
    }

    // Conversor de DFa em Json, ao final deve gerar o arquivo json
    public void DFAtoJson(){

    }

    //Criar método para vizualização do DFA no console
    @Override
    public String stringfy(){
        return "";
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

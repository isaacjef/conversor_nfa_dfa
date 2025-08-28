import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;


public class NFA implements AutomatoFinito {


    //Declaração dos atributos do NFA
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> end_state = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private String initial_state="";
    private Map<String, Map<String, List<String>>> transiction = new HashMap<>();

    //Contrutor padrão do NFA
    public NFA(ArrayList<String> alphabet){
        this.alphabet = alphabet;
    }

    // Passar no parametro da função o Json convertido em String ou Objeto
    // criar metodos para adicionar os valores a cada atributo corretamente
    public void NFAfromJSON(JSONObject json){
        this.alphabet = null;
    }

    public ArrayList<String> getAlphabet(){
        return new ArrayList<String>();
    }

    public void setAlphabet(ArrayList<String> alphabet){
        this.alphabet = alphabet;
    }

    //Criar método para vizualização do NFA no console
    public String stringfyNFA(){
        return "";
    }
    
}


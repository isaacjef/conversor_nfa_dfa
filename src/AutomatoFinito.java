package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AutomatoFinito {
    

    public ArrayList<String> getAlphabet();
    public void setAlphabet(ArrayList<String> param);

    public ArrayList<String> getEnd_state();
    public void setEnd_state(ArrayList<String> param);

    public ArrayList<String> getStates();
    public void setStates(ArrayList<String> param);

    public String getInitial_state();
    public void setInitial_state(String param);

    //Map<String, Map<String, List<String>>> transicoes = new HashMap<>();
    public Map<String, Map<String, List<String>>> getTransiction();
    public void setTransiction(Map<String, Map<String, List<String>>> param);

    //public String stringfy();

}

/**
 *
 * @author Gabriel Alexandre
 */

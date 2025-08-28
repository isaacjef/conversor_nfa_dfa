import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AutomatoFinito {
    

    public ArrayList<String> getAlphabet();
    public void setAlphabet();

    public ArrayList<String> getEnd_state();
    public void setEnd_state();

    public String getStates();
    public String setStates();

    public ArrayList<String> getInitial_state();
    public void setInitial_state();

    //Map<String, Map<String, List<String>>> transicoes = new HashMap<>();
    public Map<String, Map<String, List<String>>> getTransiction();
    public void setTransiction();
    
}

/**
 *
 * @author Gabriel Alexandre
 */

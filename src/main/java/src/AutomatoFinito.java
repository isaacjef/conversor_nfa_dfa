package src;

import java.util.ArrayList;

public interface AutomatoFinito {
    public ArrayList<String> getAlphabet();
    public void setAlphabet(Object param);

    public ArrayList<String> getEnd_state();
    public void setEnd_state(Object param);

    public ArrayList<String> getStates();
    public void setStates(Object param);

    public String getInitial_state();
    public void setInitial_state(Object param);


    //Map<String, Map<String, List<String>>> transicoes = new HashMap<>();
    // public Map<String, Map<String, List<String>>> getTransiction();
    // public void setTransiction(Map<String, Map<String, List<String>>> param);

    //public String stringfy();

}

/**
 *
 * @author Gabriel Alexandre
 */

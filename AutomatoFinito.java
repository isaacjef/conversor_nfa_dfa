import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface AutomatoFinito {
    /*{
 
 
 "transiction": [
 {"initial": "q0", "symbol": "0", "end": ["q0", "q1"]},
 {"initial": "q0", "symbol": "1", "end": ["q0"]},
 {"initial": "q1", "symbol": "0", "end": "null"},
 {"initial": "q1", "symbol": "1", "end": ["q2"]},
 {"initial": "q2", "symbol": "0", "end": "null"},
 {"initial": "q2", "symbol": "1", "end": "null"}
 ],
 
 
} */
    public ArrayList<String> alphabet = new ArrayList<>();
    public ArrayList<String> end_state = new ArrayList<>();
    public ArrayList<String> states = new ArrayList<>();
    public String initial_state="0";
    public ArrayList<String> transiction = new ArrayList<>();

    public ArrayList<String> getAlphabet();
    public ArrayList<String> setAlphabet();

    public ArrayList<String> getEnd_state();
    public ArrayList<String> setEnd_state();

    public String getStates();
    public String setStates();

    public ArrayList<String> getInitial_state();
    public ArrayList<String> setInitial_state();

    public ArrayList<Object> getTransiction();
    public ArrayList<Object> setTransiction();
    
}

/**
 *
 * @author Gabriel Alexandre
 */

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import api.NodeData;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.Arrays;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(json_file))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            HashMap<String, ArrayList<HashMap<String, Double>>> h = new Gson().fromJson(obj.toString(), HashMap.class);

            for(Map.Entry v: h.entrySet()){
                for (int i = 0; i < h.get(v.getKey()).size();i++){
                    h.get(v.getKey()).set(i,new HashMap<String,Double>(h.get(v.getKey()).get(i)));
                }
            }
            ans = new DirectedWeightedGraphClass(h);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans =  new DirectedWeightedGraphAlgorithmsClass(null);
        ans.load(json_file);
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        GuiRun a = new GuiRun((DirectedWeightedGraphAlgorithmsClass) alg);
        a.setVisible(true);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args) {

        /*DirectedWeightedGraph ans = getGrapg(args[0]);
        DirectedWeightedGraphAlgorithmsClass g = new DirectedWeightedGraphAlgorithmsClass((DirectedWeightedGraphClass) ans);
        g.save(args[1]);
        GuiRun a = new GuiRun(g);
        a.setVisible(true);*/

        runGUI(args[0]);





    }
}
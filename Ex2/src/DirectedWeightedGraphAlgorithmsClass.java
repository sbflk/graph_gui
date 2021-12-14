import api.*;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraphClass g;
    private HashMap<Integer,HashMap<Integer,HashMap<String,Double>>> dijkstra_map;

    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        this.g = g;
        this.dijkstra_map = new HashMap<>();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (DirectedWeightedGraphClass) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraphClass new_g = null;
        ArrayList<EdgeData> edges = new ArrayList<EdgeData>();
        HashMap<Double, NodeData> nodes = new HashMap<>();

        // deep copy of the edges
        Iterator<EdgeData> it = this.g.edgeIter();
        while(it.hasNext())
        {
            EdgeData e = it.next();
            EdgeDataClass e1 = new EdgeDataClass(e.getSrc(),e.getWeight(),e.getDest());
            edges.add(e1);
        }

        // deep copy of the Nodes
        Iterator<NodeData> it1 = this.g.nodeIter();
        while(it1.hasNext())
        {
            NodeData n = it1.next();
            GeoLocation pos = n.getLocation();
            GeoLocation new_pos = new GeoLocationClass(pos.x(),pos.y(),pos.z());
            NodeDataClass n1 = new NodeDataClass(n.getKey(), new_pos);
            nodes.put((double)n1.getKey(), n1);
        }

        DirectedWeightedGraphClass g = new DirectedWeightedGraphClass(nodes, edges);
        return g;
    }
    // a function to check if we can reach every vertex from a certain vertex(v)
    // we know if we reached a vertex if in the index of the array there is true, and the index
    // is the vertex id.

    public void dfs(int v, boolean[] visited)
    {
        // we reach the vertex we are in
        visited[v] = true;
        int dest;
        // we get iterator of all the edges tht we are the source of them
        Iterator<EdgeData> it = this.g.edgeIter(v);
        while(it.hasNext())
        {
            //we see if we havent visited a vertex we can reach with the edges of this vertex
            EdgeDataClass edge = (EdgeDataClass) it.next();
            dest = edge.getDest();
            // if we havent visited, we visit and update the array recursively.
            if(!visited[dest])
            {
                dfs(dest, visited);
            }
        }
    }

    // a function to revese the edges in the graph
    public DirectedWeightedGraphAlgorithmsClass reverseGraph()
    {
        // creating a copy of this graph
        DirectedWeightedGraphAlgorithms new_ga = new DirectedWeightedGraphAlgorithmsClass(this.g);
        DirectedWeightedGraphClass copy_graph = (DirectedWeightedGraphClass) new_ga.copy();

        ArrayList<EdgeData> edges = new ArrayList<>();
        Iterator<EdgeData> it = copy_graph.edgeIter();
        // moving through the edges, saving them as reverse edge in an array, and erasing the originals.
        while(it.hasNext())
        {
            EdgeDataClass edge = (EdgeDataClass) it.next();
            EdgeDataClass new_edge = new EdgeDataClass(edge.getDest(), edge.getWeight(), edge.getSrc());
            edges.add(new_edge);
            it.remove();
        }

        // adding the revese edge we created and reversing the graph
        Iterator<EdgeData> new_it = edges.iterator();
        while(new_it.hasNext())
        {
            EdgeDataClass new_edge = (EdgeDataClass)new_it.next();
            copy_graph.connect(new_edge.getSrc(),new_edge.getDest(), new_edge.getWeight());
        }

        // putting the graph to this class so we can use it in the dfs function of this class
        DirectedWeightedGraphAlgorithmsClass new_copy_graph = new DirectedWeightedGraphAlgorithmsClass(copy_graph);
        return new_copy_graph;
    }

    @Override
    public boolean isConnected() {
        // create an array of the vertex we visited and fiil it with false
        boolean[] visited = new boolean[this.g.nodeSize()];
        int i;
        for(i = 0; i < this.g.nodeSize(); i++)
        {
            visited[i] = false;
        }
        // using a function to see if we can get to every vertex from vertex 0
        dfs(0, visited);


        // if we didnt get to every vertex from vertex 0, we return false
        for(i = 0; i < this.g.nodeSize(); i++)
        {
            if(!visited[i]) {
                return false;
            }
        }

        // we create a reverse graph
        DirectedWeightedGraphAlgorithmsClass gr = reverseGraph();
        // we fill the visited array with false, because we now check the reverse graph
        // so we start from the beginning.
        for(i = 0; i < this.g.nodeSize(); i++)
        {
            visited[i] = false;
        }

        // we now check if vertex 0 can reach every node in this reverse graph
        gr.dfs(0, visited);

        // if we didnt reach a vertex, return false
        for(i = 0; i < this.g.nodeSize(); i++)
        {
            if(!visited[i]) {
                return false;
            }
        }

        return true;
    }



    @Override
    public double shortestPathDist(int src, int dest) {
        if (!dijkstra_map.containsKey(src)){
            dijkstra_map.put(src,dijkstra(src));
        }
        return dijkstra_map.get(src).get(dest).get("total_w");
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        ArrayList<NodeData> ans = new ArrayList<>();
        if (!dijkstra_map.containsKey(src)){
            dijkstra_map.put(src,dijkstra(src));
        }
        ans.add(g.getNode(dest));
        NodeData current_n = g.getNode((dijkstra_map.get(src).get(dest).get("previous").intValue()));
        ans.add(0,current_n);
        while (current_n.getKey() != src){
            current_n = g.getNode((dijkstra_map.get(src).get(current_n.getKey()).get("previous").intValue()));
            ans.add(0,current_n);
        }
        return ans;
    }

    public HashMap<Integer,HashMap<String,Double>> dijkstra(int src){
        HashMap<Integer,HashMap<String,Double>> dijkstra_table = new HashMap<>();
        Iterator<NodeData> n_iter = g.nodeIter();
        ArrayList<Integer> unvisited = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        while (n_iter.hasNext()){
            NodeData n = n_iter.next();
            System.out.print(n.getKey());
            System.out.print("\n");
            dijkstra_table.put(n.getKey(),new HashMap<>());
            if (n.getKey() == src){
                dijkstra_table.get(n.getKey()).put("total_w", (double) 0);
            }
            else{
                dijkstra_table.get(n.getKey()).put("total_w", Double.MAX_VALUE);
            }
            dijkstra_table.get(n.getKey()).put("previous", null);
            unvisited.add(n.getKey());
        }
        while (!unvisited.isEmpty()){
            int shortest_node_from_source = 0;
            double shortest_dist = Double.MAX_VALUE;
            for (int i = 0; i < unvisited.size(); i++) {
                int key = (int)dijkstra_table.keySet().toArray()[unvisited.get(i)];
                if (dijkstra_table.get(key).get("total_w") < shortest_dist){
                    shortest_dist = dijkstra_table.get(key).get("total_w");
                    shortest_node_from_source = key;
                }
            }
            ArrayList<Integer> unvisited_neighbours = new ArrayList<>();
            Iterator<EdgeData> e_iter = g.edgeIter(shortest_node_from_source);
            HashMap<Integer,Double> dist_from_prev = new HashMap<>();
            while (e_iter.hasNext()){
                EdgeData e = e_iter.next();
                if (!visited.contains(e.getDest())){
                    unvisited_neighbours.add(e.getDest());
                    dist_from_prev.put(e.getDest(),e.getWeight());
                }
            }

            for (int i = 0; i < unvisited_neighbours.size(); i++) {
                int current_n = unvisited_neighbours.get(i);
                double new_dist = dijkstra_table.get(shortest_node_from_source).get("total_w") + dist_from_prev.get(current_n);
                if (new_dist < dijkstra_table.get(current_n).get("total_w")){
                    dijkstra_table.get(current_n).replace("total_w",new_dist);
                    dijkstra_table.get(current_n).replace("previous",(double)shortest_node_from_source);
                }
            }
            unvisited.remove((Object)shortest_node_from_source);
            visited.add(shortest_node_from_source);
        }
        return dijkstra_table;
    }

    @Override
    public NodeData center() {
        Iterator<NodeData> nodeiter = g.nodeIter();
        while (nodeiter.hasNext()){
            NodeData n = nodeiter.next();
            if (!dijkstra_map.containsKey(n.getKey())){
                dijkstra_map.put(n.getKey(),dijkstra(n.getKey()));
            }
        }
        int center = 0;
        double min_max_w = Double.MAX_VALUE;
        for (int i = 0; i < dijkstra_map.size(); i++) {
            int key = (int) dijkstra_map.keySet().toArray()[i];
            double max_w = 0;
            for (int j = 0; j < dijkstra_map.get(key).size(); j++) {
                int current_key = (int)dijkstra_map.get(key).keySet().toArray()[j];
                if (max_w < dijkstra_map.get(key).get(current_key).get("total_w")){
                    max_w = dijkstra_map.get(key).get(current_key).get("total_w");
                }
            }
            if (max_w < min_max_w){
                center = key;
                min_max_w = max_w;
            }
        }
        return g.getNode(center);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        Iterator<NodeData> nodeiter = g.nodeIter();
        while (nodeiter.hasNext()){
            NodeData n = nodeiter.next();
            if (!dijkstra_map.containsKey(n.getKey())){
                dijkstra_map.put(n.getKey(),dijkstra(n.getKey()));
            }
        }
        List<NodeData> quickest_path = new ArrayList<>();
        double smallest_w = Double.MAX_VALUE;
        for (int i = 0; i < cities.size()-1; i++) {
            for (int j = i+1; j < cities.size(); j++) {
                List<NodeData> best_path = shortestPath(cities.get(i).getKey(),cities.get(j).getKey());
                if (best_path.containsAll(cities)){
                    double path_w = dijkstra_map.get(cities.get(i).getKey()).get(cities.get(j).getKey()).get("total_w");
                    if (path_w < smallest_w){
                        smallest_w = path_w;
                        quickest_path = new ArrayList<>(best_path);
                    }
                }
            }
        }
        if (quickest_path.size() == 0){
            List<NodeData> absolute_quickest_path = new ArrayList<>();
            double absolute_w = Double.MAX_VALUE;
            List<NodeData> visited = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                absolute_w = Double.MAX_VALUE;
                visited = new ArrayList<>();
                NodeData start = cities.get(i);
                quickest_path = new ArrayList<>();
                quickest_path.add(start);
                visited.add(start);
                double path_w = 0;
                NodeData current_n = start;
                for (int j = 0; j < cities.size(); j++) {
                    NodeData closet_one = null;
                    double w = Double.MAX_VALUE;
                    for (int l = 0; l < cities.size(); l++) {
                        double current_w = dijkstra_map.get(current_n.getKey()).get(cities.get(l).getKey()).get("total_w");
                        if (!visited.contains(cities.get(l)) && current_w < w){
                            closet_one = cities.get(l);
                            w = current_w;
                        }
                    }
                    if (closet_one != null){
                        visited.add(closet_one);
                        List<NodeData> path = shortestPath(current_n.getKey(),closet_one.getKey());
                        path = path.subList(1,path.size());
                        quickest_path.addAll(path);
                        path_w += w;
                        current_n = closet_one;
                    }
                }
                if (path_w < absolute_w){
                    absolute_w = path_w;
                    absolute_quickest_path = new ArrayList<>(quickest_path);
                }
            }
            quickest_path = new ArrayList<>(absolute_quickest_path);
        }
        if (quickest_path.size() == 0){
            return null;
        }
        Collections.reverse(quickest_path);
        return quickest_path;
    }

    @Override
    public boolean save(String file) {
        //        take the iterators
        JSONObject obj = new JSONObject();
        JSONArray edges_array = new JSONArray();
        JSONArray nodes_array = new JSONArray();
        FileWriter file1 = null;


        Iterator<NodeData> ite_n = this.g.nodeIter();
        Iterator<EdgeData> ite_e = this.g.edgeIter();

        while(ite_e.hasNext())
        {
            EdgeDataClass next = (EdgeDataClass) ite_e.next();
            JSONObject e1 = new JSONObject();
            e1.put("src", next.getSrc());
            e1.put("w", next.getWeight());
            e1.put("dest", next.getDest());
            edges_array.add(e1);
        }
        obj.put("Edges", edges_array);

        while(ite_n.hasNext())
        {
            NodeDataClass next1 = (NodeDataClass) ite_n.next();
            JSONObject n1 = new JSONObject();
            GeoLocation p1 = next1.getLocation();
            String pos = String.valueOf(p1.x()) + "," + String.valueOf(p1.y()) + "," + String.valueOf(p1.z());
            n1.put("pos", pos);
            n1.put("id", next1.getKey());
            nodes_array.add(n1);
        }

        obj.put("Nodes", nodes_array);

        try {

            // Constructs a FileWriter given a file name, using the platform's default charset
            file1 = new FileWriter(file);
            file1.write(obj.toJSONString());
        }
        catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file1.flush();
                file1.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return false;
    }

    @Override
    public boolean load(String file) {
        DirectedWeightedGraphClass ans = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            HashMap<String, ArrayList<HashMap<String, Double>>> h = new Gson().fromJson(obj.toString(), HashMap.class);

            for(Map.Entry v: h.entrySet()){
                for (int i = 0; i < h.get(v.getKey()).size();i++){
                    h.get(v.getKey()).set(i,new HashMap<String,Double>(h.get(v.getKey()).get(i)));
                }
            }
            ans = new DirectedWeightedGraphClass(h);
            this.g = ans;
            return true;
        }
        catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

    }
}

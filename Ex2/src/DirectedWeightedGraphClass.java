import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private HashMap<Double,NodeData> Nodes;
    private ArrayList<EdgeData> Edges;
    private HashMap<Integer, HashMap<Integer,EdgeData>> from_node;
    private HashMap<Integer, HashMap<Integer,EdgeData>> to_node;
    private int mc;

    public DirectedWeightedGraphClass(HashMap<Double,NodeData> nodes, ArrayList<EdgeData> edges)
    {
        this.mc = 0;
        this.Nodes = nodes;
        this.Edges = edges;

        this.from_node = new HashMap<>();
        this.to_node = new HashMap<>();
        for (int i = 0; i < Nodes.size();i++){
            from_node.put((Double.valueOf(String.valueOf(Nodes.keySet().toArray()[i]))).intValue(), new HashMap<>());
            to_node.put((Double.valueOf(String.valueOf(Nodes.keySet().toArray()[i]))).intValue(), new HashMap<>());
        }
        for(EdgeData e: Edges){
            from_node.get(e.getSrc()).put(e.getDest(),e);
            to_node.get(e.getDest()).put(e.getSrc(),e);
        }
    }

    public DirectedWeightedGraphClass(HashMap<String, ArrayList<HashMap<String,Double>>> h){
        mc = 0;
        Nodes = new HashMap<>();
        for(int i = 0; i < h.get("Nodes").size(); i++){
            String pos = String.valueOf(h.get("Nodes").get(i).get("pos"));
            double x = Double.parseDouble(pos.split(",")[0]);
            double y = Double.parseDouble(pos.split(",")[1]);
            double z = Double.parseDouble(pos.split(",")[2]);
            GeoLocation g = new GeoLocationClass(x,y,z);
            NodeData n = new NodeDataClass(h.get("Nodes").get(i).get("id"),g);
            Nodes.put(h.get("Nodes").get(i).get("id"),n);
        }
        Edges = new ArrayList<>();
        for (int i = 0; i < h.get("Edges").size(); i++){
            EdgeData e = new EdgeDataClass(h.get("Edges").get(i));
            Edges.add(e);
        }
        from_node = new HashMap<>();
        to_node = new HashMap<>();
        for (int i = 0; i < Nodes.size();i++){
            from_node.put((Double.valueOf(String.valueOf(Nodes.keySet().toArray()[i]))).intValue(), new HashMap<>());
            to_node.put((Double.valueOf(String.valueOf(Nodes.keySet().toArray()[i]))).intValue(), new HashMap<>());
        }
        for(EdgeData e: Edges){
            from_node.get(e.getSrc()).put(e.getDest(),e);
            to_node.get(e.getDest()).put(e.getSrc(),e);
        }
    }

    @Override
    public NodeData getNode(int key) {
        return Nodes.get((double)key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return from_node.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        Nodes.put((double)n.getKey(),n);
        mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (Nodes.get((double)src) != null && Nodes.get((double)dest) != null && w > 0){
            HashMap<String,Double> data = new HashMap();
            data.put("src", (double)src);
            data.put("dest", (double)dest);
            data.put("w", w);
            EdgeData e = new EdgeDataClass(data);
            Edges.add(e);
            from_node.get(e.getSrc()).put(e.getDest(),e);
            to_node.get(e.getDest()).put(e.getSrc(),e);
        }
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return Nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return Edges.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return from_node.get(node_id).values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        NodeData n = Nodes.remove((double)key);
        HashMap<Integer,EdgeData> e = from_node.get(key);
        for (Map.Entry edge: e.entrySet()){
            Edges.remove(edge.getValue());
        }
        from_node.remove(key);
        HashMap<Integer,EdgeData> e1 = to_node.get(key);
        for (Map.Entry edge: e1.entrySet()){
            Edges.remove(edge.getValue());
        }
        to_node.remove(key);
        for (Map.Entry node : from_node.entrySet()){
            from_node.get(node.getKey()).remove(key);
        }
        mc++;
        return n;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = from_node.get(src).remove(dest);
        to_node.get(dest).remove(src);
        Edges.remove(e);
        mc++;
        return e;
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return Edges.size();
    }

    @Override
    public int getMC() {
        return mc;
    }
}

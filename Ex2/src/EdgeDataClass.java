import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EdgeDataClass implements EdgeData{
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    public EdgeDataClass(HashMap<String, Double> edge1)
    {
        this.src = (edge1.get("src")).intValue();
        this.dest = (edge1.get("dest")).intValue();
        this.weight = edge1.get("w");
        this.info = "";
        this.tag = 0;
    }
    public EdgeDataClass(int src, double w, int dest)
    {
        this.src = src;
        this.weight = w;
        this.dest = dest;
        this.info = "";
        this.tag = 0;
    }



    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
            this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
             this.tag = t;
    }
}

import api.GeoLocation;
import api.NodeData;

import java.util.HashMap;

public class NodeDataClass implements NodeData {
    private int key;
    private GeoLocation pos;
    private double weight;
    private String info;
    private int tag;

    public NodeDataClass(double id, GeoLocation pos)
    {
        this.key = (int)id;
        this.pos = pos;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }




    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
         this.weight = w;
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

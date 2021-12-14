import static org.junit.jupiter.api.Assertions.*;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import java.awt.*;
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
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import api.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;

class DirectedWeightedGraphAlgorithmsClassTest {
    public DirectedWeightedGraphAlgorithmsClass graph1;
    public DirectedWeightedGraphAlgorithmsClass graph2;

    @Before

    public void setUp() {
        graph1 = new DirectedWeightedGraphAlgorithmsClass(null);
        graph1.load("C:\\Users\\alonm\\IdeaProjects\\Ex2\\data\\G1.json");
    }

    public void setUp1() {
        graph2 = new DirectedWeightedGraphAlgorithmsClass(null);
        graph2.load("C:\\Users\\alonm\\IdeaProjects\\Ex2\\data\\G2.json");
    }


    @org.junit.jupiter.api.Test
    void copy() {
        // test 1 - for G1
        setUp();
        DirectedWeightedGraphClass g1 = (DirectedWeightedGraphClass) graph1.copy();
        Iterator<EdgeData> e1 = graph1.getGraph().edgeIter();
        Iterator<EdgeData> e2 = g1.edgeIter();
        while(e1.hasNext())
        {
            EdgeDataClass new_e1 = (EdgeDataClass) e1.next();
            EdgeDataClass new_e2 = (EdgeDataClass) e2.next();
            assertEquals(new_e1.getDest(), new_e1.getDest());
            assertEquals(new_e1.getSrc(), new_e1.getSrc());
            assertEquals(new_e1.getWeight(), new_e2.getWeight());
        }
        while(e2.hasNext())
        {
            EdgeDataClass new_e1 = (EdgeDataClass) e1.next();
            EdgeDataClass new_e2 = (EdgeDataClass) e2.next();
            assertEquals(new_e1.getDest(), new_e1.getDest());
            assertEquals(new_e1.getSrc(), new_e1.getSrc());
            assertEquals(new_e1.getWeight(), new_e2.getWeight());
        }


        Iterator<NodeData> n1 = graph1.getGraph().nodeIter();
        Iterator<NodeData> n2 = g1.nodeIter();

        while(n1.hasNext())
        {
            NodeDataClass new_n1 = (NodeDataClass) n1.next();
            NodeDataClass new_n2 = (NodeDataClass) n2.next();
            assertEquals(new_n1.getKey(),new_n2.getKey());
            assertEquals(new_n1.getLocation().x(), new_n2.getLocation().x());
            assertEquals(new_n1.getLocation().y(), new_n2.getLocation().y());
            assertEquals(new_n1.getLocation().z(), new_n2.getLocation().z());
        }
        while(n2.hasNext())
        {
            NodeDataClass new_n1 = (NodeDataClass) n1.next();
            NodeDataClass new_n2 = (NodeDataClass) n2.next();
            assertEquals(new_n1.getKey(),new_n2.getKey());
            assertEquals(new_n1.getLocation().x(), new_n2.getLocation().x());
            assertEquals(new_n1.getLocation().y(), new_n2.getLocation().y());
            assertEquals(new_n1.getLocation().z(), new_n2.getLocation().z());
        }




        // test 2 - for G2
        //
        //
        setUp1();
        g1 = (DirectedWeightedGraphClass) graph2.copy();
        e1 = graph2.getGraph().edgeIter();
        e2 = g1.edgeIter();
        while(e1.hasNext())
        {
            EdgeDataClass new_e1 = (EdgeDataClass) e1.next();
            EdgeDataClass new_e2 = (EdgeDataClass) e2.next();
            assertEquals(new_e1.getDest(), new_e1.getDest());
            assertEquals(new_e1.getSrc(), new_e1.getSrc());
            assertEquals(new_e1.getWeight(), new_e2.getWeight());
        }
        while(e2.hasNext())
        {
            EdgeDataClass new_e1 = (EdgeDataClass) e1.next();
            EdgeDataClass new_e2 = (EdgeDataClass) e2.next();
            assertEquals(new_e1.getDest(), new_e1.getDest());
            assertEquals(new_e1.getSrc(), new_e1.getSrc());
            assertEquals(new_e1.getWeight(), new_e2.getWeight());
        }


        n1 = graph2.getGraph().nodeIter();
        n2 = g1.nodeIter();

        while(n1.hasNext())
        {
            NodeDataClass new_n1 = (NodeDataClass) n1.next();
            NodeDataClass new_n2 = (NodeDataClass) n2.next();
            assertEquals(new_n1.getKey(),new_n2.getKey());
            assertEquals(new_n1.getLocation().x(), new_n2.getLocation().x());
            assertEquals(new_n1.getLocation().y(), new_n2.getLocation().y());
            assertEquals(new_n1.getLocation().z(), new_n2.getLocation().z());
        }
        while(n2.hasNext())
        {
            NodeDataClass new_n1 = (NodeDataClass) n1.next();
            NodeDataClass new_n2 = (NodeDataClass) n2.next();
            assertEquals(new_n1.getKey(),new_n2.getKey());
            assertEquals(new_n1.getLocation().x(), new_n2.getLocation().x());
            assertEquals(new_n1.getLocation().y(), new_n2.getLocation().y());
            assertEquals(new_n1.getLocation().z(), new_n2.getLocation().z());
        }


    }

    @org.junit.jupiter.api.Test
    void isConnected() {
        // test1 - for G1
        setUp();
        boolean flag = graph1.isConnected();
        Assertions.assertEquals(flag, true);

        //test2 - for G2
        setUp1();
        flag = graph2.isConnected();
        Assertions.assertEquals(flag, true);

    }

    @org.junit.jupiter.api.Test
    void shortestPathDist() {
        // test - for G1
        setUp();
        double dis1 = graph1.shortestPathDist(0,16);
        double test1 = graph1.getGraph().getEdge(0,16).getWeight();
        assertEquals(dis1, test1);

        double dis2 = graph1.shortestPathDist(0,5);
        double test2 = 6.323938666501508;
        Assertions.assertEquals(dis2, test2);


        // test2 - for G2
        setUp1();
        dis1 = graph2.shortestPathDist(14,17);
        test1 = 0.7167080826867309;
        assertEquals(dis1, test1);

        dis2 = graph2.shortestPathDist(0,30);
        test2 = 7.043765863063326;
        assertEquals(dis2, test2);

        double dis3 = graph2.shortestPathDist(0, 18);
        double test3 = 6.3379990179844521;
        assertEquals(dis3, test3);


    }

    @org.junit.jupiter.api.Test
    void shortestPath() {
        setUp();
        // test1 - for G1
        List<NodeData> test1 = new ArrayList<>();
        test1.add(graph1.getGraph().getNode(0));
        test1.add(graph1.getGraph().getNode(1));
        test1.add(graph1.getGraph().getNode(2));
        test1.add(graph1.getGraph().getNode(6));
        test1.add(graph1.getGraph().getNode(5));

        ArrayList<NodeData> l1 = (ArrayList<NodeData>) graph1.shortestPath(0,5);
        for(int i = 0; i < test1.size(); i++)
        {
            assertEquals(l1.get(i),test1.get(i));
        }
        assertEquals(test1.size(), l1.size());



        List<NodeData> test2 = new ArrayList<>();
        test2.add(graph1.getGraph().getNode(1));
        test2.add(graph1.getGraph().getNode(0));
        test2.add(graph1.getGraph().getNode(16));
        test2.add(graph1.getGraph().getNode(15));

        ArrayList<NodeData> l2 = (ArrayList<NodeData>) graph1.shortestPath(1,15);
        for(int i = 0; i < test2.size(); i++)
        {
            assertEquals(l2.get(i),test2.get(i));
        }
        assertEquals(test2.size(), l2.size());

        // test2 - for G2
        //
        //
        setUp1();
        test1 = new ArrayList<>();
        test1.add(graph2.getGraph().getNode(0));
        test1.add(graph2.getGraph().getNode(16));
        test1.add(graph2.getGraph().getNode(15));
        test1.add(graph2.getGraph().getNode(14));
        test1.add(graph2.getGraph().getNode(13));
        test1.add(graph2.getGraph().getNode(30));

        l1 = (ArrayList<NodeData>) graph2.shortestPath(0,30);
        for(int i = 0; i < test1.size(); i++)
        {
            assertEquals(l1.get(i),test1.get(i));
        }
        assertEquals(test1.size(), l1.size());



        test2 = new ArrayList<>();
        test2.add(graph2.getGraph().getNode(1));
        test2.add(graph2.getGraph().getNode(26));
        test2.add(graph2.getGraph().getNode(8));
        test2.add(graph2.getGraph().getNode(9));
        test2.add(graph2.getGraph().getNode(10));
        test2.add(graph2.getGraph().getNode(11));
        test2.add(graph2.getGraph().getNode(20));
        test2.add(graph2.getGraph().getNode(30));

        l2 = (ArrayList<NodeData>) graph2.shortestPath(1,30);
        for(int i = 0; i < test2.size(); i++)
        {
            assertEquals(l2.get(i),test2.get(i));
        }
        assertEquals(test2.size(), l2.size());

    }

    @org.junit.jupiter.api.Test
    void center() {
        // test1 - for G1
        setUp();
        NodeDataClass n1 = (NodeDataClass) graph1.center();
        assertEquals(8, n1.getKey());

        // test2 = for G2
        setUp1();
        n1 = (NodeDataClass) graph2.center();
        assertEquals(0, n1.getKey());
    }

    @org.junit.jupiter.api.Test
    void tsp() {
        setUp();
        // test1
        List<NodeData> l1 = new ArrayList<>();

        l1.add(graph1.getGraph().getNode(0));
        l1.add(graph1.getGraph().getNode(16));
        l1.add(graph1.getGraph().getNode(1));

        // building the expected path in a list
        ArrayList<NodeData> test1 = new ArrayList<>();
        test1.add(graph1.getGraph().getNode(1));
        test1.add(graph1.getGraph().getNode(0));
        test1.add(graph1.getGraph().getNode(16));

        ArrayList<NodeData> value1 = (ArrayList<NodeData>) graph1.tsp(l1);
        for(int i = 0; i < test1.size(); i++)
        {
            assertEquals(test1.get(i), value1.get(i));
        }


        List<NodeData> l2 = new ArrayList<>();
        l2.add(graph1.getGraph().getNode(0));
        l2.add(graph1.getGraph().getNode(16));
        l2.add(graph1.getGraph().getNode(1));
        l2.add(graph1.getGraph().getNode(5));
        l2.add(graph1.getGraph().getNode(7));

        // building the expected path in a list
        ArrayList<NodeData> test2 = new ArrayList<>();
        test2.add(graph1.getGraph().getNode(16));
        test2.add(graph1.getGraph().getNode(0));
        test2.add(graph1.getGraph().getNode(1));
        test2.add(graph1.getGraph().getNode(2));
        test2.add(graph1.getGraph().getNode(6));
        test2.add(graph1.getGraph().getNode(5));
        test2.add(graph1.getGraph().getNode(6));
        test2.add(graph1.getGraph().getNode(7));

        ArrayList<NodeData> value2 = (ArrayList<NodeData>) graph1.tsp(l2);
        for(int i = 0; i < test2.size(); i++)
        {
            assertEquals(test2.get(i), value2.get(i));
        }


        // test2 - for G2
        //
        //

        setUp1();

        l1 = new ArrayList<>();

        l1.add(graph2.getGraph().getNode(0));
        l1.add(graph2.getGraph().getNode(30));
        l1.add(graph2.getGraph().getNode(5));
        l1.add(graph2.getGraph().getNode(18));

        // building the expected path in a list
        test1 = new ArrayList<>();
        test1.add(graph2.getGraph().getNode(5));
        test1.add(graph2.getGraph().getNode(6));
        test1.add(graph2.getGraph().getNode(2));
        test1.add(graph2.getGraph().getNode(1));
        test1.add(graph2.getGraph().getNode(0));
        test1.add(graph2.getGraph().getNode(16));
        test1.add(graph2.getGraph().getNode(15));
        test1.add(graph2.getGraph().getNode(14));
        test1.add(graph2.getGraph().getNode(13));
        test1.add(graph2.getGraph().getNode(30));
        test1.add(graph2.getGraph().getNode(20));
        test1.add(graph2.getGraph().getNode(18));


        value1 = (ArrayList<NodeData>) graph2.tsp(l1);
        for(int i = 0; i < test1.size(); i++)
        {
            assertEquals(test1.get(i), value1.get(i));
        }



        l2 = new ArrayList<>();
        l2.add(graph2.getGraph().getNode(0));
        l2.add(graph2.getGraph().getNode(5));
        l2.add(graph2.getGraph().getNode(10));
        l2.add(graph2.getGraph().getNode(15));
        l2.add(graph2.getGraph().getNode(20));
        l2.add(graph2.getGraph().getNode(25));
        l2.add(graph2.getGraph().getNode(30));

        // building the expected path in a list
        test2 = new ArrayList<>();
        test2.add(graph2.getGraph().getNode(5));
        test2.add(graph2.getGraph().getNode(6));
        test2.add(graph2.getGraph().getNode(2));
        test2.add(graph2.getGraph().getNode(1));
        test2.add(graph2.getGraph().getNode(0));
        test2.add(graph2.getGraph().getNode(16));
        test2.add(graph2.getGraph().getNode(15));
        test2.add(graph2.getGraph().getNode(16));
        test2.add(graph2.getGraph().getNode(0));
        test2.add(graph2.getGraph().getNode(1));
        test2.add(graph2.getGraph().getNode(26));
        test2.add(graph2.getGraph().getNode(25));
        test2.add(graph2.getGraph().getNode(8));
        test2.add(graph2.getGraph().getNode(9));
        test2.add(graph2.getGraph().getNode(10));
        test2.add(graph2.getGraph().getNode(11));
        test2.add(graph2.getGraph().getNode(20));
        test2.add(graph2.getGraph().getNode(30));

        value2 = (ArrayList<NodeData>) graph2.tsp(l2);
        for(int i = 0; i < test2.size(); i++)
        {
            assertEquals(test2.get(i), value2.get(i));
        }

    }
}
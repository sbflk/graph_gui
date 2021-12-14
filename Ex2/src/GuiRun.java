import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class GuiRun extends JFrame implements ActionListener {

    private DirectedWeightedGraphAlgorithmsClass algo;
    int x_size;
    int y_size;
    int center_key;//for center function
    ArrayList<NodeData> shortestpath;// for shortestpath function


    // all the buttons
    private Menu load;
    private Menu save;
    private MenuItem connect;
    private MenuItem removeNode;
    private MenuItem center;
    private MenuItem shortest_path_dist;
    private MenuItem shortest_path;
    private MenuItem is_connected;
    private MenuItem tsp;
    private JButton b;
    private JTextField textField;
    private JFrame f;
    private MenuItem removeEdge;
    private MenuItem load_b;
    private MenuItem save_b;

    private String command;//the current command name the user presses on

    public GuiRun(DirectedWeightedGraphAlgorithmsClass algo) {
        //setVisible(true);
        this.algo = algo;
        this.x_size = 800;
        this.y_size = 800;
        this.center_key = -1;
        this.shortestpath = new ArrayList<>();
        initFrame();
        addMenu();
        //paint(getGraphics());
    }

    private void initFrame() {
        this.setSize(x_size, y_size);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMenu() {
        MenuBar menubar = new MenuBar();
        load = new Menu("Load");
        save = new Menu("Save");
        Menu edit = new Menu("Edit");
        Menu run = new Menu("Run");
        menubar.add(load);
        menubar.add(save);
        menubar.add(edit);
        menubar.add(run);
        this.setMenuBar(menubar);

        load_b = new MenuItem("Load");
        load.add(load_b);
        save_b = new MenuItem("Save");
        save.add(save_b);
        connect = new MenuItem("Connect");
        removeNode = new MenuItem("Remove Node");
        removeEdge = new MenuItem("Remove Edge");
        edit.add(connect);
        edit.add(removeNode);
        edit.add(removeEdge);
        center = new MenuItem("Center");
        shortest_path = new MenuItem("Shortest Path");
        shortest_path_dist = new MenuItem("Shortest Path Dist");
        tsp = new MenuItem("Tsp");
        is_connected = new MenuItem(" Is Connected");
        run.add(center);
        run.add(shortest_path_dist);
        run.add(shortest_path);
        run.add(tsp);
        run.add(is_connected);
        b = new JButton("Submit");
        b.setBounds(50, 150, 100, 30);

        load_b.addActionListener(this);
        save_b.addActionListener(this);
        connect.addActionListener(this);
        removeNode.addActionListener(this);
        removeEdge.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        center.addActionListener(this);
        is_connected.addActionListener(this);
        shortest_path.addActionListener(this);
        shortest_path_dist.addActionListener(this);
        tsp.addActionListener(this);
        b.addActionListener(this);

    }


    public void paint(Graphics g){
        g.setColor(Color.RED);
        Iterator<NodeData> iter = algo.getGraph().nodeIter();
        NodeData n = null;
        double max_y = 0;
        double min_y = Double.MAX_VALUE;
        double max_x = 0;
        double min_x = Double.MAX_VALUE;
        while (iter.hasNext()){
            n = iter.next();
            if (max_x < (n.getLocation().x())){
                max_x = (n.getLocation().x());
            }
            else if (min_x > (n.getLocation().x())){
                min_x = (n.getLocation().x());
            }
            if (max_y < (n.getLocation().y())){
                max_y = (n.getLocation().y());
            }
            else if (min_y > (n.getLocation().y())){
                min_y = (n.getLocation().y());
            }
        }
        min_x -= 0.000625;
        min_y -= 0.000625;
        max_x += 0.000625;
        max_y += 0.000625;
        Iterator<NodeData> iter1 = algo.getGraph().nodeIter();
        NodeData n1;
        while (iter1.hasNext()) {
            n1 = iter1.next();
            double x = (n1.getLocation().x()-min_x) /(max_x - min_x);
            double y = (n1.getLocation().y()-min_y) /(max_y - min_y);
            int final_x = (int) (x*x_size);
            int final_y = (int) (y*y_size);
            if (n1.getKey() == center_key){
                g.fillOval(final_x, final_y,10,10);
            }
            else if (shortestpath.contains(n1)){
                g.setColor(Color.GREEN);
                g.fillOval(final_x, final_y,10,10);
                g.setColor(Color.red);
            }
            else{
                g.drawOval(final_x, final_y,10,10);
            }
            Iterator<EdgeData> node_edges = algo.getGraph().edgeIter(n1.getKey());
            EdgeData e;
            while (node_edges.hasNext()){
                e = node_edges.next();
                NodeData dest = algo.getGraph().getNode(e.getDest());
                double dest_x = (dest.getLocation().x()-min_x) /(max_x - min_x);
                double dest_y = (dest.getLocation().y()-min_y) /(max_y - min_y);
                int dest_final_x = (int) (dest_x*x_size);
                int dest_final_y = (int) (dest_y*y_size);
                draw_arrow(g, final_x + 5,final_y + 5,dest_final_x + 5,dest_final_y + 5, 6,5);
                g.setColor(Color.RED);
            }
        }
    }

    public void draw_arrow(Graphics g, int x1, int y1, int x2, int y2, int d, int h){
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        g.drawLine(x1,y1,x2,y2);
        g.setColor(Color.BLACK);
        g.fillPolygon(xpoints,ypoints,3);

    }


    public void create_text_box(String message){
        f = new JFrame();
        f.setSize(390, 300);
        f.setLocation(100, 150);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setDefaultLookAndFeelDecorated(true);
        JLabel labelM = new JLabel(message);
        labelM.setBounds(50, 50, 400, 30);
        textField = new JTextField();
        textField.setBounds(50, 100, 200, 30);
        f.add(labelM);
        f.add(textField);
        f.setLayout(null);
        f.setVisible(true);
        f.add(b);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connect){
            if (f != null){
                f.dispose();
            }
            command = "connect";
            create_text_box("Enter in this format:src,dest,weight");
        }
        if (e.getSource() == removeNode){
            if (f != null){
                f.dispose();
            }
            command = "removeNode";
            create_text_box("Enter Node key");
        }
        if (e.getSource() == removeEdge){
            if (f != null){
                f.dispose();
            }
            command = "removeEdge";
            create_text_box("Enter in this format:src,dest");
        }
        if (e.getSource() == center){
            if (f != null){
                f.dispose();
            }
            command = "center";
        }
        if (e.getSource() == shortest_path){
            if (f != null){
                f.dispose();
            }
            command = "shortestPath";
            create_text_box("Enter in this format:src,dest");
        }
        if (e.getSource() == shortest_path_dist){
            if (f != null){
                f.dispose();
            }
            command = "shortestPathDist";
            create_text_box("Enter in this format:src,dest");
        }
        if (e.getSource() == tsp){
            if (f != null){
                f.dispose();
            }
            command = "tsp";
            create_text_box("Enter keys of cities in this format:k1,k2,k3...");
        }
        if (e.getSource() == is_connected){
            if (f != null){
                f.dispose();
            }
            command = "is_connected";
        }
        if (e.getSource() == load_b){
            if (f != null){
                f.dispose();
            }
            command = "load";
            create_text_box("Enter file to load");
        }
        if (e.getSource() == save_b){
            if (f != null){
                f.dispose();
            }
            command = "save";
            create_text_box("Enter file to save");
        }
        if (e.getSource() == b) {
            f.dispose();
            String c = textField.getText();
            if (command == "connect"){
                int src = Integer.parseInt(c.split("\\,")[0]);
                int dest = Integer.parseInt(c.split("\\,")[1]);
                double w = Double.parseDouble(c.split("\\,")[2]);
                algo.getGraph().connect(src,dest,w);
                paint(getGraphics());
            }
            else if (command == "load"){
                boolean happend = algo.load(c);
                if (!happend){
                    createButton("File doesn't exist.");
                }
                else{
                    paint(getGraphics());
                }
            }
            else if (command == "save"){
                algo.save(c);
                createButton("Saved!");
            }
            else if (command == "removeNode"){
                int key = Integer.parseInt(c);
                algo.getGraph().removeNode(key);
                paintComponents(getGraphics());
                paint(getGraphics());
            }
            else if (command == "removeEdge"){
                int src = Integer.parseInt(c.split("\\,")[0]);
                int dest = Integer.parseInt(c.split("\\,")[1]);
                algo.getGraph().removeEdge(src,dest);
                paintComponents(getGraphics());
                paint(getGraphics());
            }
            else if (command == "shortestPath"){
                int src = Integer.parseInt(c.split("\\,")[0]);
                int dest = Integer.parseInt(c.split("\\,")[1]);
                shortestpath = (ArrayList<NodeData>) algo.shortestPath(src,dest);
                String s = "the shortest path is: ";
                for (int i = 0; i < shortestpath.size(); i++) {
                    s = s.concat(shortestpath.get(i).getKey() + ", ");
                }
                s = s.concat("it is colored in green in the graph!");
                createButton(s);
                paint(getGraphics());
            }
            else if (command == "shortestPathDist"){
                int src = Integer.parseInt(c.split("\\,")[0]);
                int dest = Integer.parseInt(c.split("\\,")[1]);
                double dist = algo.shortestPathDist(src,dest);
                createButton("The shortest path dist is: " + dist);
                command = "";

            }
            else if (command == "tsp"){
                String[] k = c.split("\\,");
                ArrayList<Integer> keys = new ArrayList<>();
                for (int i = 0; i < k.length; i++) {
                    keys.add(Integer.parseInt(k[i]));
                }
                ArrayList<NodeData> nodes = new ArrayList<>();
                Iterator<NodeData> nodeiter = algo.getGraph().nodeIter();
                NodeData n;
                while (nodeiter.hasNext()){
                    n = nodeiter.next();
                    if (keys.contains(n.getKey())){
                        nodes.add(n);
                    }
                }
                ArrayList<NodeData> ans = (ArrayList<NodeData>) algo.tsp(nodes);
                String s = "The path is: ";
                for (int i = 0; i < ans.size(); i++) {
                    s = s.concat(ans.get(i).getKey() + ", ");
                }
                createButton(s);
            }
            command = "";
        }
        if (command == "center"){
            center_key = algo.center().getKey();
            paint(getGraphics());
            createButton("The center is node number: " + center_key+"! it's colored in red in the graph!");
            command = "";
        }
        if(command == "is_connected"){
            String s = "";
            if (algo.isConnected()){
                s = "The graph is connected!";
            }
            else {
                s = "The graph is not connected";
            }
            createButton(s);
            command = "";
        }
    }

    public void createButton(String s){
        final JFrame parent = new JFrame();
        JButton button = new JButton();
        button.setText(s);
        parent.setBounds(50, 100, 200, 30);
        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parent.dispose();
            }
        });
    }
}
Readme – object oriented graph

The problem:
We have a text file in json format that the data it’s represent a graph, with a list of edges and nodes. We need to transform the graph to a code format, visualize it, and edit it in various ways.

The solution/planning:
We will take the data written in the text file and transform it to objects, so we can work with it more conveniently. 

EdgeDataClass:
A class to represent a singular edge between 2 nodes, and will implement the EdgeData interface.
As an object that represent an edge, it will need to have attributes that represent an edge in a graph.
An edge has a direction, and it comes out of a node, and goes into a node. In addition an edge has length/weight.
EdgeDataClass object will have the attributes of: src – the source of the edge,
dest – the destination of the edge, and w – the weight/length of the edge.

GeoLocationClass:
 A class to represent the location of a node, and will implement the GeoLocation interface.
As an object that represent a location, it will to have attributes to represent a location, such as where he is on the x, y, and z axis.
GeoLocationClass object will have the attributes of: x – the location of the node on the X axis, y – the location of the node on the Y axis,
and z – the location of the node in the Z axis.

NodeDataClass:
A class to represent a singular node in the graph, and will implement the NodeData interface.
As an object that represent a node, it will need to have an attribute to identify the specific node, 
an attribute/s of it’s location, and the attribute to represent it’s weight.
NodeDataClass object will have the attributes of: key – the id of the node(it’s number),
pos – a GeoLocationClass object to represent the location of the node in the graph, and w – the weight of the node.

DirectedWeightedGraphClass:
A class to represent a graph made of the edges and nodes in the text file, and will implement the DirectedWeightedGraph interface.

As an object that represent a graph, it will need to have attributes that represent a graph. A list of nodes, that we can get a node according to it’s id. 
A list of edges, and optionally a list of nodes that go to a specific node, and a list of nodes that go from a specific node.

DirectedWeightedGraphClass object will have the attributes of: Nodes – a HashMap of the key/id of the node, and the node himself,
as NodeDataClass object, Edges – an ArrayList of the edges, which they are as EdgeDataClass objects, to_node – an HashMap of the key/id of the node,
and the edge that go into that node, from_node - an HashMap of the key/id of the node, and the edge that go out of that node.




DirectedWeightedGraphAlgorithmsClass:
A class that’s used to perform algorithms on a graph, and implements the interface DirectedWeightedGraphAlgorithms.
All we need is a graph attribute, so we can run algorithms on it.
DirectedWeightedGraphAlgorithmsClass object will have the attribute of: g – a DirectedWeightedGraphClass object to act as the graph to perform algorithms on.


Testing:
How we will test the functions.
The functions we will work with will be from the DirectedWeightedGraphAlgorithms, meaning we will test the functions of this interface.
Such functions as isConnected and copy can only be tested in a single way for a graph(i.e. if the value is true or if the lists are identical).
Other function such as tsp and shortestPath we can test in multiple ways.
Those function we will test by giving them variables for an edge case, 
or a large variable(i.e. a list containing many NodeDataClass objects or  giving the src and dest between paths as the first and last Nodes in the graph).









Running of multiple graphs:

1,000:

copy:
0.039 seconds(39 milliseconds).

isConnected:
0.072 seconds(72 milliseconds).

shortestPathDist:
2.386 seconds(2386 milliseconds).
shortestPath:
2.343 seconds(2343 milliseconds).

center:
doesn’t return value in 5 minutes.

tsp:
doesn’t return value in 5 minutes.

load:
0.172 seconds(172 milliseconds).

save:
0.045 seconds(45 milliseconds).





10,000:

copy:
0.814 seconds(814 milliseconds).
isConnected:
1.256 seconds(1256 milliseconds).

shortestPathDist:
doesn’t return value in 5 minutes.

shortestPath:
doesn’t return value in 5 minutes.

center:
doesn’t return value in 5 minutes.

tsp:
doesn’t return value in 5 minutes.

load:
1.155 seconds(1155 milliseconds).

save:
0.211 seconds(211 milliseconds).





100,000:

copy:
1.542 seconds(1542 milliseconds).
isConnected:
2.012 seconds(2012 milliseconds).

shortestPathDist:
doesn’t return value in 5 minutes.

shortestPath:
doesn’t return value in 5 minutes.

center:
doesn’t return value in 5 minutes.

tsp:
doesn’t return value in 5 minutes.

load:
1.478 seconds(1478 milliseconds).

save:
0.401 seconds(401 milliseconds).






1,000,000:

copy:
2.112 seconds(2112 milliseconds).
isConnected:
2.734 seconds(2734 milliseconds).

shortestPathDist:
doesn’t return value in 5 minutes.

shortestPath:
doesn’t return value in 5 minutes.

center:
doesn’t return value in 5 minutes.

tsp:
doesn’t return value in 5 minutes.

load:
1.993 seconds(1993 milliseconds).

save:
1.011 seconds(1011 milliseconds).


Explanation of the GUI:
We have a gui to visualize the graph and to make interaction with the client.
With the gui we can interact with the graph, use algorithms on it, load another graph, and save the graph to a file.

In the gui, we have a menu, with four items: Load, Save, Edit, Run.
If your mouse hovers on one of this items, a menu corresponding to the menu item you are hovering over will appear.
In the Run menu item, there is a menu of all the algorithms that can be used on the algorithms, such as isConnected. If you press on one of the options, 
a window pops out, and directs you to give input in the right way. After the submit button is pressed,
the window closes and another open with the answer of the algorithms that was run.

In the Edit menu item, there is a menu of all the options/functions that can be used to change a graph. 
If you press on one of the options, a window pops out, and directs you to put input the right way. After the submit button is pressed, the graph is updated.

In the Save menu item, there is only one option to press, which is “Save”.
After the option is pressed, a window pops up, that asks you to give a file path to save the graph into,
but it’s possible to give a file name of a file that doesn’t exist, and it will create it.
If given an incorrect file name/path, the window shut down, and another pops up to notify the mistake. The graph is saved in a json format in a text file.
After that the program shuts down.

In the Load Save menu item, there is only one option to press, which is “Load”.
After the option is pressed, a window pops up, that asks you to give a file path to load the graph. 
If given an incorrect file name/path, the window shut down, and another pops up to notify the mistake. The graph is then loaded, and the program shuts down.

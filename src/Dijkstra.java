//Quinn Zipse Homework6 CS340
import java.io.*;
import java.util.*;

public class Dijkstra {
    private class Vertex{
        private EdgeNode edges1;
        private EdgeNode edges2;
        private boolean known;
        private int distance;
        private int previous;

            private Vertex(){
                edges1 = null;
                edges2 = null;
                known = false;
                distance = Integer.MAX_VALUE;
                previous = -1;
            }
    }

    private class EdgeNode {
        private int vertex1;
        private int vertex2;
        private EdgeNode next1;
        private EdgeNode next2;
        private int weight;

        private EdgeNode(int v1, int v2, EdgeNode e1, EdgeNode e2, int w){
            //PRE: v1 < v2
            vertex1 = v1;
            vertex2 = v2;
            next1 = e1;
            next2 = e2;
            weight = w;
        }
    }

    private Vertex[] g;

    public Dijkstra(int size){
        g = new Vertex[size];
        for(int i=0; i<size; i++){
            g[i] = new Vertex();
        }
    }

    public void addEdge(int v1, int v2, int w){
        //PRE: v1 and v2 are legitimate vertices
        //(i.e. 0<=v1 < g.length and 0 <= v2 < g.length)
        if( !(v1 < v2) ) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        var edge = new EdgeNode(v1, v2, g[v1].edges1, g[v2].edges2, w);

        g[v1].edges1 = edge;
        g[v2].edges2 = edge;

    }

    public void printRoutes(int j){
        // Find and print the best routes from j to all other nodes in the graph
        // Init a place to store distances
        DijkstraHeap p = new DijkstraHeap(g.length);

        // Start off by marking the origin node with distance 0.
        g[j].distance = 0;
        p.insert(j, 0);
        // repeat until the distance to all nodes is known
        while(p.getSize() != 0){
            // Choose a node that has the smallest distance and isn't known.
            Vertex node = g[p.getMinNode()];
            node.distance = p.getMinDistance();
            node.known = true;
            p.removeMin();

            // for each node adjacent to the current node...
            var edge = node.edges1;
            while(edge != null){
                // vertex1 should always be the same, use vertex2 to find the node it connects to.
                var adjNode = g[edge.vertex2];
                int weight = node.distance + edge.weight;
                if(weight < adjNode.distance){
                    if(adjNode.distance == Integer.MAX_VALUE) p.insert(edge.vertex2, weight);
                    else if(p.inserted(edge.vertex1)) p.decreaseKey(edge.vertex2, weight);
                    adjNode.distance = weight;
                    adjNode.previous = edge.vertex1;
                }
                edge = edge.next1;
            }
            edge = node.edges2;
            while(edge != null){
                // vertex2 should always be the same, use vertex1 to find the node it connects to.
                var adjNode = g[edge.vertex1];
                int weight = node.distance + edge.weight;
                if(weight < adjNode.distance){
                    if(adjNode.distance == Integer.MAX_VALUE) p.insert(edge.vertex1, weight);
                    else if(p.inserted(edge.vertex1)) p.decreaseKey(edge.vertex1, weight);
                    adjNode.distance = weight;
                    adjNode.previous = edge.vertex2;
                }
                edge = edge.next2;
            }
        }

        // Print time
        System.out.print("Best paths from node " + j + " to: ");
        for(int i=0; i<g.length; i++){
            var vertex = g[i];
            Stack<Integer> stack = new Stack<>();
            int distance = 0;
            while(vertex.previous != -1) {
                stack.push(vertex.previous);
                distance += vertex.distance;
                vertex = g[vertex.previous];
            }
            if(stack.isEmpty()) continue;
            System.out.print("\nNode " + i + ": ");
            while(!stack.isEmpty()){
                System.out.print( stack.pop() + ", " );
            }
            System.out.print(i + "  |  distance: " + distance);
        }

    }

    public static void main(String[] args) throws IOException{
        BufferedReader b = new BufferedReader(new FileReader(args[0]));
        String line = b.readLine();
        int numNodes = Integer.parseInt(line);
        line = b.readLine();
        int source = Integer.parseInt(line);
        Dijkstra g = new Dijkstra(numNodes);
        line = b.readLine();
        while(line != null){
            Scanner scan = new Scanner(line);
            g.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
            line = b.readLine();
        }
        g.printRoutes(source);
    }
}

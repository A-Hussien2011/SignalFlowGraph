package Structure;

import java.util.ArrayList;

public class Testing {
    public static void main(String args[]){
        ArrayList tmp1 = new ArrayList(2);
        INode x1 = new Node(1, tmp1);
        ArrayList tmp2 = new ArrayList(3);
        tmp2.add(4);
        INode x2 = new Node(2, tmp2);
        ArrayList tmp3 = new ArrayList(4);
        tmp3.add(2);
        INode x3 = new Node(3, tmp3);
        ArrayList tmp4 = new ArrayList(null);
        INode x4 = new Node(4, tmp4);
        ArrayList nodes = new ArrayList();
        nodes.add(x1);
        nodes.add(x2);
        nodes.add(x3);
        nodes.add(x4);

        Edge e1 = new Edge(x1,x2,1);
        Edge e2 = new Edge(x2,x3,1);
        Edge e3 = new Edge(x3,x4,1);
        Edge e4 = new Edge(x2,x4,1);
        Edge e5 = new Edge(x3,x2,-1);
        ArrayList edges = new ArrayList();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);

        Path sgf = new Path(x1,x4,nodes,edges);
    }
}

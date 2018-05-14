package Implementation;

import Structure.INode;

import java.util.ArrayList;

public class Testing {
    public static void main(String args[]) {
        ArrayList tmp1 = new ArrayList(2);
        ArrayList tmp2 = new ArrayList(1);
        ArrayList tmp3 = new ArrayList(2);
        ArrayList tmp4 = new ArrayList(1);
        INode x1 = new Node(1);
        INode x2 = new Node(2);
        INode x3 = new Node(3);
//        INode x4 = new Node(4);
//        INode x5 = new Node(5);
        tmp1.add(x2);
        tmp1.add(x1);
        tmp1.add(x3);
        tmp2.add(x3);
        tmp3.add(x2);
//        tmp3.add(x4);
//        tmp3.add(x5);
//        tmp4.add(x5);
        x1.setForwardReferences(tmp1);
        x2.setForwardReferences(tmp2);
        x3.setForwardReferences(tmp3);
//        x4.setForwardReferences(tmp4);

        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(x1);
        nodes.add(x2);
        nodes.add(x3);
        SignalFlowGraph sgf = new SignalFlowGraph();

//        ArrayList paths = sgf.getForwardPaths(x1,x3);
        ArrayList loops = sgf.getLoops(nodes);
        int x = 5;


    }
}

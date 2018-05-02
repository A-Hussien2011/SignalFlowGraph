package Structure;

import java.util.ArrayList;

public class Node implements INode {

    private int index ;
    private ArrayList<INode> forwardReferences ;

    public Node(int index, ArrayList<INode> forwardReferences) {
        this.index = index;
        this.forwardReferences = forwardReferences;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public ArrayList getForwardReferences() {
        return forwardReferences;
    }
}

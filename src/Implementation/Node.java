package Implementation;

import Structure.INode;

import java.util.ArrayList;

public class Node implements INode {

    private int index ;
    private ArrayList<INode> forwardReferences ;

    public Node(int index) {
        this.index = index;
        this.forwardReferences = new ArrayList<>();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public ArrayList getForwardReferences() {
        return forwardReferences;
    }
    @Override
    public INode addForwardReference(INode node){
        this.forwardReferences.add(node);
        return this;
    }
}

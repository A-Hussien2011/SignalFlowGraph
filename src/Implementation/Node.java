package Implementation;

import Structure.INode;

import java.util.ArrayList;

public class Node implements INode {

    private int index ;
    private ArrayList<INode> forwardReferences ;
    private boolean visited = false;
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }



    public Node(int index, ArrayList<INode> forwardReferences) {
        this.index = index;
        this.forwardReferences = forwardReferences;
    }
    public Node(int index){
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public ArrayList<INode> getForwardReferences() {
        return forwardReferences;
    }

    public void setForwardReferences(ArrayList<INode> forwardReferences) {
        this.forwardReferences = forwardReferences;
    }
}

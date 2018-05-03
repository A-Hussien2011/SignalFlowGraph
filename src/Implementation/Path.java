package Implementation;

import Structure.IEdge;
import Structure.INode;
import Structure.IPath;

import java.util.ArrayList;

public class Path implements IPath {

    private INode start ;
    private INode end ;
    private ArrayList<INode> nodes ;
    private ArrayList<IEdge> edges ;

    public Path(INode start, INode end, ArrayList<INode> nodes, ArrayList<IEdge> edges) {
        this.start = start;
        this.end = end;
        this.edges = edges;
        this.nodes = nodes;
    }

    public Path(INode start, INode end){
        this.start = start;
        this.end = end;
    }

    public  Path(){
        this.nodes = new ArrayList<INode>();
        this.edges = new ArrayList<IEdge>();
    }

    public int numberOfNodes() {
        return nodes.size();
    }

    public int numberOfEdges() {
        return edges.size();
    }

    public void setEdges(ArrayList<IEdge> edges) {
        this.edges = edges;
    }

    public ArrayList<INode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<INode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public INode getStart() {
        return start;
    }

    @Override
    public INode getEnd() {
        return end;
    }

    @Override
    public ArrayList getEdges() {
        return edges ;
    }

    public void addNode(INode node){
        nodes.add(node);
    }

    @Override
    public void removeNodeAt(int nodeIndex) {
        nodes.remove(nodeIndex);
    }

    public void setStart(INode start) {
        this.start = start;
    }

    public void setEnd(INode end) {
        this.end = end;
    }
}

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
    private float gain ;

    public Path(INode start, ArrayList<IEdge> edges) {
        this(start , start , edges);

    }

    private void setGain() {
        this.gain = 1;

        for (IEdge edge : edges) {
            this.gain *= edge.getGain();
        }

    }

    public Path(INode start, INode end, ArrayList<IEdge> edges) {
        this(start , end , new ArrayList<>() , edges);

    }

    private ArrayList<INode> addNodes(ArrayList<IEdge> edges) {
        this.nodes = new ArrayList<>();
        for (IEdge edge:edges) {
            nodes.add(edge.getEnd());
        }
        return nodes;
    }

    public Path(INode start, INode end, ArrayList<INode> nodes, ArrayList<IEdge> edges) {
        this.start = start;
        this.end = end;
        this.edges = edges;
        if(nodes.size() < 1){
            nodes = this.addNodes(edges);
        }
        this.nodes = nodes;
        setGain();
    }

    public  Path(){
        this(null , null , new ArrayList<INode>() , new ArrayList<IEdge>());
    }

    public ArrayList<INode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<INode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void addEdges(ArrayList<IEdge> allEdges) {
        this.edges = new ArrayList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            IEdge edge = getTheEdge(nodes.get(i), nodes.get(i + 1), allEdges);
            if (edge != null)
                this.edges.add(edge);
        }
        setGain();
    }

    private IEdge getTheEdge(INode iNode, INode iNode1, ArrayList<IEdge> allEdges) {
            for (IEdge edge : allEdges) {
                if (edge.getStart().equals(iNode) && edge.getEnd().equals(iNode1)) {
                    return edge;
                }
            }
            return null;

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
    public float getGain() {
        return this.gain;
    }

    @Override
    public ArrayList getEdges() {
        return edges ;
    }



    public void addNode(INode node){
        nodes.add(node);
    }

    public void setStart(INode start) {
        this.start = start;
    }

    public void setEnd(INode end) {
        this.end = end;
    }
}

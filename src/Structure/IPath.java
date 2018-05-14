package Structure;

import java.util.ArrayList;

public interface IPath {

    INode getStart();
    INode getEnd ();
    float getGain();
    ArrayList<INode> getNodes();
    ArrayList<IEdge> getEdges ();
    void addNode(INode node);
    void setEnd(INode end);
    void setStart(INode start);
    void setNodes(ArrayList<INode> nodes);


    void addEdges(ArrayList<IEdge> edges);
}
package Structure;

import java.util.ArrayList;

public interface IPath {

    INode getStart();
    INode getEnd ();
    int getGain();
    ArrayList<IEdge> getEdges ();
    ArrayList<INode> getNodes();
}

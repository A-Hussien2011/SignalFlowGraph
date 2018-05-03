package Structure;

import java.util.ArrayList;

public interface ISignalFlowGraph {
    // TODO type of transfer funcion
    String getTransferFunction(ArrayList<INode> Nodes , ArrayList edges);
    ArrayList<IPath> getForwardPaths (INode startNode, INode finalNode);
    ArrayList<IPath> getLoops(ArrayList<INode> nodes);

}

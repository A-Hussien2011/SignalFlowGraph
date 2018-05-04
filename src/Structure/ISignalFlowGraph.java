package Structure;

import java.util.ArrayList;

public interface ISignalFlowGraph {
    // TODO type of transfer funcion

    ArrayList<IPath> getForwardPaths (INode startNode, INode finalNode);
    ArrayList<IPath> getLoops(ArrayList<INode> nodes);
    int getTransferFunction(ArrayList<INode> Nodes , ArrayList edges);

}

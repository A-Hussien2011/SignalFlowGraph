package Structure;

import java.util.ArrayList;

public interface ISignalFlowGraph {
    // TODO type of transfer funcion


    float getTransferFunction(ArrayList<INode> Nodes, ArrayList edges);
    ArrayList<IPath> getLoops();
    ArrayList<IPath> getForwardPaths();
    ArrayList<Float> getDeltas();

}

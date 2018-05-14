package GUI;

import Implementation.SignalFlowGraph;
import Structure.IEdge;
import Structure.INode;
import Structure.ISignalFlowGraph;

import java.util.ArrayList;

public class Controller {
    public ISignalFlowGraph getSignalFlowGraph() {
        return signalFlowGraph;
    }

    private ISignalFlowGraph signalFlowGraph;


    public String getFunction(ArrayList<INode> nodes, ArrayList<IEdge> edges) {

        signalFlowGraph = new SignalFlowGraph();
        float transferFunction = signalFlowGraph.getTransferFunction(nodes, edges);
        return String.valueOf(transferFunction);

    }
}

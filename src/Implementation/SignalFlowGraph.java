package Implementation;

import Structure.IEdge;
import Structure.INode;
import Structure.IPath;
import Structure.ISignalFlowGraph;

import java.util.ArrayList;

public class SignalFlowGraph implements ISignalFlowGraph {

    private ArrayList<IPath> forwardPaths ;
    private ArrayList<IPath> loops ;
    private ArrayList<IEdge> edges ;
    private ArrayList<INode> nodes ;

    @Override
    public String getTransferFunction(ArrayList<INode>  Nodes, ArrayList edgesList) {
        return null;
    }

    private ArrayList<IPath> getForwardPaths (INode startNode, INode finalNode){
        return null ;
    }

    private ArrayList<IPath> getLoops(){
        for(INode currentNode : nodes){
            ArrayList<INode> forwardNodes = currentNode.getForwardReferences();
            for(INode nextNode: forwardNodes){
                if(nextNode.getIndex() < currentNode.getIndex()){
                    loops.addAll(getForwardPaths(nextNode, currentNode));
                }
            }
        }
        return  null ;
    }

    private ArrayList<ArrayList<IPath>> getUntouchedLoops (){
        return null;
    }

    private String getGain (IPath requiredPath){
        return  null;
    }

    private ArrayList<IPath> getPathLoops (IPath forwardPath ){
        return  null ;
    }
}

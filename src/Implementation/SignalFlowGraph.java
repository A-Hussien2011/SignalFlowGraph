package Implementation;

import Structure.IEdge;
import Structure.INode;
import Structure.IPath;
import Structure.ISignalFlowGraph;

import java.util.ArrayList;
import java.util.Stack;

public class SignalFlowGraph implements ISignalFlowGraph {

    public SignalFlowGraph(){
        forwardPaths = new ArrayList<>() ;
        loops = new ArrayList<>() ;

    }

    private ArrayList<IPath> forwardPaths;
    private int numberOfPaths = 1;
    private ArrayList<IPath> loops;
    private ArrayList<IEdge> edges ;
    private ArrayList<INode> nodes ;

    @Override
    public String getTransferFunction(ArrayList<INode>  Nodes, ArrayList edgesList) {
        return null;
    }

    public ArrayList<IPath> getForwardPaths (INode startNode, INode finalNode){
        getPaths(startNode, finalNode, new Stack());
        return forwardPaths ;
    }

    private void getPaths(INode start, INode end, Stack currentPath){
        start.setVisited(true);
        if(currentPath.isEmpty()) currentPath.push(start);
        if(start == end){
            IPath path = new Path();
            path.setNodes(new ArrayList<>(currentPath));
            path.setEnd(end);
            forwardPaths.add(path);
            return;
        }
        for(INode child : start.getForwardReferences()){
            if(!child.isVisited()){
                currentPath.push(child);
                getPaths(child, end, currentPath);
                currentPath.pop();
                child.setVisited(false);
            }
        }
    }

    public ArrayList<IPath> getLoops(ArrayList<INode> listOfNodes){
        for(INode currentNode : listOfNodes){
            ArrayList<INode> forwardNodes = currentNode.getForwardReferences();
            for(INode nextNode: forwardNodes){
                if(nextNode.getIndex() < currentNode.getIndex()){
                    loops.addAll(getForwardPaths(nextNode, currentNode));
                }
            }
        }
        return  loops;
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

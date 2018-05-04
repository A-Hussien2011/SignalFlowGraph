package Implementation;

import Structure.IEdge;
import Structure.INode;
import Structure.IPath;
import Structure.ISignalFlowGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
    public int getTransferFunction(ArrayList<INode>  Nodes, ArrayList edgesList) {
        int overAllTrasferFun =0;
        this.getForwardPaths(nodes.get(0) , nodes.get(nodes.size() - 1));
        this.getLoops(nodes);
        ArrayList<ArrayList<IPath>>[] untouchedLoops = this.getUntouchedLoops(loops);
        for (IPath path:forwardPaths) {
            ArrayList<IPath> pathLoops = this.getPathLoops(path);
            ArrayList<ArrayList<IPath>>[] pathUntouchedLoops = this.getUntouchedLoops(pathLoops);
            overAllTrasferFun += path.getGain() * this.getDelta(pathUntouchedLoops , pathLoops);
        }
        overAllTrasferFun /= this.getDelta(untouchedLoops , loops);
        return overAllTrasferFun ;
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

    public ArrayList<ArrayList<IPath>>[] getUntouchedLoops (ArrayList<IPath> paths){
        ArrayList[] untouchedLoops = new ArrayList[paths.size()];
        int iterations = (int) (Math.pow(2,paths.size()));
        boolean loopNotTouched = true ;
        Set touched = new HashSet();
        int binary = 3 ;

        do {
            loopNotTouched = true ;
            String binaryReperestation = Integer.toBinaryString(binary);
            ArrayList <Integer> arrayOfIndexes = new ArrayList<>();
            for (int index = binaryReperestation.indexOf('1'); index >= 0;
                 index = binaryReperestation.indexOf('1', index + 1)){
                arrayOfIndexes.add(binaryReperestation.length() -  1 - index);
            }
            touched.clear();
            for (int i = 0; i < arrayOfIndexes.size() && arrayOfIndexes.size() > 1 ; i++) {
               if(!insertPath(touched , paths.get(arrayOfIndexes.get(i)))){
                  loopNotTouched = false;
                  break;
               }
            }

            if (loopNotTouched && arrayOfIndexes.size() > 1){
                ArrayList arrayList1 = new ArrayList();
                for (int i = 0; i < arrayOfIndexes.size() ; i++) {
                    arrayList1.add(paths.get(arrayOfIndexes.get(i)));
                }

                if (untouchedLoops[arrayOfIndexes.size() - 2] == null)
                {
                    untouchedLoops[arrayOfIndexes.size() - 2] = new ArrayList();
                }
                untouchedLoops[arrayOfIndexes.size() - 2].add(arrayList1);
            }

            binary++;
        }while (binary < iterations);

        return untouchedLoops;
    }

    private boolean insertPath(Set touched, IPath path) {
        ArrayList<INode> nodes = path.getNodes();
        for (INode node : nodes){
            if(!touched.add(node)){
                return false ;
            }
        }
        return true ;
    }


    private int getGain (IPath requiredPath){
        int pathGain = 0 ;
        for (IEdge edge: requiredPath.getEdges()) {
            pathGain+= edge.getGain();
        }
        return  pathGain;
    }

    private ArrayList<IPath> getPathLoops (IPath forwardPath )
    {
        boolean unTouched ;
        Set pathNodes = new HashSet();
        ArrayList<IPath> untouchedLoops = new ArrayList<>();
        for (INode node:forwardPath.getNodes()) {
            pathNodes.add(node);
        }
        for (IPath loop:loops) {
            Set Nodes = new HashSet(pathNodes);
            unTouched = true;
            for (INode node:loop.getNodes()) {
                if(!Nodes.add(node)){
                    unTouched = false;
                    break;
                }
            }
            if(unTouched){
                untouchedLoops.add(loop);
            }
        }

        return  untouchedLoops ;
    }

    public int getDelta(ArrayList<ArrayList<IPath>>[] loops , ArrayList<IPath> allLoops){
        int loopsGain = 0;
        for (IPath path:allLoops) {
            loopsGain += path.getGain();
        }
            int delta = 1 - loopsGain;
            int sign = -1 ;
        for (int i = 0; i < loops.length ; i++) {
            if(loops[i] != null){
                ArrayList<ArrayList<IPath>> untouchedLoops = loops[i];
                int sum = 0;
                for (int j = 0; j < untouchedLoops.size() ; j++) {
                    int multiply = 1 ;
                    ArrayList<IPath> untouchedSet = untouchedLoops.get(j);
                    for (int k = 0; k < untouchedSet.size() ; k++) {
                        multiply *= untouchedSet.get(k).getGain();
                    }
                    sum += multiply ;
                }
                delta += (Math.pow(sign , i)) * sum ;
            }
        }
        return delta;
    }
}

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

    private ArrayList<IPath> loops;
    private ArrayList<IPath> forwardPaths;
    private ArrayList<INode> nodes;
    private ArrayList<IEdge> edges;
    private float overAllTrasferFun;
    public SignalFlowGraph() {
        loops = new ArrayList<>();

    }

    @Override
    public float getTransferFunction(ArrayList<INode> Nodes, ArrayList edgesList) {
        this.nodes = Nodes;
        this.edges = edgesList;
        overAllTrasferFun = 0;
        this.forwardPaths = this.getForwardPaths(Nodes.get(0), Nodes.get(Nodes.size() - 1));
        this.getLoops(Nodes);
        for (IPath path : forwardPaths) {
            ArrayList<IPath> pathLoops = this.getPathLoops(path);
            ArrayList<ArrayList<IPath>>[] pathUntouchedLoops = this.getUntouchedLoops(pathLoops);
            overAllTrasferFun += path.getGain() * this.getDelta(pathUntouchedLoops, pathLoops);
        }
        ArrayList<ArrayList<IPath>>[] untouchedLoops = this.getUntouchedLoops(loops);
        float delta = this.getDelta(untouchedLoops , loops);
        overAllTrasferFun /= this.getDelta(untouchedLoops, loops);
        return overAllTrasferFun;
    }

    public ArrayList<IPath> getForwardPaths (INode startNode, INode finalNode){
        ArrayList<IPath> forwardPaths = new ArrayList<>();
        getPaths(forwardPaths , startNode, finalNode, new Stack());
        return forwardPaths ;
    }
    private void getPaths(ArrayList<IPath> forwardPaths , INode start, INode end, Stack currentPath){
        start.setVisited(true);
        if(currentPath.isEmpty()) currentPath.push(start);
        if(start == end){
            IPath path = new Path();
            ArrayList<INode> pathNodes = new ArrayList<>(currentPath);
            path.setNodes(pathNodes );
            path.addEdges(this.edges);
            path.setStart(pathNodes.get(0));
            path.setEnd(end);
            forwardPaths.add(path);
            start.setVisited(false);
            return;
        }
        for(INode child : start.getForwardReferences()){
            if(!child.isVisited()){
                currentPath.push(child);
                getPaths(forwardPaths , child, end, currentPath);
                currentPath.pop();
                child.setVisited(false);
            }
        }
        start.setVisited(false);
    }

    public ArrayList<IPath> getLoops(ArrayList<INode> listOfNodes){
        for(INode currentNode : listOfNodes){
            ArrayList<INode> forwardNodes = currentNode.getForwardReferences();
            for(INode nextNode: forwardNodes){
                if(nextNode.getIndex() <= currentNode.getIndex()){
                    ArrayList<IPath> paths = getForwardPaths(nextNode, currentNode);
                    for (IPath path : paths) {
                        path.addNode(path.getStart());
                        path.addEdges(this.edges);
                    }
                    loops.addAll(paths);
                }
            }
        }
        return  loops;
    }


    public ArrayList<ArrayList<IPath>>[] getUntouchedLoops(ArrayList<IPath> paths) {
        if (paths.size() < 1) return null;
        ArrayList[] untouchedLoops = new ArrayList[paths.size()];
        int iterations = (int) (Math.pow(2, paths.size()));
        boolean loopNotTouched = true;
        Set touched = new HashSet();
        int binary = 3;

        while (binary < iterations){

            loopNotTouched = true;
            String binaryReperestation = Integer.toBinaryString(binary);
            ArrayList<Integer> arrayOfIndexes = new ArrayList<>();
            for (int index = binaryReperestation.indexOf('1'); index >= 0;
                 index = binaryReperestation.indexOf('1', index + 1)) {
                arrayOfIndexes.add(binaryReperestation.length() - 1 - index);
            }
            touched.clear();
            for (int i = 0; i < arrayOfIndexes.size() && arrayOfIndexes.size() > 1; i++) {
                if (!insertPath(touched, paths.get(arrayOfIndexes.get(i)))) {
                    loopNotTouched = false;
                    break;
                }
            }

            if (loopNotTouched && arrayOfIndexes.size() > 1) {
                ArrayList arrayList1 = new ArrayList();
                for (int i = 0; i < arrayOfIndexes.size(); i++) {
                    arrayList1.add(paths.get(arrayOfIndexes.get(i)));
                }

                if (untouchedLoops[arrayOfIndexes.size() - 2] == null) {
                    untouchedLoops[arrayOfIndexes.size() - 2] = new ArrayList();
                }
                untouchedLoops[arrayOfIndexes.size() - 2].add(arrayList1);
            }

            binary++;
        }

        return untouchedLoops;
    }

    private boolean insertPath(Set touched, IPath path) {
        ArrayList<INode> nodes = path.getNodes();
        for (int i = 0; i < nodes.size() - 1; i++) {
            if (!touched.add(nodes.get(i))) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<IPath> getPathLoops(IPath forwardPath) {
        boolean unTouched;
        Set pathNodes = new HashSet();
        ArrayList<IPath> untouchedLoops = new ArrayList<>();
        for (INode node : forwardPath.getNodes()) {
            pathNodes.add(node);
        }
        for (IPath loop : loops) {
            Set Nodes = new HashSet(pathNodes);
            unTouched = true;
            for (int i = 0 ; i < loop.getNodes().size() - 1 ; i++) {
                if (!Nodes.add(loop.getNodes().get(i))) {
                    unTouched = false;
                    break;
                }
            }
            if (unTouched) {
                untouchedLoops.add(loop);
            }
        }

        return untouchedLoops;
    }

    public float getDelta(ArrayList<ArrayList<IPath>>[] loops, ArrayList<IPath> allLoops) {
        if (allLoops.size() < 1) return 1;
        int loopsGain = 0;
        for (IPath path : allLoops) {
            loopsGain += path.getGain();
        }
        float delta = 1 - loopsGain;
        int sign = -1;
        for (int i = 0; i < loops.length; i++) {
            if (loops[i] != null) {
                ArrayList<ArrayList<IPath>> untouchedLoops = loops[i];
                int sum = 0;
                for (int j = 0; j < untouchedLoops.size(); j++) {
                    int multiply = 1;
                    ArrayList<IPath> untouchedSet = untouchedLoops.get(j);
                    for (int k = 0; k < untouchedSet.size(); k++) {
                        multiply *= untouchedSet.get(k).getGain();
                    }
                    sum += multiply;
                }
                delta += (Math.pow(sign, i)) * sum;
            }
        }
        return delta;
    }

    public ArrayList<IPath> getLoops() {
        return loops;
    }

    public ArrayList<IPath> getForwardPaths() {
        return forwardPaths;
    }

    public float getOverAllTrasferFun() {
        return overAllTrasferFun;
    }

}

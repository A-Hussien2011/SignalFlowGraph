package Structure;

public class Edge implements IEdge {

    private INode start ;
    private INode end ;
    private int gain ;

    public Edge(INode start, INode end, int gain) {
        this.start = start;
        this.end = end;
        this.gain = gain;
    }

    @Override
    public INode getStart() {
        return start;
    }

    @Override
    public INode getEnd() {
        return end;
    }

    @Override
    public int getGain() {
        return gain;
    }
}

package Structure;

import java.util.ArrayList;

public interface INode {

    int getIndex();

    ArrayList<INode> getForwardReferences();

    void setForwardReferences(ArrayList<INode> forwardReferences);

    boolean isVisited();

    void setVisited(boolean visited);

    INode addForwardReference(INode node);
}

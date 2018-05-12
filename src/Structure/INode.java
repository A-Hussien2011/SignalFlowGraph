package Structure;

import java.util.ArrayList;

public interface INode {

   int getIndex();
   ArrayList<INode> getForwardReferences() ;
   boolean isVisited();
   void setVisited(boolean visited);
   void setForwardReferences(ArrayList<INode> forwardReferences);
   INode  addForwardReference(INode node);
}

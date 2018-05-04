package Structure;

import java.util.ArrayList;

public interface INode {

   int getIndex();
   ArrayList getForwardReferences() ;
   INode  addForwardReference(INode node);
}

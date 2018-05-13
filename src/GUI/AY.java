package GUI;

import Implementation.Edge;
import Implementation.Node;
import Implementation.Path;
import Structure.IEdge;
import Structure.INode;
import Structure.IPath;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;


public class AY extends JFrame{
    private JPanel panel1;
    private JFormattedTextField addNodesFormattedTextField;
    private JButton btnNodes;
    private JPanel panelGraph;
    private JTextField txtStartNode;
    private JTextField txtEndNode;
    private JTextField txtWeight;
    private JButton btnAddEdge;
    private JButton btnFunction;
    private int numNodes = 0;
    ArrayList<INode> nodes;
    ArrayList<Object> graphNodes;
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    ArrayList<IEdge> edges = new ArrayList<>();
    Controller controller = new Controller();


    public AY() {
        nodes = new ArrayList<>();
        graphNodes = new ArrayList<>();
        this.DrawGraph();
        btnNodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numNodes ++;
                graphNodes.add(graph.insertVertex(parent, null, numNodes, (numNodes-1)*200, 200, 80,
                        30, mxConstants.SHAPE_CLOUD));
                INode x = new Node(numNodes);
                nodes.add(x);
            }

        });

        btnAddEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startNodeIndex = Integer.valueOf(txtStartNode.getText()) - 1;
                int endNodeIndex = Integer.valueOf(txtEndNode.getText()) - 1;
                int weight = Integer.valueOf(txtWeight.getText());
                if(endNodeIndex - startNodeIndex == 1)graph.insertEdge(parent, null, weight, graphNodes.get(startNodeIndex), graphNodes.get(endNodeIndex));
                if(endNodeIndex - startNodeIndex != 1)graph.insertEdge(parent, null, weight, graphNodes.get(startNodeIndex),
                        graphNodes.get(endNodeIndex), "ROUNDED;strokeColor=red;fillColor=green");

                INode startNode = nodes.get(startNodeIndex);
                INode endNode = nodes.get(endNodeIndex);
                startNode.getForwardReferences().add(endNode);
                IEdge edge = new Edge(startNode, endNode, weight);
                edges.add(edge);
            }
        });
        txtStartNode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtStartNode.setText("");
            }
        });txtEndNode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtEndNode.setText("");
            }
        });txtWeight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtWeight.setText("");
            }
        });
        btnFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, controller.getFunction(nodes, edges));

            }
        });
    }

    public void DrawGraph(){
        graph.getModel().beginUpdate();
        try{
//            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                    30);
//            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
//                    80, 30);
//            graph.insertEdge(parent, null, "Edge", v1, v2);
        }
        finally
        {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setBounds(0, 0, 300, 300);
        panelGraph.add(graphComponent);
    }

    public static void main(String[] args) {
        AY frame = new AY();
        frame.setContentPane(new AY().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

package GUI;

import Implementation.Edge;
import Implementation.Node;
import Structure.IEdge;
import Structure.INode;
import Structure.IPath;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class AY extends JFrame {
    ArrayList<INode> nodes;
    ArrayList<Object> graphNodes;
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    ArrayList<IEdge> edges = new ArrayList<>();
    Controller controller = new Controller();
    private JPanel panel1;
    private JFormattedTextField addNodesFormattedTextField;
    private JButton btnNodes;
    private JPanel panelGraph;
    private JTextField txtStartNode;
    private JTextField txtEndNode;
    private JTextField txtWeight;
    private JButton btnAddEdge;
    private JButton btnFunction;
    private JButton newGraphButton;
    private int numNodes = 0;
    private JPanel InformationPane1;
    private JTable forwardPathsTable;
    private JTable LoopsTable;


    public AY() {
        nodes = new ArrayList<>();
        graphNodes = new ArrayList<>();
        this.DrawGraph();
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CLOUD);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        style.put(mxConstants.STYLE_FILLCOLOR, "#4e72ad");
        style.put(mxConstants.STYLE_IMAGE_BORDER, "#774400");
        stylesheet.putCellStyle("ROUNDED", style);
        btnNodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numNodes++;
                graphNodes.add(graph.insertVertex(parent, null, numNodes, (numNodes - 1) * 200, 200, 80,
                        30, "ROUNDED"));
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
                Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
                style.put(mxConstants.STYLE_ROUNDED, true);
                style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW);
                if (endNodeIndex - startNodeIndex == 1)
                    graph.insertEdge(parent, null, weight, graphNodes.get(startNodeIndex), graphNodes.get(endNodeIndex));
                if (endNodeIndex - startNodeIndex != 1) {

                    graph.insertEdge(parent, null, weight, graphNodes.get(startNodeIndex),
                            graphNodes.get(endNodeIndex), "strokeColor=red;fillColor=green;");
                }

                INode startNode = nodes.get(startNodeIndex);
                INode endNode = nodes.get(endNodeIndex);
                startNode.getForwardReferences().add(endNode);
                IEdge edge = new Edge(startNode, endNode, weight);
                edges.add(edge);
            }
        });
        newGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.removeCells(graph.getChildCells(graph.getDefaultParent(), true, true));
                numNodes = 0;
                nodes = new ArrayList<INode>();
                graphNodes = new ArrayList<>();
                edges = new ArrayList<IEdge>();
            }
        });
        txtStartNode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtStartNode.setText("");
            }
        });
        txtEndNode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtEndNode.setText("");
            }
        });
        txtWeight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtWeight.setText("");
            }
        });
        btnFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String result =  controller.getFunction(nodes, edges);
                InformationPane1 = new JPanel();
                ArrayList<IPath> paths = controller.getSignalFlowGraph().getForwardPaths();
                ArrayList<IPath> loops = controller.getSignalFlowGraph().getLoops();
                Object[][] data1 = getData(paths);
                String[] columnNames1 = {"Forward Paths", "Gain"};
                forwardPathsTable = new JTable(data1, columnNames1);
                setJTableColumnsWidth(forwardPathsTable, 480, 10, 30, 30, 30);
                JScrollPane tableContainer1 = new JScrollPane(forwardPathsTable);
                String[] columnNames2 = {"Loops", "Gain"};
                Object[][] data2 = getData(loops);
                LoopsTable = new JTable(data2 , columnNames2);
                JScrollPane tableContainer2 = new JScrollPane(LoopsTable);
                JLabel label = new JLabel();
                ArrayList<Float> deltas = controller.getSignalFlowGraph().getDeltas() ;
                for (int i = 0; i < deltas.size() ; i++) {
                        label.setText(label.getText() + "\n" + "Delta" + String.valueOf(i) +" = " + String.valueOf(deltas.get(i)) );
                }
                InformationPane1.add(tableContainer1, BorderLayout.AFTER_LAST_LINE);
                InformationPane1.add(tableContainer2, BorderLayout.AFTER_LAST_LINE);
                InformationPane1.add(label , BorderLayout.CENTER);
                InformationPane1.add(new Label("\nTransfer Function = " + result));
                JOptionPane.showMessageDialog(null,InformationPane1  ,"Information",JOptionPane.INFORMATION_MESSAGE);
            }


        });

    }
    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
                                             double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    private Object[][] getData(ArrayList<IPath> paths) {
        Object[][] data = new Object[paths.size()][2];
        int i = 0 ;
        for (IPath path:paths) {
            String pathName = "";
            for (INode node:path.getNodes()) {
                pathName += String.valueOf(node.getIndex());
            }
            Object[] object = new Object[2];
            object[0] = pathName;
            object[1] = path.getGain();
            data[i] = object ;
            i++;
        }
        return data;
    }

    public static void main(String[] args) {
        AY frame = new AY();
        frame.setContentPane(new AY().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.pack();
        frame.setVisible(true);
    }

    public void DrawGraph() {
        graph.getModel().beginUpdate();
        try {
//            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                    30);
//            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
//                    80, 30);
//            graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setPageBackgroundColor(Color.GRAY);
        graphComponent.setBounds(0, 0, 300, 300);
        graphComponent.setPageBackgroundColor(Color.WHITE);
        panelGraph.add(graphComponent);
    }
}

import javax.swing.*;
import java.awt.*;
public class DijkstrasTable extends JFrame {
    //widgets used in the frame
    JPanel panel;
    JTable table;
    //construct the frame
    public DijkstrasTable() {
        panel = new JPanel();
        //create a table with 3 columns, and add all the information to the table.
        String[] columns = {
                "Vertex Name",
                "Order confirmed",
                "Minimum distance"
        };
        String[][] data = Window.dijkstrasTable;
        table = new JTable(data, columns);
        table.setRowHeight(60);
        //make the table scrollable.
        JScrollPane js = new JScrollPane(table);
        js.setSize(500, 150 * Window.vertexNames.size());
        panel.add(js);
        //label to describe the current process
        JLabel instruction = new JLabel("Hello there");
        Font f2 = new Font("SansSerif", Font.BOLD, 30);
        instruction.setFont(f2);
        panel.add(instruction);
        add(panel);
        setVisible(true);
        //set the size of the window based on how many vertices there are
        setSize(500, 100 * Window.vertexNames.size());
    }
}
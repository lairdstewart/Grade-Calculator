import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {
    JTabbedPane pane;
    JTextField addClass;
    JTextField delClass;

    MainFrame(){
        // initialize class objects
        addClass = new JTextField();

        // set up UI
        mainFrameUI();
    }

    private void mainFrameUI(){
        JFrame mainFrame = new JFrame("Grade Calculator");
        mainFrame.setSize(560, 600); // size in pixels
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close when press red x
        mainFrame.setLayout(new BorderLayout());

        mainFrame.add(mainPane(), BorderLayout.CENTER);
//        mainFrame.add(north(), BorderLayout.NORTH);

        mainFrame.setVisible(true);
    }

    private JPanel north(){
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        north.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));


        JButton add = new JButton("Add");
        JButton del = new JButton("Delete");
        addClass = new JTextField(10);
        delClass = new JTextField(10);
        add.addActionListener(this);
        del.addActionListener(this);

        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.setMaximumSize(new Dimension(120, 70));
        left.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        left.add(new JLabel("Add New Class: "), BorderLayout.NORTH);
        left.add(addClass, BorderLayout.CENTER);
        left.add(add, BorderLayout.SOUTH);

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.setMaximumSize(new Dimension(120, 70));
        right.add(new JLabel("Delete Class: "), BorderLayout.NORTH);
        right.add(delClass, BorderLayout.CENTER);
        right.add(del, BorderLayout.SOUTH);

        north.add(left);
        north.add(right);


        return north;
    }

    private JTabbedPane mainPane(){
        pane = new JTabbedPane();
        ClassTab econ231 = new ClassTab("Econ231");
        pane.addTab("Econ 231", econ231.newTabUI());

        ClassTab astronomy = new ClassTab("Astronomy");
        pane.addTab("Astronomy", astronomy.newTabUI());

        return pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
    }
}

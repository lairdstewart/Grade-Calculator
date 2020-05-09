import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculator implements ActionListener{
    private static JPanel basePanel;
    private static JFrame frame;
    private static AssignmentLine a1;
    private static AssignmentLine a2;
    private static AssignmentLine a3;
    private static AssignmentLine a4;
    private static AssignmentLine a5;
    private static AssignmentLine a6;
    private static AssignmentLine a7;
    private static AssignmentLine a8;
    private static  AssignmentLine a9;
    private static  AssignmentLine a10;
    private static  JLabel grade;
    private static String gradeString;
    private static double gradeDouble = 0.0;


    public static void main(String[] args){
        GradeCalculator gc = new GradeCalculator();
        gc.createUI();
    }

    GradeCalculator(){
    }

    private void createUI(){
        // FRAME
        frame = new JFrame(); // create frame to hold everything
        frame.setSize(300, 500); // size in pixels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close when press red x
        frame.setTitle("Grade Calculator"); // title

        // BASE PANEL
        basePanel = new JPanel(); // create base panel to hold other panels
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.PAGE_AXIS)); // set layout to vertical stack scroll
        basePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // border

        // add label for each column
        JLabel weightLabel = new JLabel("Weight", 2);
        JLabel gradeLabel = new JLabel("Grade", 4);
        JPanel labelPanel = new JPanel();
        labelPanel.add(weightLabel);
        labelPanel.add(gradeLabel);
        basePanel.add(labelPanel);

        // create assignment lines and add them to basePanel
        a1 = new AssignmentLine();
        a2 = new AssignmentLine();
        a3 = new AssignmentLine();
        a4 = new AssignmentLine();
        a5 = new AssignmentLine();
        a6 = new AssignmentLine();
        a7 = new AssignmentLine();
        a8 = new AssignmentLine();
        a9 = new AssignmentLine();
        a10 = new AssignmentLine();

        a1.createUI(basePanel);
        a2.createUI(basePanel);
        a3.createUI(basePanel);
        a4.createUI(basePanel);
        a5.createUI(basePanel);
        a6.createUI(basePanel);
        a7.createUI(basePanel);
        a8.createUI(basePanel);
        a9.createUI(basePanel);
        a10.createUI(basePanel);


        // create button on bottom with grade text, and add to new panel
        JPanel gradePanel = new JPanel();
        JButton calcGrade = new JButton("Calculate Grade");
        calcGrade.addActionListener(this);
        gradePanel.add(calcGrade);
        grade = new JLabel("--.--");
        gradePanel.add(grade);
        basePanel.add(gradePanel); // add the new panel to the base panel

        // add the base panel to the frame, and set it to visible
        frame.add(basePanel, BorderLayout.WEST);
        frame.setVisible(true);
    }


    private double calculate(){
        double total = 0;

        // grades
        double g1 = a1.getGrade();
        double g2 = a2.getGrade();
        double g3 = a3.getGrade();
        double g4 = a4.getGrade();
        double g5 = a5.getGrade();
        double g6 = a6.getGrade();
        double g7 = a7.getGrade();
        double g8 = a8.getGrade();
        double g9 = a9.getGrade();
        double g10 = a10.getGrade();

        // weights
        double w1 = a1.getWeight();
        double w2 = a2.getWeight();
        double w3 = a3.getWeight();
        double w4 = a4.getWeight();
        double w5 = a5.getWeight();
        double w6 = a6.getWeight();
        double w7 = a7.getWeight();
        double w8 = a8.getWeight();
        double w9 = a9.getWeight();
        double w10 = a10.getWeight();

        // sum up total grade
        if(g1 != -1 && w1 != -1){
            total = total + g1 * w1;
        }
        if(g2 != -1 && w2 != -1){
            total = total + g2 * w2;
        }
        if(g3 != -1 && w3 != -1){
            total = total + g3 * w3;
        }
        if(g4 != -1 && w4 != -1){
            total = total + g4 * w4;
        }
        if(g5 != -1 && w5 != -1){
            total = total + g5 * w5;
        }
        if(g6 != -1 && w6 != -1){
            total = total + g6 * w6;
        }
        if(g7 != -1 && w7 != -1){
            total = total + g7 * w7;
        }
        if(g8 != -1 && w8 != -1){
            total = total + g8 * w8;
        }
        if(g9 != -1 && w9 != -1){
            total = total + g9 * w9;
        }
        if(g10 != -1 && w10 != -1){
            total = total + g10 * w10;
        }

        return total * 100;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        String input = e.getActionCommand();
        if(input == "Calculate Grade"){
            String a = String.valueOf(calculate());
            if(a.length() > 5){
                gradeString = a.substring(0, 5) + "%";
            }else {
                gradeString = a + "%";
            }
            grade.setText(gradeString);
        }
    }


}

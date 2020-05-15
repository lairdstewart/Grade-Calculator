import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassTab implements ActionListener {
    private String className;
    private String weightsPath;
    private String assignmentsPath;

    private JTextField assignmentName;
    private JTextField assignmentGrade;
    private JTextField weightType;
    private JTextField weightValue;
    private JComboBox assignmentType;
    private String assignmentText;
    private String weightsText;
    private JTextArea assignmentListing;
    private JTextArea typeListing;
    private JLabel grade;

    private ArrayList<String[]> typeList = new ArrayList<String[]>(10);
    private ArrayList<String[]> assignmentList = new ArrayList<String[]>(20);

    ClassTab(String s){
        // assign all class variables
        className = s;
        assignmentName = new JTextField();
        assignmentGrade = new JTextField();
        weightType = new JTextField();
        weightValue = new JTextField();
        assignmentType = new JComboBox();
        assignmentText = "";
        weightsText = "";
        assignmentListing = new JTextArea();
        typeListing = new JTextArea();

        // set up text box
        assignmentType.addItem("----");

        // save pathname-s
        final File f = new File(ClassTab.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = f.getPath();
        weightsPath = path + "/" + className + "_weights";
        assignmentsPath = path + "/" + className + "_assignments";

        // load saved data
        load();

        // update grade label once we have loaded data
        grade = new JLabel(calculateGrade() + "%");
    }

    // GUI STUFF
    JPanel newTabUI(){
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        main.add(center(), BorderLayout.CENTER);
        main.add(south(), BorderLayout.SOUTH);
        main.add(west(), BorderLayout.WEST);

        return main;
    }

    private JPanel center(){
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // main text area
        assignmentListing.setText(assignmentText);

        // label above
        JTextField label = new JTextField();
        label.setEditable(false);
        label.setText("Name\tType\tGrade");
        label.setMaximumSize(new Dimension(300, 20));

        // add it to panel
        center.add(assignmentCreator());
        center.add(label);
        center.add(assignmentListing);
        return center;
    }

    private JPanel south(){
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
        south.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton calculate = new JButton("Calculate Grade");
        calculate.addActionListener(this);

        south.add(Box.createHorizontalGlue()); 
        south.add(calculate);
        south.add(grade);
        south.add(Box.createHorizontalGlue());

        return south;
    }

    private JPanel assignmentCreator(){
        JPanel assignmentCreator = new JPanel();
        assignmentCreator.setLayout(new BoxLayout(assignmentCreator, BoxLayout.Y_AXIS));

        JPanel main = new JPanel(); // main grid
        main.setLayout(new GridLayout(3, 2));
        JLabel name = new JLabel("Assignment Name: ");
        JLabel grade = new JLabel("Assignment Grade: ");
        JLabel type = new JLabel("Assignment Type: ");

        main.add(name);
        main.add(assignmentName);
        main.add(grade);
        main.add(assignmentGrade);
        main.add(type);
        main.add(assignmentType);
        main.setMaximumSize(new Dimension(275, 90));

        JButton create = new JButton("Create"); // button
        create.addActionListener(this);

        assignmentCreator.add(main);
        assignmentCreator.add(create);


        return assignmentCreator;
    }

    private JPanel west(){
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        // create button
        JButton add = new JButton("Add");
        add.addActionListener(this);

        // main grid with user input
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(2, 2));
        weightType = new JTextField();
        weightValue = new JTextField();
        main.add(new JLabel("Assignment type: "));
        main.add(weightType);
        main.add(new JLabel("Weight: "));
        main.add(weightValue);
        main.setMaximumSize(new Dimension(275, 55));

        // label for text box
        JTextField label = new JTextField();
        label.setEditable(false);
        label.setText("Type\tWeight");
        label.setMaximumSize(new Dimension(300, 20));

        // text box with assignment types and their weights
        typeListing.setText(weightsText);

        west.add(main);
        west.add(add);
        west.add(label);
        west.add(typeListing);

        return west;
    }

    // HANDLING USER INPUTS
    @Override
    public void actionPerformed(ActionEvent e) {
        updateText();
        String input = e.getActionCommand();
        if (input.equals("Create")) {
            addAssignment();
        }
        if (input.equals("Add")) {
            addWeight();
        }
        if (input.equals("New Class")) {
            newClass();
        }
        updateAll();
        if (input.equals("Calculate Grade")) {
            grade.setText(calculateGrade() + "%");
        }
        try {
            save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addAssignment(){
        // get user inputs
        String name = assignmentName.getText();
        String grade = convertGrade(assignmentGrade.getText())*100 + "%";
        String type = assignmentType.getSelectedItem().toString();        // if boxes are left empty, do nothing

        // if boxes are left empty, do nothing
        if(name.length() == 0 || grade.length() == 0 || type.length() == 0){
            return;
        }

        // add new text to text box
        assignmentText = assignmentText + name + "\t" + type + "\t"  + grade + "\n";
        assignmentListing.setText(assignmentText);

        // clear text entry box
        assignmentName.setText("");
        assignmentGrade.setText("");
        assignmentType.setSelectedItem("----");
    }

    private void addWeight(){
        // get assignment type and its weight value
        String type = weightType.getText();
        String value = weightValue.getText();
        String[] pair = {type, value};

        // if the boxes are empty do nothing
        if(type.length() == 0 || value.length() == 0){
            return;
        }

        // add to typeListing text box
        weightsText = weightsText + type + "\t" + value + "\n";
        typeListing.setText(weightsText);

        // clear input text box
        weightType.setText("");
        weightValue.setText("");
    }

    private void newClass(){}

    // UPDATES
    private void updateWeight(){
        // erase old list
        typeList = new ArrayList<String[]>();

        Scanner scan = new Scanner(weightsText);
        boolean type = true; // keep track if we are on a 'type' or a 'weight'
        String[] pair = new String[]{"", ""};
        while(scan.hasNext()){
            if(type){
                pair[0] = scan.next();
            }else{
                pair[1] = scan.next();
                typeList.add(pair);
                pair = new String[]{"", ""}; // clear it again
            }
            type = !type;
        }
    }

    private void updateAssignments(){
        // erase old list
        assignmentList = new ArrayList<String[]>();

        Scanner scan = new Scanner(assignmentText);
        int name_type_grade = 1;
        String[] triple = new String[]{"", "", ""};

        while(scan.hasNext()){
            if(name_type_grade == 1){
                triple[0] = scan.next();
                name_type_grade = 2;
            }else if(name_type_grade == 2){
                triple[1] = scan.next();
                name_type_grade = 3;
            }else{
                triple[2] = scan.next();
                name_type_grade = 1;
                assignmentList.add(triple);
                triple = new String[]{"", "", ""};
            }
        }
    }

    private void updateDropDown(){
        assignmentType.removeAllItems();
        assignmentType.addItem("----");
        for(String[] i : typeList){
            assignmentType.addItem(i[0]);
        }
    }

    private void updateAll(){
        // if we have manual inputs, every time an action is performed, we need to update all of our lists
        // update weight list
        updateWeight();
        // update assignments
        updateAssignments();
        // update dropdown
        updateDropDown();
    }

    private void updateText(){
        // update strings from text boxes
        assignmentText = assignmentListing.getText();
        weightsText = typeListing.getText();
    }

    // CALCULATION
    private double calculateGrade(){
        double finalGrade = 0;

        // loop through each weight type
        for(String[] i : typeList){
            String type = i[0];
            double weight = Double.parseDouble(i[1]);

            // loop through assignments
            int num = 0;
            double totalGrade = 0;
            for(String[] j : assignmentList){
                if(j[1].equals(type)){
                    num = num + 1;
                    totalGrade += convertGrade(j[2]);
                }
            }
            finalGrade += (totalGrade/num)*weight;
        }
        return finalGrade;
    }

    private double convertGrade(String grade){
        if(grade.length() != 0) {
            // check if it has a percentage
            if(isPercent(grade)){
                return Double.parseDouble(grade.substring(0, grade.length()-1));
            }
            // check if it is a fraction
            if (isFraction(grade)) {
                return divide(grade);
            }
            // otherwise divide by 100
            return Double.parseDouble(grade) / 100;
        }
        return -1;
    }

    private boolean isFraction(String s){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '/'){
                return true;
            }
        }
        return false;
    }

    double divide(String s){
        int operator_index = 1;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '/'){
                operator_index = i;
                break;
            }
        }
        String str1 = s.substring(0, operator_index);
        String str2 = s.substring(operator_index + 1);

        Double num1 = Double.parseDouble(str1);
        Double num2 = Double.parseDouble(str2);

        return num1/num2;
    }

    private boolean isPercent(String s){
        for(int i = 0; i < s.length(); i ++){
            if(s.charAt(i) == '%'){
                return true;
            }
        }
        return false;
    }

    // HELPER
    private void printList(ArrayList<String[]> s){
        for(String[] i : s){
            System.out.print(i[0] + ", " + i[1] + "\t");
        }
        System.out.println("");
    }

    // SAVE and LOAD
    private void save() throws IOException {
        writeFile(weightsText, weightsPath);
        writeFile(assignmentText, assignmentsPath);
    }

    private void writeFile(String text, String path) throws IOException {
        FileWriter writer = new FileWriter(path, false);
        PrintWriter printer = new PrintWriter(writer);

        printer.println(text);
        printer.close();
    }

    private void load() {
        // save entire txt files into these strings
        try {
            weightsText = Files.readString(Paths.get(weightsPath));
            assignmentText = Files.readString(Paths.get(assignmentsPath));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        // update text boxes
        typeListing.setText(weightsText);
        assignmentListing.setText(assignmentText);

        // update
        updateAll();
    }


}

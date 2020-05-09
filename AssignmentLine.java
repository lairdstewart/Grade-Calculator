import javax.swing.*;

public class AssignmentLine extends GradeCalculator{
    private String gradeString;
    private String weightString;

    private JPanel panel = new JPanel(); // panel to hold everything
    private JTextField gradeField;
    private JTextField weightField;


    AssignmentLine(){
        weightString = "";
        gradeString = "";
    }

    void createUI(JPanel basePanel){
        // create weight field 
        weightField = new JTextField(4);
        panel.add(weightField);

        // create grade field
        gradeField = new JTextField(6);
        panel.add(gradeField);

        // add this new panel to the base panel
        basePanel.add(panel);
    }

    double getGrade(){
        if(gradeField.getText().length() != 0) {
            gradeString = gradeField.getText();
            if(isFraction(gradeString)){
                return divide(gradeString);
            }else{
                return Double.parseDouble(gradeString) / 100 ;
            }
        }else{
            return -1;
        }
    }

    double getWeight(){
        weightString = weightField.getText();

        if(weightString.length() == 0){
            return -1;
        }else{
            return Double.parseDouble(weightString);

        }
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

    boolean isFraction(String s){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '/'){
                return true;
            }
        }
        return false;
    }

}

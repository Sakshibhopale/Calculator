
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LiveCalculatorWithResultField extends JFrame implements ActionListener {
    private JTextField expressionField;  // To display the current expression
    private JTextField resultField;      // To display the result
    private double result = 0;
    private String operator = "";
    private boolean isOperatorClicked = false;

    LiveCalculatorWithResultField() {
        setTitle("Calculator");

        // Creating panel to hold buttons and setting layout as GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));
        panel.setBackground(new Color(32,32,32));

        // Creating a panel for the two text fields
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1, 5, 0));  // 2 rows, 1 column
        displayPanel.setBackground(new Color(32,32,32));

        // Creating the text fields for displaying inputs and results
        expressionField = new JTextField();
        expressionField.setHorizontalAlignment(JTextField.RIGHT);
        expressionField.setFont(new Font("Arial", Font.BOLD, 30));
        expressionField.setEditable(false);  // This field is only for display
        expressionField.setBackground(new Color(32,32,32));
        expressionField.setForeground(Color.WHITE);
        expressionField.setPreferredSize(new Dimension(400, 70));
        expressionField.setBorder(BorderFactory.createEmptyBorder());

        resultField = new JTextField();
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setFont(new Font("Arial", Font.BOLD, 30));
        resultField.setEditable(false);  // This field is only for display
        resultField.setBackground(new Color(32,32,32));
        resultField.setForeground(Color.WHITE);
        resultField.setPreferredSize(new Dimension(400, 70));
        resultField.setBorder(BorderFactory.createEmptyBorder());

        // Adding the text fields to the display panel
        displayPanel.add(expressionField);
        displayPanel.add(resultField);

        // Adding buttons to the panel
        String[] buttonLabels = {
                "C", "%", "Cancel", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "00", "0", ".", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this);  // Registering buttons with ActionListener
            button.setBackground(new Color(61, 61, 61));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());
            panel.add(button);
        }

        // Adding components to the frame
        setLayout(new BorderLayout());
        add(displayPanel, BorderLayout.NORTH);  // Add the display panel with two fields
        add(panel, BorderLayout.CENTER);

        // Finalizing the frame setup
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handling number and decimal input
        if (command.matches("[0-9]+") || command.equals(".")) {
            if (isOperatorClicked) {
                expressionField.setText(command);
                isOperatorClicked = false;
            } else {
                expressionField.setText(expressionField.getText() + command);
            }
        }
        // Handling operator input
        else if (command.matches("[+\\-*/]")) {
            if (!operator.isEmpty()) {
                // Perform the calculation as soon as the operator is entered
                performCalculation(Double.parseDouble(expressionField.getText()));
            } else {
                result = Double.parseDouble(expressionField.getText());
            }
            operator = command; // Store the new operator
            displayResult(result);  // Display the result immediately
            isOperatorClicked = true;
        }
        // Handling equals operation
        else if (command.equals("=")) {
            if (!operator.isEmpty()) {
                performCalculation(Double.parseDouble(expressionField.getText()));
                operator = "";  // Reset the operator after calculation
            }
            displayResult(result);
            isOperatorClicked = true;
        }
        // Handling clear and cancel buttons
        else if (command.equals("C")) {
            expressionField.setText("");
            resultField.setText("");
        } else if (command.equals("Cancel")) {
            String text = expressionField.getText();
            if (!text.isEmpty()) {
                expressionField.setText(text.substring(0, text.length() - 1));
            }
        }
        // Handling percentage operation
        else if (command.equals("%")) {
            double value = Double.parseDouble(expressionField.getText());
            expressionField.setText(String.valueOf(value / 100));
            displayResult(Double.parseDouble(expressionField.getText()));  // Show the result as well
        }
    }

    private void performCalculation(double operand) {
        switch (operator) {
            case "+" -> result += operand;
            case "-" -> result -= operand;
            case "*" -> result *= operand;
            case "/" -> result /= operand;
        }
        displayResult(result);  // Display the result after calculation
    }

    private void displayResult(double value) {
        // If the value is a whole number, display it as an integer
        if (value == (int) value) {
            resultField.setText(String.valueOf((int) value));
        } else {
            resultField.setText(String.valueOf(value));
        }
    }

    public static void main(String[] args) {
        new LiveCalculatorWithResultField();
    }
}

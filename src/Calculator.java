import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class Calculator extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new Calculator();
    }

    private final JTextField display;
    private final JButton[] numberButtons;
    private final JButton addButton;
    private final JButton substractButton;
    private final JButton multiplicateButton;
    private final JButton divideButton;
    private final JButton decimalButton;
    private final JButton equalsButton;
    private final JButton deleteButton;
    private final JButton clearButton;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    public Calculator(){
        setTitle("Calculator");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Font font = new Font("Monospaced", Font.PLAIN, 20);

        display = new JTextField();
        display.setBounds(50, 25, 300, 50);
        display.setFont(font);
        display.setEditable(false);
        add(display);

        JPanel panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        add(panel);

        numberButtons = new JButton[10];
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(font);
            numberButtons[i].setFocusable(false);
        }

        addButton = new JButton("+");
        substractButton = new JButton("-");
        multiplicateButton = new JButton("*");
        divideButton = new JButton("/");
        decimalButton = new JButton(".");
        equalsButton = new JButton("=");
        deleteButton = new JButton("Del");
        clearButton = new JButton("Clr");

        JButton[] functionButtons = new JButton[]{addButton, substractButton, multiplicateButton, divideButton, decimalButton, equalsButton, deleteButton, clearButton};

        for (JButton button : functionButtons) {
            button.addActionListener(this);
            button.setFont(font);
            button.setFocusable(false);
        }

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(substractButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(multiplicateButton);
        panel.add(equalsButton);
        panel.add(numberButtons[0]);
        panel.add(decimalButton);
        panel.add(divideButton);

        clearButton.setBounds(50, 430, 145, 50);
        deleteButton.setBounds(205, 430, 145, 50);
        add(clearButton);
        add(deleteButton);

        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c >= '0' && c <= '9') || c == '.') {
                    display.setText(display.getText() + c);
                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (!display.getText().isEmpty() && !isLastCharOperator()) {
                        display.setText(display.getText() + " " + c + " ");
                    }
                } else if (c == '\n') {
                    calculateResult();
                } else if (c == '\b') {
                    if (!display.getText().isEmpty()) {
                        display.setText(display.getText().substring(0, display.getText().length() - 1));
                    }
                }
            }
        });

        display.setFocusable(true);
        display.requestFocusInWindow();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                display.setText(display.getText().concat(String.valueOf(i)));
            }
        }
        if (e.getSource() == decimalButton) {
            display.setText(display.getText().concat("."));
        }
        if (e.getSource() == addButton) {
            handleOperator('+');
        }
        if (e.getSource() == substractButton) {
            handleOperator('-');
        }
        if (e.getSource() == multiplicateButton) {
            handleOperator('*');
        }
        if (e.getSource() == divideButton) {
            handleOperator('/');
        }
        if (e.getSource() == equalsButton) {
            calculateResult();
        }
        if (e.getSource() == clearButton) {
            display.setText("");
        }
        if (e.getSource() == deleteButton) {
            if (!display.getText().isEmpty()) {
                display.setText(display.getText().substring(0, display.getText().length() - 1));
            }
        }
    }

    private void handleOperator(char operator) {
        if (!display.getText().isEmpty() && !isLastCharOperator()) {
            display.setText(display.getText().concat(" " + operator + " "));
        }
    }

    private boolean isLastCharOperator() {
        String text = display.getText();
        return text.endsWith(" + ") || text.endsWith(" - ") || text.endsWith(" * ") || text.endsWith(" / ");
    }

    private void calculateResult() {
        try {
            String[] parts = display.getText().trim().split(" ");
            if (parts.length == 3) {
                num1 = Double.parseDouble(parts[0]);
                operator = parts[1].charAt(0);
                num2 = Double.parseDouble(parts[2]);

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error: Division by Zero");
                            return;
                        }
                        break;
                }
                display.setText(decimalFormat.format(result));
                num1 = result;
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }
}
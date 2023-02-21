import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" ( "),
            new JButton(" ) "),
            new JButton(" = "),
            new JButton(" C ")
    };

    String oper_values[] = {"+", "-", "*", "/", "(", ")", "=", ""};

    String value;
    char operator;

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setSize(230, 250);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i = 0; i < 10; i++)
            buttonpanel.add(digits[i]);

        for (int i = 0; i < 8; i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 7)
                        area.setText("");
                    else if (finalI == 6) {
                        String lhs;
                        String rhs;
                        try {
                            //lhs = area.getText().substring(0, area.getText().indexOf(operator + ""));
                            //rhs = area.getText().substring(area.getText().indexOf(operator + "") + 1, area.getText().length());
                            Stiva stack = new Stiva();
                            String pol = "";
                            String op = area.getText();
                            for (int i = 0; i < op.length(); ++i) {
                                char chr = op.charAt(i);
                                if (chr >= '0' && chr <= '9') {
                                    pol += chr;
                                    if (i + 1 >= op.length() || !(op.charAt(i + 1) >= '0' && op.charAt(i + 1) <= '9'))
                                        pol += "#";
                                }
                                if (chr == '*' || chr == '/') {
                                    while (!stack.isEmpty() && (stack.top() == "*" || stack.top() == "/"))
                                        pol += stack.pop();
                                    stack.push(chr + "");
                                }
                                if (chr == '+' || chr == '-') {
                                    while (!stack.isEmpty() && !stack.top().equalsIgnoreCase("("))
                                        pol += stack.pop();
                                    stack.push(chr + "");
                                }
                                if (chr == '(')
                                    stack.push(chr + "");
                                if (chr == ')') {
                                    while (!stack.top().equalsIgnoreCase("("))
                                        pol += stack.pop();
                                    stack.pop();
                                }
                            }
                            while (!stack.isEmpty())
                                pol += stack.pop();
                            area.setText(calcul(stack, pol) + "");
                        } catch (Exception e) {
                            area.setText(" !!!Probleme!!! ");
                        }
                    } else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }
    }

    public double calcul(Stiva stack, String pol) {
        int i = 0;
        String temp = "";
        while (pol.charAt(i) != '+' && pol.charAt(i) != '-' && pol.charAt(i) != '*' && pol.charAt(i) != '/') {
            if (pol.charAt(i) != '#')
                temp += pol.charAt(i);
            else {
                stack.push(temp);
                temp = "";
            }
            ++i;
        }
        temp = "";
        for (; i < pol.length(); ++i) {
            if (pol.charAt(i) >= '0' && pol.charAt(i) <= '9' || pol.charAt(i) == ' ') {
                temp += pol.charAt(i);
            } else if (pol.charAt(i) == '#') {
                stack.push(temp);
                temp = "";
            } else {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                if (pol.charAt(i) == '+')
                    b += a;
                if (pol.charAt(i) == '-')
                    b -= a;
                if (pol.charAt(i) == '*')
                    b *= a;
                if (pol.charAt(i) == '/')
                    b /= a;
                stack.push(b + "");
            }
        }
        return Double.parseDouble(stack.pop());
    }
}
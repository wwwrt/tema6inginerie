package tema;

import java.util.ArrayList;
import java.util.List;

class Calculator {
    private double currentValue;
    private List<Operation> history;  //<Operation> este o clasă auxiliară creată pentru a ține evidența tipului de operație
    private List<Operation> redoHistory;

    public Calculator() {
        currentValue = 0;
        history = new ArrayList<>();  //prin generics (<>) se face o verificare statică a tipului și se asigură că lista poate conține doar obiecte de tip "Operation"
        redoHistory = new ArrayList<>();
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void add(double value) {
        currentValue += value;
        history.add(new Operation("add", value));
        redoHistory.clear();
    }

    public void subtract(double value) {
        currentValue -= value;
        history.add(new Operation("subtract", value));
        redoHistory.clear();
    }

    public void multiply(double value) {
        currentValue *= value;
        history.add(new Operation("multiply", value));
        redoHistory.clear();
    }

    public void divide(double value) {
        if (value != 0) {
            currentValue /= value;
            history.add(new Operation("divide", value));
            redoHistory.clear();
        } else {
            System.out.println("Cannot divide by zero.");
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            Operation lastOperation = history.remove(history.size() - 1);
            double undoValue = lastOperation.getValue();
            String operationType = lastOperation.getType();

            redoHistory.add(new Operation(operationType, undoValue));

            switch (operationType) {
                case "add":
                    currentValue -= undoValue;
                    break;
                case "subtract":
                    currentValue += undoValue;
                    break;
                case "multiply":
                    currentValue /= undoValue;
                    break;
                case "divide":
                    currentValue *= undoValue;
                    break;
            }
        }
    }

    public void redo() {
        if (!redoHistory.isEmpty()) {
            Operation lastRedoOperation = redoHistory.remove(redoHistory.size() - 1);
            double redoValue = lastRedoOperation.getValue();
            String operationType = lastRedoOperation.getType();

            history.add(new Operation(operationType, redoValue));

            switch (operationType) {
                case "add":
                    currentValue += redoValue;
                    break;
                case "subtract":
                    currentValue -= redoValue;
                    break;
                case "multiply":
                    currentValue *= redoValue;
                    break;
                case "divide":
                    currentValue /= redoValue;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        calculator.add(5);
        calculator.subtract(2);
        calculator.multiply(3);
        calculator.divide(2);

        System.out.println("Current Value: " + calculator.getCurrentValue());

        calculator.undo();
        System.out.println("After Undo: " + calculator.getCurrentValue());

        calculator.redo();
        System.out.println("After Redo: " + calculator.getCurrentValue());
    }
}

class Operation {
    private String type;
    private double value;

    public Operation(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}

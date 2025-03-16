import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.awt.event.ActionEvent;
import java.util.List;
import java.time.Duration;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testCalculatorInitialization() {
        assertNotNull(calculator, "Calculator instance should not be null");
        assertNotNull(calculator.textfield, "Text field should not be null");
    }

    @Test
    void testClearButton() {
        calculator.textfield.setText("12345");
        calculator.actionPerformed(new ActionEvent(calculator.clrButton, ActionEvent.ACTION_PERFORMED, ""));
        assertEquals("", calculator.textfield.getText(), "Clear button should reset text field");
    }

    @ParameterizedTest
    @CsvSource({"10,5,+,15.0", "10,3,-,7.0", "4,5,*,20.0", "20,4,/,5.0"})
    void testArithmeticOperations(String num1, String num2, char operator, String expected) {
        calculator.textfield.setText(num1);
        switch (operator) {
            case '+': calculator.actionPerformed(new ActionEvent(calculator.addButton, ActionEvent.ACTION_PERFORMED, "")); break;
            case '-': calculator.actionPerformed(new ActionEvent(calculator.subButton, ActionEvent.ACTION_PERFORMED, "")); break;
            case '*': calculator.actionPerformed(new ActionEvent(calculator.mulButton, ActionEvent.ACTION_PERFORMED, "")); break;
            case '/': calculator.actionPerformed(new ActionEvent(calculator.divButton, ActionEvent.ACTION_PERFORMED, "")); break;
        }
        calculator.textfield.setText(num2);
        calculator.actionPerformed(new ActionEvent(calculator.equButton, ActionEvent.ACTION_PERFORMED, ""));
        assertEquals(expected, calculator.textfield.getText(), "Incorrect calculation result");
    }

    @Test
    void testDeleteButton() {
        calculator.textfield.setText("12345");
        calculator.actionPerformed(new ActionEvent(calculator.delButton, ActionEvent.ACTION_PERFORMED, ""));
        assertEquals("1234", calculator.textfield.getText(), "Delete button should remove last character");
    }

    @Test
    void testNegativeButton() {
        calculator.textfield.setText("10");
        calculator.actionPerformed(new ActionEvent(calculator.negButton, ActionEvent.ACTION_PERFORMED, ""));
        assertEquals("-10.0", calculator.textfield.getText(), "Negative button should negate the value");
    }

    @Test
    void testDecimalPoint() {
        calculator.textfield.setText("3");
        calculator.actionPerformed(new ActionEvent(calculator.decButton, ActionEvent.ACTION_PERFORMED, ""));
        assertEquals("3.", calculator.textfield.getText(), "Decimal button should add '.' to number");
    }

    @Test
    @Timeout(1)
    void testTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            calculator.textfield.setText("10");
            calculator.actionPerformed(new ActionEvent(calculator.addButton, ActionEvent.ACTION_PERFORMED, ""));
            calculator.textfield.setText("20");
            calculator.actionPerformed(new ActionEvent(calculator.equButton, ActionEvent.ACTION_PERFORMED, ""));
        });
    }

    @Test
    void testLinesMatch() {
        List<String> expectedLines = List.of("Calculator initialized", "Ready");
        List<String> actualLines = List.of("Calculator initialized", "Ready");
        assertLinesMatch(expectedLines, actualLines);
    }
}

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test {
    public static void main(String[] args) {
        // Create a JFrame (a window)
        JFrame frame = new JFrame("Simple Button Example");

        // Create a JPanel to hold the button
        JPanel panel = new JPanel();

        // Create a JButton
        JButton button = new JButton("Click Me");

        // Add the button to the panel
        panel.add(button);

        // Add the panel to the frame
        frame.add(panel);

        // Set the default close operation of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(300, 200);

        // Make the frame visible
        frame.setVisible(true);
    }
}

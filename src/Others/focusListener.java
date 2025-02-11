/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Others;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author RaeYobz
 */
public class focusListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTextField Focus Example");
        JTextField textField = new JTextField(20);

        // Add a focus listener to the text field
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Do something when the focus is gained (if needed)
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Do something when the focus is lost
                System.out.println("Focus lost! Performing action...");
                // Example: You could validate input or save the data here
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(textField);
        frame.pack();
        frame.setVisible(true);
    }
}

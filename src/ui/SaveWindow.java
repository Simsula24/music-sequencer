package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveWindow extends JDialog {
    private static SaveWindow currentInstance;

    public SaveWindow(Frame owner) {
        super(owner, "Save project", true);

        setTitle("Save Project");
        setSize(600, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            //    MainWindow.subWindowClosed();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (currentInstance == SaveWindow.this) {
                    currentInstance = null;
                }
            //    MainWindow.subWindowClosed();
            }
        });

        this.setVisible(true);



    }


}

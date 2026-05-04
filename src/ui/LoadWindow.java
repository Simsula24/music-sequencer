package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadWindow extends JDialog {

    private static LoadWindow currentInstance;

    public LoadWindow(Frame owner) {
        super(owner, "Load project", true);

        setTitle("Load project");
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
                if (currentInstance == LoadWindow.this) {
                    currentInstance = null;
                }
                //MainWindow.subWindowClosed();
            }
        });


        this.setVisible(true);



    }

}

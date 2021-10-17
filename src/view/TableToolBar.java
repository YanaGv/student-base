package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableToolBar extends JToolBar {
    private Controller controller;
    private JLabel pagesLabel;
    private StudentTable studentTable;

    public TableToolBar(StudentTable studentTable){
        super();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.studentTable = studentTable;
        controller = studentTable.getController();


        JButton next = new JButton(">");
        JButton previous = new JButton("<");
        JButton first = new JButton("<<");
        JButton last = new JButton(">>");

        pagesLabel = new JLabel("1 / 1");

        add(first);
        add(previous);
        addSeparator();
        add(pagesLabel);
        addSeparator();
        add(next);
        add(last);
        addSeparator();



        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setCurentPage(1);
                studentTable.updateTable();
                updatePagesLable();
            }
        });
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int curentPage = controller.getCurentPage();
                if(curentPage!=1) controller.setCurentPage(curentPage-1);
                studentTable.updateTable();
                updatePagesLable();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int curentPage = controller.getCurentPage();
                if(curentPage!=controller.getPagesNumber()) controller.setCurentPage(curentPage+1);
                studentTable.updateTable();
                updatePagesLable();
            }
        });
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setCurentPage(controller.getPagesNumber());
                studentTable.updateTable();
                updatePagesLable();
            }
        });
    }

    public Controller getController() {
        return controller;
    }

    public StudentTable getStudentTable() {
        return studentTable;
    }

    public void updatePagesLable(){
        pagesLabel.setText(controller.getCurentPage()+" / "+controller.getPagesNumber());
    }
}

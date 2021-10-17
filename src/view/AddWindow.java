package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddWindow extends JFrame{
    private static final int HEIGHT = 20;
    private Controller controller;
    private StudentTable studentTable;

    public AddWindow(TableToolBar toolBar) {
        super("Добавить запись");
        controller = toolBar.getController();
        studentTable = toolBar.getStudentTable();
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        createLabels();
        ArrayList<JTextField> textFields = createTexts();

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> works = new ArrayList<>();
                String firstName = textFields.get(0).getText();
                String lastName = textFields.get(1).getText();
                String middleName = textFields.get(2).getText();
                String group = textFields.get(3).getText();
                for (JTextField workInTerm: textFields.subList(4,14)) works.add(workInTerm.getText());
                controller.addStudent(firstName,lastName, middleName, group,works);
                toolBar.updatePagesLable();
                studentTable.updateTable();

            }
        });
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> setVisible(false));

        getContentPane().add(addButton);
        getContentPane().add(ok);

        setBounds(20, 20, 850, 130);
        setResizable(false);

    }
    private void createLabels(){
        ArrayList<JLabel> labels = new ArrayList<>();
        JLabel firstNameLabel = new JLabel("Фамилия");
        firstNameLabel.setPreferredSize(new Dimension(100,HEIGHT));
        labels.add(firstNameLabel);
        JLabel lastNameLabel = new JLabel("Имя");
        lastNameLabel.setPreferredSize(new Dimension(100,HEIGHT));
        labels.add(lastNameLabel);
        JLabel middleNameLabel = new JLabel("Отчество");
        middleNameLabel.setPreferredSize(new Dimension(100, HEIGHT));
        labels.add(middleNameLabel);
        JLabel groupLabel = new JLabel("Группа");
        groupLabel.setPreferredSize(new Dimension(50, HEIGHT));
        labels.add(groupLabel);
        labels.add(new JLabel("1 сем"));
        labels.add(new JLabel("2 сем"));
        labels.add(new JLabel("3 сем"));
        labels.add(new JLabel("4 сем"));
        labels.add(new JLabel("5 сем"));
        labels.add(new JLabel("6 сем"));
        labels.add(new JLabel("7 сем"));
        labels.add(new JLabel("8 сем"));
        labels.add(new JLabel("9 сем"));
        labels.add(new JLabel("10 сем"));
        for (JLabel label : labels.subList(4, 14)) label.setPreferredSize(new Dimension(40, HEIGHT));
        for (JLabel label : labels) getContentPane().add(label);

    }
    private ArrayList<JTextField> createTexts(){
        ArrayList<JTextField> textFields = new ArrayList<>();
        JTextField firstNameText = new JTextField();
        firstNameText.setPreferredSize(new Dimension(100, HEIGHT));
        textFields.add(firstNameText);  //0
        JTextField lastNameText = new JTextField();
        lastNameText.setPreferredSize(new Dimension(100, HEIGHT));
        textFields.add(lastNameText);   //1
        JTextField middleNameText = new JTextField();
        middleNameText.setPreferredSize(new Dimension(100, HEIGHT));
        textFields.add(middleNameText); //2
        JTextField groupText = new JTextField();
        groupText.setPreferredSize(new Dimension(50, HEIGHT));
        textFields.add(groupText);      //3
        for (int i=0;i<10;i++) textFields.add(new JTextField());
        for (JTextField textField : textFields.subList(4, 14)) textField.setPreferredSize(new Dimension(40, HEIGHT));
        for (JTextField textField: textFields) getContentPane().add(textField);
        return textFields;
    }
}
package view;

import javax.swing.*;
import java.awt.*;

public class ChosePanel extends JPanel {
    private JComboBox<Integer> groupChouser;

    private JRadioButtonMenuItem lastNameAndGroup;
    private JRadioButtonMenuItem lastNameAndWork;
    private JRadioButtonMenuItem groupAndWork;

    private JTextField lastName;
    private JTextField workFrom;
    private JTextField workTo;

    public ChosePanel(){
        super(new FlowLayout(FlowLayout.LEFT));

        lastNameAndGroup = new JRadioButtonMenuItem("Поиск по фамилии и группе");
        lastNameAndWork = new JRadioButtonMenuItem("Поиск по фамилии и общественной работе");
        groupAndWork = new JRadioButtonMenuItem("Поиск по группе и общественной работе");

        lastNameAndGroup.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(lastNameAndGroup);
        buttonGroup.add(lastNameAndWork);
        buttonGroup.add(groupAndWork);

        setPreferredSize(new Dimension(300,200));
        add(lastNameAndGroup);
        add(lastNameAndWork);
        add(groupAndWork);

        JLabel lastnameLabel = new JLabel("  Фамилия: ");
        add(lastnameLabel);
        lastName  = new JTextField(10);
        add(lastName);
        JLabel groupLabel     = new JLabel("Группа:");
        add(groupLabel);
        groupChouser = new JComboBox<>();
        add(groupChouser);
        JLabel workFromLable  = new JLabel("  Общественные работы от: ");
        add(workFromLable);
        workFrom   = new JTextField(4);
        add(workFrom);
        JLabel workToLable    = new JLabel("до:");
        add(workToLable);
        workTo     = new JTextField(4);
        add(workTo);

        setVisible(true);
    }

    public JComboBox<Integer> getGroupChouser() {
        return groupChouser;
    }

    public JRadioButtonMenuItem getLastNameAndGroup() {
        return lastNameAndGroup;
    }

    public JRadioButtonMenuItem getLastNameAndWork() {
        return lastNameAndWork;
    }

    public JRadioButtonMenuItem getGroupAndWork() {
        return groupAndWork;
    }
    public String getLastName(){
        String lastName = this.lastName.getText();
        return lastName;
    }
    public int getWorkFrom(){
        int workFrom = Integer.parseInt(this.workFrom.getText());
        return workFrom;
    }
    public int getWorkTo(){
        int workTo = Integer.parseInt(this.workTo.getText());
        return workTo;
    }
    public int getGroup(){
        int group = (Integer)groupChouser.getSelectedItem();
        return group;
    }
}

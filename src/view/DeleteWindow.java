package view;

import controller.Controller;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteWindow extends JFrame {
    private Controller controller;
    private StudentTable deleteTable;
    private StudentTable studentTable;
    private ChosePanel chousePanel;

    public DeleteWindow(StudentTable studentTable){
        super("Удаление");
        this.studentTable = studentTable;
        controller = studentTable.getController();
        deleteTable = new StudentTable(controller);

        setLayout(new BorderLayout());
        chousePanel = new ChosePanel();

        JButton deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(new DeleteListener());
        chousePanel.add(deleteButton);

        getContentPane().add(chousePanel, BorderLayout.WEST);
        getContentPane().add(new JScrollPane(deleteTable.getTable()));

        setBounds(20,20,1024,210);

    }
    //заполняет выпадающий список груп
    public void configerGroupChouser(){
        JComboBox<Integer> groups = chousePanel.getGroupChouser();
        ArrayList<Student> students = controller.getStudentList();
        groups.setPreferredSize(new Dimension(50,20));

        for (Student student: students){
            ComboBoxModel<Integer> model = groups.getModel();
            //количество записей в списке груп
            int itemsNumber = model.getSize();
            //переменная указывающая есть ли уже такая группа в списке групп
            boolean isExist = false;

            for(int i=0;i<itemsNumber;i++) {
                if(student.getGroup()==model.getElementAt(i)) isExist = true;
            }
            if(!isExist) groups.addItem(student.getGroup());
        }
    }
    //В зависимости от выбранного checkBox запускает разные сценарии удаления записей из контроллера
    private class DeleteListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Student> deletedStudents = new ArrayList<>();
            if(chousePanel.getLastNameAndGroup().isSelected()){
                deletedStudents = controller.getListByParams(chousePanel.getLastName(),chousePanel.getGroup());
            }else if(chousePanel.getLastNameAndWork().isSelected()){
                deletedStudents = controller.getListByParams(chousePanel.getLastName(), chousePanel.getWorkFrom(), chousePanel.getWorkTo());
            }else if(chousePanel.getGroupAndWork().isSelected()){
                deletedStudents = controller.getListByParams(chousePanel.getGroup(), chousePanel.getWorkFrom(), chousePanel.getWorkTo());
            }
            deleteTable.updateTable(controller.getDataVector(deletedStudents));
            controller.deleteStudent(deletedStudents);

            if(deletedStudents.size()==0) JOptionPane.showMessageDialog(new JFrame(),"Данные записи не найдены");
            else  JOptionPane.showMessageDialog(new JFrame(),"Было удалено "+deletedStudents.size()+" записей.");
            studentTable.updateTable();
        }
    }
}

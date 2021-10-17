package view;

import controller.Controller;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FindWindow extends JFrame {
    private Controller controller;
    private StudentTable findTable;
    private ChosePanel chousePanel;



    public FindWindow(StudentTable studentTable) {
        super("Поиск");
        controller = studentTable.getController();
        //таблица в которую будут выводится результаты поиска
        findTable = new StudentTable(controller);

        setLayout(new BorderLayout());
        chousePanel = new ChosePanel();

        JButton findButton = new JButton("Найти");
        findButton.addActionListener(new FindWindow.FindListener());
        chousePanel.add(findButton);

        getContentPane().add(chousePanel, BorderLayout.WEST);
        getContentPane().add(new JScrollPane(findTable.getTable()));

        setBounds(20, 20, 1024, 210);


    }

    //заполнение выпадающего списка групп на основе записей существующих в контроллере
    public void configerGroupChouser() {
        boolean isExist = false;//переменная указывающая есть ли уже такая группа в списке групп
        var groupChooser = chousePanel.getGroupChouser();
        groupChooser.setPreferredSize(new Dimension(50, 20));
        var comboBoxModel = groupChooser.getModel();
        var students = controller.getStudentList();//количество записей в списке груп
        int itemsNumber = comboBoxModel.getSize();
        int grops = groupChooser.getItemCount();
        System.out.print(grops);

        for (Student student : students) {
            int studentGroup = student.getGroup();
            for (int i = 0; i < itemsNumber; i++) {
                if (studentGroup == comboBoxModel.getElementAt(i)) isExist = true;
            }
            if (!isExist) groupChooser.addItem(student.getGroup());
        }
    }

    //в зависимости от выбранного checkBox запускает разные сценарии поика
    private class FindListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Student> findedStudents = new ArrayList<>();
            if (chousePanel.getLastNameAndGroup().isSelected()) {
                findedStudents = controller.getListByParams(chousePanel.getLastName(), chousePanel.getGroup());
            } else if (chousePanel.getLastNameAndWork().isSelected()) {
                findedStudents = controller.getListByParams(chousePanel.getLastName(), chousePanel.getWorkFrom(), chousePanel.getWorkTo());
            } else if (chousePanel.getGroupAndWork().isSelected()) {
                findedStudents = controller.getListByParams(chousePanel.getGroup(), chousePanel.getWorkFrom(), chousePanel.getWorkTo());
            }
            if (findedStudents.size() == 0) JOptionPane.showMessageDialog(new JFrame(), "Данные записи не найдены");
            else findTable.updateTable(controller.getDataVector(findedStudents)); //заполнение таблицы поиска
        }
    }
}

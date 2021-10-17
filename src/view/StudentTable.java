package view;

import controller.Controller;
import view.table.header.ColumnGroup;
import view.table.header.GroupableTableHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import java.util.Vector;

public class StudentTable {
    private JTable table;
    private Controller controller;

    public StudentTable(Controller controller){
        this.controller = controller;
        table = createStudentTable();
    }

    public Controller getController() {
        return controller;
    }

    public JTable getTable() {
        return table;
    }

    private JTable createStudentTable(){

        DefaultTableModel tableModel = new DefaultTableModel();
        Object[] columnIdentifiers =  new Object[]{"ФИО","Группа","1 сем","2 сем","3 сем","4 сем","5 сем","6 сем","7 сем","8 сем","9 сем","10 сем"};
        tableModel.setColumnIdentifiers(columnIdentifiers);


        JTable table = new JTable( tableModel ) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        TableColumnModel columnModelm = table.getColumnModel();


        ColumnGroup workGroup = new ColumnGroup("Общественные работы");
        for(int i=2; i<12; i++) workGroup.add(columnModelm.getColumn(i));
        GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
        header.addColumnGroup(workGroup);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=1;i<12;i++) columnModelm.getColumn(i).setCellRenderer( centerRenderer );

        columnModelm.getColumn(0).setPreferredWidth(150);

        return table;
    }
    //таблица строится на основании всех записей в находящихся в контроллере
    public void updateTable(){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for(Vector<String> record: controller.getDataVector()) tableModel.addRow(record);
    }
    //таблица строится на основе данных из вектора data
    public void updateTable(Vector<Vector<String>> data){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for(Vector<String> record: data) tableModel.addRow(record);
    }
}

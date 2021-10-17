package view;
import controller.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class StudentTableWindow extends JFrame {

    private Controller controller;
    private StudentTable studentTable;
    private TableToolBar toolBar;
    private DeleteWindow deleteWindow;
    private FindWindow findWindow;
    private AddWindow addWindow;

    public StudentTableWindow(){
        super( "Таблица студентов" );

        controller = new Controller();
        studentTable = new StudentTable(controller);
        toolBar = new TableToolBar(studentTable);
        addWindow = new AddWindow(toolBar);
        deleteWindow = new DeleteWindow(studentTable);
        findWindow = new FindWindow(studentTable);

        JButton add = new JButton("Добавить запись");
        add.addActionListener(e -> addWindow.setVisible(true));
        toolBar.add(add);

        getContentPane().add( toolBar, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(studentTable.getTable()));
        getContentPane().add(createMenuBar(), BorderLayout.NORTH);

        setSize(1024,500);
    }

    private JMenuBar createMenuBar(){
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        menu.add(createEditMenu());
        menu.add(createViewMenu());
        return menu;
    }

    private JMenu createViewMenu(){
        JMenu viewMenu = new JMenu("Вид");
        JMenu view = new JMenu("Отображение");
        JRadioButtonMenuItem five = new JRadioButtonMenuItem("Пять записей");
        JRadioButtonMenuItem ten = new JRadioButtonMenuItem("Десять записей");
        JRadioButtonMenuItem twenty = new JRadioButtonMenuItem("Двадцать записей");

        five.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(five);
        buttonGroup.add(ten);
        buttonGroup.add(twenty);

        view.add(five);
        view.add(ten);
        view.add(twenty);
        viewMenu.add(view);

        five.addActionListener(new ViewActionListener(5));
        ten.addActionListener(new ViewActionListener(10));
        twenty.addActionListener(new ViewActionListener(20));

        return viewMenu;
    }
    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem close = new JMenuItem("Закрыть");

        open.addActionListener(new LoadListener());
        save.addActionListener(new SaveListener());
        close.addActionListener(e -> System.exit(0));

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(close);

        return fileMenu;
    }
    private JMenu createEditMenu(){
        JMenu editMenu = new JMenu("Правка");
        JMenuItem delete = new JMenuItem("Удалить");
        JMenuItem serch = new JMenuItem("Найти");

        delete.addActionListener(e -> {
            deleteWindow.configerGroupChouser();
            deleteWindow.setVisible(true);
        });
        serch.addActionListener(e -> {
            findWindow.configerGroupChouser();
            findWindow.setVisible(true);
        });

        editMenu.add(delete);
        editMenu.add(serch);
        return editMenu;
    }
    //меняет количество отображаемых записей на странице
    private class ViewActionListener implements ActionListener{
        private int visibleRecordesNumber;

        ViewActionListener(int visibleRecordesNumber){
            this.visibleRecordesNumber = visibleRecordesNumber;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.setVisibleRecordsNumber(visibleRecordesNumber);
            controller.setCurentPage(1);
            studentTable.updateTable();
            toolBar.updatePagesLable();

        }
    }
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Special XML file", "xml");
            fileChooser.setFileFilter(filter);
            //если выбор файла произошол успешно
            int status = fileChooser.showSaveDialog(fileChooser);
            if (status == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                controller.saveXML(path);
            }

        }
    }

    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Special xml file", "xml");
            fileChooser.setFileFilter(filter);
            int status = fileChooser.showOpenDialog(fileChooser);
            //если выбор файла произошол успешно
            if (status == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    controller.readXML(path);
                    studentTable.updateTable();
                    toolBar.updatePagesLable();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (SAXException exc) {
                    exc.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

package controller;

import model.Student;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Controller {
    private ArrayList<Student> studentList;
    private int visibleRecordsNumber;
    private int curentPage;

    public Controller() {
        studentList = new ArrayList<>(0);
        visibleRecordsNumber = 5;
        curentPage = 1;
    }

    public void setCurentPage(int curentPage) {
        this.curentPage = curentPage;
    }

    public void setVisibleRecordsNumber(int visibleRecordsNumber) {
        this.visibleRecordsNumber = visibleRecordsNumber;
    }

    public int getCurentPage() {
        return curentPage;
    }

    public int getVisibleRecordsNumber() {
        return visibleRecordsNumber;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    //высчитывает количество страниц для пагинации с числом записей visibleRecordsNumber
    public int getPagesNumber() {
        int recordsNumber = studentList.size();
        int pagesNumber = 1;
        if (recordsNumber != 0) {
            if (recordsNumber % visibleRecordsNumber != 0) {
                pagesNumber = recordsNumber / visibleRecordsNumber + 1;
            } else pagesNumber = recordsNumber / visibleRecordsNumber;
        }
        return pagesNumber;
    }

    //добавляет новый объект в studentList
    public void addStudent(String lastName, String firstName, String middleName, String group, ArrayList<String> works) {
        ArrayList<Integer> workInTerm = new ArrayList<>(0);
        for (String work : works) {
            int value = 0;
            if (!work.isEmpty()) value = Integer.parseInt(work);
            workInTerm.add(value);
        }
        Student student = new Student(lastName, firstName, middleName, Integer.parseInt(group), workInTerm);
        studentList.add(student);
    }

    //вектор данных который может принимать таблица для отображения
    public Vector<Vector<String>> getDataVector() {

        Vector<Vector<String>> data = new Vector<>();
        int startIndex = (curentPage - 1) * visibleRecordsNumber;

        int endIndex = startIndex + visibleRecordsNumber;
        int recordsNumber = studentList.size();
        if (recordsNumber > endIndex) {
            for (Student student : studentList.subList(startIndex, endIndex))
                data.add(createVectorRecord(student));
        } else {
            for (int i = startIndex; i < recordsNumber; i++)
                data.add(createVectorRecord(studentList.get(i)));
        }
        return data;
    }

    //преобразует массив объектов типа Student в вектор данных
    public Vector<Vector<String>> getDataVector(ArrayList<Student> studentArrayListList) {
        Vector<Vector<String>> data = new Vector<>();

        for (Student student : studentArrayListList)
            data.add(createVectorRecord(student));

        return data;
    }

    //преобразует запись в вектор
    private Vector<String> createVectorRecord(Student student) {
        Vector<String> record = new Vector<>();
        record.add(student.getFullName());
        record.add("" + student.getGroup());
        for (int work : student.getWorkInTerm()) record.add("" + work);

        return record;
    }

    public void saveXML(String path) {
        DOMParser domParser = new DOMParser();
        domParser.saveXML(studentList, path);
    }

    public void readXML(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = new SAXParser();
        saxParser.readXML(path);
        studentList.clear();
        studentList = SAXParser.getStudentArrayList();
    }

    public void deleteStudent(ArrayList<Student> deletedStudentList) {
        for (Student student : deletedStudentList)
            studentList.remove(student);
    }

    //ищет записи по заданному номеру группы и фамилии
    public ArrayList<Student> getListByParams(String lastName, int group) {
        ArrayList<Student> findList = new ArrayList<>();
        for (Student student : studentList)
            if (student.getLastName().equals(lastName) && student.getGroup() == group)
                findList.add(student);

        return findList;
    }

    //ищет записи по заданной фамилии и диапазону общественных работ
    public ArrayList<Student> getListByParams(String lastName, int workFrom, int workTo) {
        ArrayList<Student> findList = new ArrayList<>();
        for (Student student : studentList)
            if (student.getLastName().equals(lastName) &&
                    student.getAllWork() >= workFrom &&
                    student.getAllWork() <= workTo) findList.add(student);

        return findList;
    }

    //ищет записи по заданному номеру группы и диапазону общественных работ
    public ArrayList<Student> getListByParams(int group, int workFrom, int workTo) {
        ArrayList<Student> findList = new ArrayList<>();
        for (Student student : studentList)
            if (student.getGroup() == group &&
                    student.getAllWork() >= workFrom &&
                    student.getAllWork() <= workTo) findList.add(student);

        return findList;
    }
}

package controller;

import model.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class DOMParser {

    private Element rootElement;
    private Document doc;

    //сохранение studentList в XML файл
    public void saveXML(ArrayList<Student> studentArrayList, String savePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("students");
            doc.appendChild(rootElement);

            for(Student student: studentArrayList) addRecord(student);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(savePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce ) {
            pce.printStackTrace();
        } catch (TransformerConfigurationException tfe){
            tfe.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    //добавление записи в XML файл
    private void addRecord(Student inputStudent) {
        Element student = doc.createElement("student");
        rootElement.appendChild(student);

        Element fullname = doc.createElement("fullname");
        student.appendChild(fullname);
        fullname.setAttribute("lastname", inputStudent.getLastName());
        fullname.setAttribute("firstname", inputStudent.getFirstName());
        fullname.setAttribute("middlename", inputStudent.getMiddleName());

        Element group = doc.createElement("group");
        student.appendChild(group);
        group.setAttribute("number", ""+inputStudent.getGroup());

        Element works = doc.createElement("works");
        student.appendChild(works);
        for (int i=0; i<10; i++)
            works.setAttribute("term"+(i+1), ""+inputStudent.getWorkInTerm().get(i));
    }
}

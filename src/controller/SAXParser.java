package controller;

import model.Student;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SAXParser {
    private static ArrayList<Student> studentArrayList;

    public SAXParser(){
        studentArrayList = new ArrayList<>();
    }

    public void readXML(String path){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();
            parser.parse(new File(path), handler);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException saxe) {
            saxe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private class XMLHandler extends DefaultHandler{
        private String lastName;
        private String firstName;
        private String middleName;
        private int groupNumber;
        private ArrayList<Integer> works;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("fullname")) {
                lastName = attributes.getValue("lastname");
                firstName = attributes.getValue("firstname");
                middleName = attributes.getValue("middlename");
            }else
            if (qName.equals("group")){
                groupNumber = Integer.parseInt(attributes.getValue("number"));
            }else
            if (qName.equals("works")){
                works = new ArrayList<>(10);
                for (int i = 0; i<10; i++){
                    int workValue = Integer.parseInt(attributes.getValue("term"+(i+1)));
                    works.add(i, workValue);
                }
                studentArrayList.add(new Student(lastName,firstName,middleName,groupNumber,works));
            }
        }
    }

    public static ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }
}


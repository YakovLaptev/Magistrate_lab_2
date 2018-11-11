package program.utils;

import events.SpecialEvent;
import events.SpecialEventStatus;
import events.Creator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StAXGenerator {

    public List<SpecialEvent> parseXML(String xmlFileName) {
        List<SpecialEvent> specialEvents = new ArrayList<>();
        Generator generator = new Generator();
        String xmlAbsoluteFilePath = generator.getPath(xmlFileName);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            FileInputStream fileInputStream = new FileInputStream(xmlAbsoluteFilePath);
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileInputStream);
            String elementName;
            SpecialEvent specialEvent;
            int hierarchy = 1;
            do {
                XMLEvent xmlEvent = xmlEventReader.peek();
                switch (xmlEvent.getEventType()) {
                    case XMLStreamConstants.START_DOCUMENT:
                        hierarchy++;
                        break;
                    case XMLStreamConstants.START_ELEMENT:
                        hierarchy++;
                        elementName = xmlEvent.asStartElement().getName().toString();
                        elementName = elementName.substring(elementName.indexOf('}') + 1, elementName.length());
                        if (elementName.equals("specialEvent")) {
                            specialEvent = parseSpecialEvent(xmlEventReader);
                            specialEvents.add(specialEvent);
                            hierarchy--;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        hierarchy--;
                        break;
                    case XMLStreamConstants.END_DOCUMENT:
                        hierarchy--;
                        break;
                }
                xmlEventReader.nextEvent();
            } while (hierarchy > 1);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Error during parse XML: " + exception.getLocalizedMessage());
        }
        return specialEvents;
    }

    private SpecialEvent parseSpecialEvent(XMLEventReader xmlEventReader) throws XMLStreamException, ParseException {
        SpecialEvent specialEvent = new SpecialEvent();
        String elementName;
        XMLEvent xmlElementEvent;
        int hierarchy = 1;
        do {
            XMLEvent xmlEvent = xmlEventReader.peek();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    hierarchy++;
                    elementName = xmlEvent.asStartElement().getName().toString();
                    elementName = elementName.substring(elementName.indexOf('}') + 1, elementName.length());
                    switch (elementName) {
                        case "id":
                            xmlEventReader.nextEvent();
                            xmlElementEvent = xmlEventReader.nextEvent();
                            specialEvent.setId(xmlElementEvent.asCharacters().getData());
                            hierarchy--;
                            break;
                        case "eventName":
                            xmlEventReader.nextEvent();
                            xmlElementEvent = xmlEventReader.nextEvent();
                            specialEvent.setEventName(xmlElementEvent.asCharacters().getData());
                            hierarchy--;
                            break;
                        case "eventDate":
                            xmlEventReader.nextEvent();
                            xmlElementEvent = xmlEventReader.nextEvent();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d'T'H:m:s");
                            specialEvent.setEventDate(dateFormat.parse(xmlElementEvent.asCharacters().getData()));
                            hierarchy--;
                            break;
                        case "specialEventAbout":
                            xmlEventReader.nextEvent();
                            xmlElementEvent = xmlEventReader.nextEvent();
                            specialEvent.setSpecialEventAbout(xmlElementEvent.asCharacters().getData());
                            hierarchy--;
                            break;
                        case "specialEventCreator":
                            specialEvent.setSpecialEventCreator(parseCreator(xmlEventReader));
                            hierarchy--;
                            break;
                        case "specialEventStatus":
                            xmlEventReader.nextEvent();
                            xmlElementEvent = xmlEventReader.nextEvent();
                            specialEvent.setSpecialEventStatus(SpecialEventStatus.valueOf(xmlElementEvent.asCharacters().getData()));
                            hierarchy--;
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    hierarchy--;
                    break;
            }
            xmlEventReader.nextEvent();
        } while (hierarchy > 1);
        return specialEvent;
    }

    private Creator parseCreator(XMLEventReader xmlEventReader) throws XMLStreamException {
        Creator creator = new Creator();
        String elementName;
        XMLEvent xmlCreatorEvent;
        int hierarchy = 1;
        do {
            XMLEvent xmlEvent = xmlEventReader.peek();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    hierarchy++;
                    elementName = xmlEvent.asStartElement().getName().toString();
                    elementName = elementName.substring(elementName.indexOf('}') + 1, elementName.length());
                    switch (elementName) {
                        case "firstname":
                            xmlEventReader.nextEvent();
                            xmlCreatorEvent = xmlEventReader.nextEvent();
                            creator.setFirstname(xmlCreatorEvent.asCharacters().toString());
                            hierarchy--;
                            break;
                        case "middleName":
                            xmlEventReader.nextEvent();
                            xmlCreatorEvent = xmlEventReader.nextEvent();
                            creator.setMiddleName(xmlCreatorEvent.asCharacters().toString());
                            hierarchy--;
                            break;
                        case "lastname":
                            xmlEventReader.nextEvent();
                            xmlCreatorEvent = xmlEventReader.nextEvent();
                            creator.setLastname(xmlCreatorEvent.asCharacters().toString());
                            hierarchy--;
                            break;
                        case "rank":
                            xmlEventReader.nextEvent();
                            xmlCreatorEvent = xmlEventReader.nextEvent();
                            creator.setRank(Integer.valueOf(xmlCreatorEvent.asCharacters().toString()));
                            hierarchy--;
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    hierarchy--;
                    break;
            }
            xmlEventReader.nextEvent();
        } while (hierarchy > 1);
        return creator;
    }
}
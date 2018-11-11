package program;

import events.SpecialEvent;
import program.utils.Generator;
import program.utils.StAXGenerator;
import program.utils.XMLValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        XMLValidator xmlValidator = new XMLValidator();
        Generator generator = new Generator();
        StAXGenerator stAXGenerator = new StAXGenerator();

        if (xmlValidator.validateXMLSchema(
                generator.getPath("xml_for_xsd.xml"),
                generator.getPath("xml_schema.xsd"))) {
            List<SpecialEvent> specialEvents = stAXGenerator.parseXML("xml_for_xsd.xml");
            generator.generate(specialEvents);
        }
    }
}
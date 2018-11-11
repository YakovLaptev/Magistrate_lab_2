package program.utils;

import events.SpecialEvent;
import events.SpecialEventStatus;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Generator {

    public String getPath(String xmlFileName) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(xmlFileName)).getPath();
    }

    public void generate(List<SpecialEvent> specialEvents) {
        try {
            File file = new File("index.html");
            Document document = generateHTML();
            Element table = generateTable(document);
            fillTable(specialEvents, table);
            generateFooter(specialEvents, table);
            FileUtils.writeStringToFile(file, document.outerHtml(), "UTF-8");
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private Document generateHTML() {
        Document document = Jsoup.parse("<html></html>");
        document.head().appendElement("meta").attr("charset", "UTF-8");
        document.head().appendElement("link")
                .attr("rel", "stylesheet")
                .attr("href", "styles.css");
        return document;
    }

    private Element generateTable(Document document) {
        Element element = document.body().appendElement("table");
        Element tableTitles = element.appendElement("thead").appendElement("tr");
        tableTitles.appendElement("th").appendText("Id");
        tableTitles.appendElement("th").appendText("Дата");
        tableTitles.appendElement("th").appendText("Название");
        tableTitles.appendElement("th").appendText("Описание");
        tableTitles.appendElement("th").appendText("Создатель");
        tableTitles.appendElement("th").appendText("Статус");
        tableTitles.appendElement("th").appendText("Комментарий");
        return element;
    }

    private void fillTable(List<SpecialEvent> specialEvents, Element element) {
        Element tableBody = element.appendElement("tbody");
        Element tableRow;

        for (SpecialEvent specialEvent : specialEvents) {
            tableRow = tableBody.appendElement("tr");
            tableRow.appendElement("td").appendText(specialEvent.getId());
            tableRow.appendElement("td").appendText(new SimpleDateFormat("yyyy-M-d H:m").format(specialEvent.getEventDate()));
            tableRow.appendElement("td").appendText(specialEvent.getEventName());
            tableRow.appendElement("td").appendText(specialEvent.getSpecialEventAbout());
            tableRow.appendElement("td").appendText(specialEvent.getSpecialEventCreator().toString());
            tableRow.appendElement("td").appendText(String.valueOf(specialEvent.getSpecialEventStatus()));
            tableRow.appendElement("td").appendText(generateComment(specialEvent));
        }
    }

    private String generateComment(SpecialEvent specialEvent) {
        if(specialEvent.getSpecialEventStatus() == SpecialEventStatus.Stop) {
            return "Событие было приостановлено в " + new SimpleDateFormat("yyyy-M-d H:m").format(new Date());
        } else {
            return "";
        }
    }

    private void generateFooter(List<SpecialEvent> specialEvents, Element element) {
        Element footer = element.appendElement("tfoot");
        Element trFooter = footer.appendElement("tr");
        trFooter.appendElement("td").appendText("Всего событий:");
        trFooter.appendElement("td").appendText(String.valueOf(specialEvents.size()));
    }
}
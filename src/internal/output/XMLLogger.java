package internal.output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLLogger {
	private final XMLEventWriter eventWriter;
	private final XMLEventFactory eventFactory;

	private final XMLEvent end;
	private final XMLEvent tab;

	private int tabCount = 0;

	public XMLLogger(String fileName) throws FileNotFoundException, XMLStreamException {
		// Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// Create XMLEventWriter
		eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(fileName));
		// Create a EventFactory
		eventFactory = XMLEventFactory.newInstance();
		end = eventFactory.createDTD("\n");
		tab = eventFactory.createDTD("\t");
		// Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);

	}

	public void close() throws XMLStreamException {
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.flush();
		eventWriter.close();
	}

	public Attribute getAttribute(String name, double value) {
		return getAttribute(name, String.valueOf(value));
	}

	public Attribute getAttribute(String name, int value) {
		return getAttribute(name, String.valueOf(value));
	}

	public Attribute getAttribute(String name, String value) {
		return eventFactory.createAttribute(name, value);
	}

	public void openTag(String name, Iterator<Attribute> attributes) throws XMLStreamException {
		StartElement sElem = eventFactory.createStartElement("", "", name, attributes, null);
		openTag(sElem);
	}

	public void openTag(String name) throws XMLStreamException {
		StartElement sElem = eventFactory.createStartElement("", "", name);
		openTag(sElem);
	}

	private void openTag(StartElement sElem) throws XMLStreamException {
		tab();
		tabInc();
		eventWriter.add(sElem);
		eventWriter.add(end);
	}

	public void closeTag(String name) throws XMLStreamException {
		EndElement eElem = eventFactory.createEndElement("", "", name);
		tabDec();
		tab();
		eventWriter.add(eElem);
		eventWriter.add(end);
	}

	public void createNode(String name, int value) throws XMLStreamException {
		createNode(name, String.valueOf(value));
	}

	public void createNode(String name, String value) throws XMLStreamException {
		// Create Start and End node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		EndElement eElement = eventFactory.createEndElement("", "", name);

		tab();
		eventWriter.add(sElement);
		eventWriter.add(eventFactory.createCharacters(value));
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

	//TODO: Make this output <name /> instead of <name></name>
	public void createNode(String name) throws XMLStreamException {
		// Create Start and End node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		EndElement eElement = eventFactory.createEndElement("", "", name);

		tab();
		eventWriter.add(sElement);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

	/**************************************************************
	 *                                                            *
	 *                        INDENTATION                         *
	 *                                                            *
	 **************************************************************/

	private void tab() throws XMLStreamException {
		for (int i = 0; i < tabCount; i++)
			eventWriter.add(tab);
	}

	private void tabInc() {
		tabCount++;
	}

	private void tabDec() {
		tabCount--;
	}
}

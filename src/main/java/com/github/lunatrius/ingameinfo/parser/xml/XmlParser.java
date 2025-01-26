package com.github.lunatrius.ingameinfo.parser.xml;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.parser.IParser;
import com.github.lunatrius.ingameinfo.value.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlParser implements IParser {
	private Document document;

	@Override
	public boolean load(final InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.document = dBuilder.parse(inputStream);
		this.document.getDocumentElement().normalize();

		return true;
	}

	@Override
	public boolean parse(final Map<Alignment, List<List<Value>>> format) {
		if (this.document == null) {
			return false;
		}

		final Element documentElement = this.document.getDocumentElement();
		final NodeList nodeListLines = documentElement.getChildNodes();
		for (int i = 0; i < nodeListLines.getLength(); i++) {
			final Element elementLines = getElement(nodeListLines.item(i), "lines");
			if (elementLines != null) {
				final Alignment alignment = Alignment.parse(elementLines.getAttribute("at"));
				if (alignment != null) {
					format.put(alignment, getLines(elementLines));
				}
			}
		}

		return true;
	}

	private List<List<Value>> getLines(final Element element) {
		final List<List<Value>> listLines = new ArrayList<List<Value>>();

		final NodeList nodeListLine = element.getChildNodes();
		for (int i = 0; i < nodeListLine.getLength(); i++) {
			final Element elementLine = getElement(nodeListLine.item(i), "line");
			if (elementLine != null) {
				listLines.add(getValues(elementLine));
			}
		}

		return listLines;
	}

	private List<Value> getValues(final Element element) {
		final List<Value> values = new ArrayList<Value>();

		final NodeList nodeListValues = element.getChildNodes();
		for (int i = 0; i < nodeListValues.getLength(); i++) {
			final Element elementValue = getElement(nodeListValues.item(i));
			if (elementValue != null) {
				final String type = elementValue.getNodeName().equalsIgnoreCase("value")
						? elementValue.getAttribute("type")
						: elementValue.getNodeName();
				final Value value = Value.fromString(type);

				if (!value.isValid()) {
					continue;
				}

				if (value.isSimple()) {
					value.setRawValue(elementValue.getTextContent(), false);
				} else {
					value.values.addAll(getValues(elementValue));
				}
				values.add(value);
			}
		}

		return values;
	}

	private Element getElement(final Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			return (Element) node;
		}

		return null;
	}

	private Element getElement(final Node node, final String name) {
		final Element element = getElement(node);
		if (element != null && element.getNodeName().equalsIgnoreCase(name)) {
			return element;
		}

		return null;
	}
}

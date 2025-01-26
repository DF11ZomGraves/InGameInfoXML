package com.github.lunatrius.ingameinfo.parser;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.value.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IParser {
	public boolean load(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException;

	public boolean parse(Map<Alignment, List<List<Value>>> format);
}

package df11zomgraves.ingameinfo.parser;

import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.value.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IParser {
    public boolean load(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException;

    public boolean parse(Map<Alignment, List<List<Value>>> format);
}

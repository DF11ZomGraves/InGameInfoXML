package df11zomgraves.ingameinfo.printer;

import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.value.Value;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IPrinter {
    public boolean print(File file, Map<Alignment, List<List<Value>>> format);
}

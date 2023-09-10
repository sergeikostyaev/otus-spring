package spring.course.processor;

import java.io.IOException;
import java.util.List;

public interface FileProcessor {

    List<String> processFile() throws IOException;
}

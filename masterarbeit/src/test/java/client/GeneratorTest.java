package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GeneratorTest {

    public Generator generator;

    @Before
    public void setUp() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("database.yaml").getFile());
        try {
            Database database = mapper.readValue(file, Database.class);
            generator = new Generator(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

    }
}

package uz.pdp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        String readStringFromJsonFile = Files.readString(Path.of("people.json"));
        Gson gson = new Gson();
        Type type = new TypeToken<List<Person>>() {
        }.getType();
        List<Person> people = gson.fromJson(readStringFromJsonFile, type);

        String collectXML = people.stream()
                .collect(new FakeDataToXML());
        System.out.println(collectXML);
        Files.write(Path.of("people.xml"), collectXML.getBytes());

        String collectHTML = people.stream()
                .collect(new FakeDataToHTMLTable());
        System.out.println(collectHTML);
        Files.write(Path.of("xyz.html"), collectHTML.getBytes());
    }
}


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonFileWriter<T> {

    private final Type REVIEW_TYPE = new TypeToken<ArrayList<T>>() {
    }.getType();

    private final Gson gson = new Gson();
    FileHandler fileHandler = new FileHandler();

    public void WriteToJsonFile(ArrayList<T> list, String fileName)
    {
        String jsonList = gson.toJson(list);
        fileHandler.writeToFile("webservers.json", jsonList);
    }

    public ArrayList<T> ReadFromJson(String fileName) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(fileHandler.readFileAsReader(fileName));
        ArrayList<T> list = gson.fromJson(jsonReader, REVIEW_TYPE);
        return list;
    }
}

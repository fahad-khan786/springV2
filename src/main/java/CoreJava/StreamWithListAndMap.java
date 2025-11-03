package CoreJava;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamWithListAndMap {

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        String val = "fahad";

        map.put("fahad",89);
        map.put("yusuf",55);
        map.put("asma",99);
        map.put("ashif",12);

        Map<String,Integer> mapV2 = new HashMap<>();
        mapV2.put("sohail",44);
        mapV2.put("shahid",56);


        Map<String,Integer> res = map.entrySet().stream().filter(e -> e.getValue() >55).collect(Collectors.toMap(g -> g.getKey()+" passed",Map.Entry::getValue));

        res.entrySet().stream().forEach(System.out::println);

        //make a keys to uppercase
         map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toUpperCase(),Map.Entry::getValue)).entrySet().stream().forEach(System.out::println);

    }
}

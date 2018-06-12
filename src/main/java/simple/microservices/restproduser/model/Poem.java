package simple.microservices.restproduser.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Poem {

    private String username;

    private String data;

    private List<String> tags;

    public void addTag(String tag){
        tags.add(tag);
    }
}

package simple.microservices.restproduser.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Poem {

    private String username;

    private String data;

    private List<Tag> tags;

    public void addTag(Tag tag){
        tags.add(tag);
    }
}

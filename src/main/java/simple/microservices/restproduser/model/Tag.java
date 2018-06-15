package simple.microservices.restproduser.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @EqualsAndHashCode
@ToString
public class Tag {
    private String name;
}

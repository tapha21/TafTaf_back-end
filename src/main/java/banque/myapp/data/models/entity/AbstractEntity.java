package banque.myapp.data.models.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public abstract class AbstractEntity {
    @Id
    private String id;
}
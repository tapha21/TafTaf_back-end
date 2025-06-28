package banque.myapp.data.models.entity;

import banque.myapp.data.models.enums.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "transaction")
public class Transaction extends AbstractEntity {
   private double montant;
   private Date date;
   @DBRef(lazy = true)
   private User envoyeur;
   @DBRef(lazy = true)
   private User receveur;
   private TypeTransaction type;
}

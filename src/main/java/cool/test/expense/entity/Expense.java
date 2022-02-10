package cool.test.expense.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String description;

    private BigDecimal amount;

    private String category;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Account account;

    private Date createdAt;
    private Date updatedAt;
}

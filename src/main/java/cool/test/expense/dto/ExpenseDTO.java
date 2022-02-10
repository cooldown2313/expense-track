package cool.test.expense.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class ExpenseDTO {

    @Min(value = 0, message = "id cannot less than 0")
    private long id;

    @Size(max = 500, message = "description should not more than 500 characters")
    private String description;

    @DecimalMin(value = "0.00")
    @Digits(integer = 12, fraction = 2, message = "amount format must be in decimal format")
    private BigDecimal amount;

    @Size(max = 100, message = "category should not more than 100 characters")
    private String category;

    @NotBlank(message = "userId cannot be null or empty")
    private String userId;

    private Date createdAt;
    private Date updatedAt;
}

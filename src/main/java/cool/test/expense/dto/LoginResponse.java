package cool.test.expense.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    String userId;

    String jwtAccessToken;
}

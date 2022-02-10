package cool.test.expense.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.test.expense.dao.AccountDao;
import cool.test.expense.dto.AccountDTO;
import cool.test.expense.dto.LoginDTO;
import cool.test.expense.dto.LoginResponse;
import cool.test.expense.entity.Account;
import cool.test.expense.filter.JwtAuthorizationFilter;
import cool.test.expense.service.AccountService;
import cool.test.expense.util.StringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static String PWD_SEED = "z9X5m7KGgobSF0e483d0Wym3r26bMFGc0mrUt25k";

    @Autowired
    private Validator validator;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccountDTO saveAccount(AccountDTO accountDTO) {

        log.info("AccountServiceImpl saveAccount");
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AccountDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(accountDTO);

            log.debug("jsonString test is " + jsonString);

            Account account = modelMapper.map(accountDTO, Account.class);

            account = this.accountDao.save(account);

            log.debug("account test is " + account.getUserId());

            accountDTO = modelMapper.map(account, AccountDTO.class);

        } catch (JsonProcessingException e) {
            log.error("saveAccount exception is ", e);
        }

        return accountDTO;
    }

    @Override
    public boolean updatePassword(LoginDTO loginDTO) {

        AccountDTO  accountDTO = null;

        try {
            log.info("AccountServiceImpl updatePassword");
            Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<LoginDTO> constraintViolation : violations) {
                    sb.append(constraintViolation.getMessage());
                }
                throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(loginDTO);

            log.debug("userId is " + loginDTO.getUserId());

            Optional<Account> found = this.accountDao.findById(loginDTO.getUserId());

            if (found != null) {
                Account account = found.get();
                account.setPassword(StringUtils.hashPassword(loginDTO.getPassword(), PWD_SEED));

                this.accountDao.save(account);

                return true;
            }

        } catch (JsonProcessingException e) {
            log.error("saveAccount exception is ", e);
        } catch (Exception e) {
            log.error("saveAccount exception is ", e);
        }

        return false;
    }

    @Override
    public AccountDTO getAccount(String userId) {
        log.info("AccountServiceImpl getAccount");

        AccountDTO  accountDTO = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(accountDTO);

            log.debug("userId is " + userId);

            Optional<Account> found = this.accountDao.findById(userId);
            Account account = found!=null ? found.get(): null;

            accountDTO = modelMapper.map(account, AccountDTO.class);

            log.debug("accountDTO test is " + accountDTO);

        } catch (JsonProcessingException e) {
            log.error("saveAccount exception is ", e);
        }
        return accountDTO;
    }

    @Override
    public LoginResponse authenticate(LoginDTO loginDTO) {
        log.info("AccountServiceImpl authenticate");
        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<LoginDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        LoginResponse loginResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(loginDTO);

            log.debug("jsonString test is " + jsonString);

            Optional<Account> found = this.accountDao.findById(loginDTO.getUserId());
            if (found!=null) {
                Account account = found.get();
                if (StringUtils.hashPassword(loginDTO.getPassword(), PWD_SEED).equals(account.getPassword())) {
                    loginResponse = new LoginResponse();
                    loginResponse.setUserId(loginDTO.getUserId());
                    loginResponse.setJwtAccessToken(this.getJWTToken(loginDTO.getUserId()));
                }
            }
        } catch (JsonProcessingException e) {
            log.error("saveAccount exception is ", e);
        } catch (Exception e) {
            log.error("saveAccount exception is ", e);
        }

        return loginResponse;
    }

    private String getJWTToken(String username) {
        String secretKey = "jApPZDTNHmNx6NaBVhXa7oulC9aI6F8yH9xxlOre6ze5y9tn9pZAO2c44LnKRxWtN98sPbWJbaeLTWpwDlF5ssKJ5FDRK1sgfQV2";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("myExpenseTracker")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        JwtAuthorizationFilter.SECRET.getBytes()).compact();

        return "Bearer " + token;
    }
}
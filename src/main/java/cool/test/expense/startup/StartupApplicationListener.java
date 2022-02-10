package cool.test.expense.startup;

import cool.test.expense.dto.AccountDTO;
import cool.test.expense.dto.LoginDTO;
import cool.test.expense.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AccountService accountService;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

        this.createAccount("tatleong","Tat Leong");
        this.updatePassword("tatleong", "Zaq12wsx!");

        this.createAccount("steve","Steve");
        this.updatePassword("steve", "Zaq12wsx!");

        this.createAccount("ray","Ray");
        this.updatePassword("ray", "Zaq12wsx!");

        log.info("Expense-tracker startup successfully");
    }


    private void createAccount(String userId, String fullName) {
        AccountDTO dummyAccount = new AccountDTO();
        dummyAccount.setUserId(userId);
        dummyAccount.setFullName(fullName);
        dummyAccount.setCreatedAt(new Date(System.currentTimeMillis()));

        accountService.saveAccount(dummyAccount);
    }

    private void updatePassword(String userId, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(userId);
        loginDTO.setPassword(password);
        accountService.updatePassword(loginDTO);
    }
}
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

        AccountDTO dummyAccount = new AccountDTO();
        dummyAccount.setUserId("tatleong");
        dummyAccount.setFullName("Tat Leong");
        dummyAccount.setCreatedAt(new Date(System.currentTimeMillis()));

        accountService.saveAccount(dummyAccount);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(dummyAccount.getUserId());
        loginDTO.setPassword("Zaq12wsx!");
        accountService.updatePassword(loginDTO);

        log.info("Expense-tracker startup successfully");
    }
}
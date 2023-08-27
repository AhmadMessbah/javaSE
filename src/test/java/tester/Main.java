package tester;

import com.example.se13.model.entity.User;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

@Log4j
public class Main {
//    private static Logger logger = Logger.getLogger(tester.Main.class);

    public static void main(String[] args) {
        User user = User
                .builder()
                .userName("akbar")
                .password("mohsen123")
                .build();


        MDC.put("data",user.toString());
        MDC.put("action","EDIT");
        MDC.put("userId",1);

        log.info("USER EDIT");

        MDC.clear();
    }
}

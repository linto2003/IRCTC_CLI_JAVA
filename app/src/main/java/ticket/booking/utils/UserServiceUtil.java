package ticket.booking.utils;
import ticket.booking.entities.User;

import java.io.IOException;

public class UserServiceUtil {

    private User user;

    public static boolean checkPassword(String pass, String checkPass) throws IOException {
        return pass.equals(checkPass);
    }

}

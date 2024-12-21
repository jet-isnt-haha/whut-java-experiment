package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Browser extends AbstractUser
{
    Browser(String name, String password, String role) {
        super(name, password, role);
    }
}

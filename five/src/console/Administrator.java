package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

import static console.DataProcessing.*;

public class Administrator extends AbstractUser
{
    Administrator(String name, String password, String role) {
        super(name, password, role);
    }

}

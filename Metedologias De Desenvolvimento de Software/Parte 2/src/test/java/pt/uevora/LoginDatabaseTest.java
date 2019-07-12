package pt.uevora;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class LoginDatabaseTest
{
    LoginDatabase database = new LoginDatabase();

    @Before
    public void set_up()
    {
        database.addColaboradorToDB("C001", "211");
        database.addColaboradorToDB("C002", "222");
        database.addColaboradorToDB("C003", "233");
        database.addGestorToDB("G001", "101");
    }

    @After
    public void reset()
    {
        database = null;
    }

    @Test
    public void testVerifyCredentials()
    {

        Boolean condition = database.verifyCredentialsColaborador("C001", "211");

        assertTrue("Caso o utilizador exista a funcao deve retornar True", condition);
    }

    @Test
    public void testGetColaboratorData()
    {
        Colaborador temp = database.getColaboratorData("C002");

        assertEquals("222", temp.password);
    }

    @Test
    public void testGetGestorData()
    {
        Gestor temp = database.getGestorData("G001");

        assertEquals("101", temp.password);
    }
}
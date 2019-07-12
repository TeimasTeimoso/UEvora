package pt.uevora;

import java.text.*;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GestorTest
{
    Gestor g;

    @Before
    public void setUp()
    {
        g = new Gestor("001", "1");
    }

    @After
    public void reset()
    {
        g = null;
    }

    @Test
    public void getNameTest()
    {
        g.setName("Ruben");

        assertEquals("O nome é obtido! O getter e setter funcionam.", "Ruben", g.getName());
    }

    @Test
    public void getId()
    {
        assertEquals("O id é obtido! O getter funciona", "001", g.getId());
    }

    @Test
    public void getDias()
    {
        g.diasATirar -= 10;

        assertEquals("Ao retirar 10 dias aos 22 iniciais o resultado esperado será 12", 12, g.getDias());
    }

    @Test
    public void testeValidarData() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse("16/01/2019");

        assertEquals("A data tem que ser válida ", date.getTime(), g.validarData("16/01/2019").getTime());
    }
  

    @Test
    public void testeDataInvalida() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("32/01/2019");
        assertEquals("A data tem que ser válida", date1, g.validarData("32/01/2019"));
    }  

    @Test
    public void testeDiferencaDeDatasCorrecta() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("16/01/2019");
        Date date2 = format.parse("26/01/2019");

        assertEquals(11, g.diferencaDatas(date1, date2));
    }

    @Test
    public void testeDiferencaDeDatasErrada() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("16/01/2019");
        Date date2 = format.parse("26/01/2019");

        assertEquals(0, g.diferencaDatas(date2, date1));
    }

    @Test
    public void testeDiasUteis() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("21/06/2019");//sexta
        Date date2 = format.parse("24/06/2019");//segunda

        assertEquals(2, g.diasUteis(date1, date2));
    }

    @Test
    public void testPontuacao() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("30/06/2019");//sexta
        Date date2 = format.parse("02/07/2019");//segunda
        g.diasUteis(date1, date2);

        assertEquals("Como 30 é Domingo, só deverá retornar a pontuação dos 2 dias de Julho. 8 pontos", 8, g.pontuacao);
    }

    @Test
    public void testListarPedidos()
    {
        LoginDatabase db = new LoginDatabase();

        assertEquals("A base de dados está vazia!", false, g.listarPedidos(db));
    }
}
package pt.uevora;

import java.text.*;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColaboradorTest
{
    Colaborador colaborador;

    @Before
    public void setUp()
    {
        colaborador = new Colaborador("C001", "1");
    }

    @After
    public void reset()
    {
        colaborador = null;
    }

    @Test
    public void getNameTest()
    {
        colaborador.setName("Ruben");

        assertEquals("O nome é obtido! O getter e setter funcionam.", "Ruben", colaborador.getName());
    }

    @Test
    public void getId()
    {
        assertEquals("O id é obtido! O getter funciona", "001", colaborador.getId());
    }

    @Test
    public void getDias()
    {
        colaborador.diasATirar -= 10;

        assertEquals("Ao retirar 10 dias aos 22 iniciais o resultado esperado será 12", 12, colaborador.getDias());
    }

    @Test
    public void marcarFeriasTeste() throws ParseException
    {
        PedidoDeFerias p = colaborador.marcarFerias("01/01/2019", "03/01/2019");

        assertEquals("Pedido de Féria marcado com sucesso!", "C001010103011", p.getId());
    }

    @Test
    public void testeValidarData() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse("16/01/2019");

        assertEquals("A data tem que ser válida ", date.getTime(), colaborador.validarData("16/01/2019").getTime());
    }

    @Test
    public void testeDataInvalida() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("32/01/2019");
        assertEquals("A data tem que ser válida", date1, colaborador.validarData("32/01/2019"));
    }  

    @Test
    public void testeDiferencaDeDatasCorrecta() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("11/05/2019");
        Date date2 = format.parse("27/05/2019");

        assertEquals(17, colaborador.diferencaDatas(date1, date2));
    }

    @Test
    public void testeDiferencaDeDatasErrada() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("16/01/2019");
        Date date2 = format.parse("26/01/2019");

        assertEquals(0, colaborador.diferencaDatas(date2, date1));
    }

    @Test
    public void testeDiasUteis() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("11/05/2019");
        Date date2 = format.parse("27/05/2019");

        assertEquals(11, colaborador.diasUteis(date1, date2));
    }

    @Test
    public void testPontuacao() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("30/06/2019");//sexta
        Date date2 = format.parse("02/07/2019");//segunda
        colaborador.diasUteis(date1, date2);

        assertEquals("Como 30 é Domingo, só deverá retornar a pontuação dos 2 dias de Julho. 8 pontos", 8, colaborador.pontuacao);
    }

    @Test
    public void testPontuacao2() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = format.parse("27/06/2019");//sexta
        Date date2 = format.parse("03/07/2019");//segunda
        colaborador.diasUteis(date1, date2);

        assertEquals("2 dias uteis de Junho e 3 de Julho. 14 pontos", 18, colaborador.pontuacao);
    }
}
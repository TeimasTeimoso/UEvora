package pt.uevora;
import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PedidoDeFeriasTest
{
    PedidoDeFerias novoPedido;

    @Before
    public void set_up() throws ParseException
    {
        String dataInicio = "20/06/2019";
        String dataFim = "22/06/2019";
        novoPedido = new PedidoDeFerias(dataInicio, dataFim);
    }

    @After
    public void reset()
    {
        novoPedido = null;
    }

    @Test
    public void getEstadoTest()
    {
        novoPedido.setEstado("Aprovado");

        assertEquals("O pedido deverá estar aprovado", "Aprovado", novoPedido.getEstado());
    }

    @Test
    public void setID()
    {
        novoPedido.setId("111");

        assertEquals("Getter e setter funcionam", "111", novoPedido.getId());
    }

    
}
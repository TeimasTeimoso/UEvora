package pt.uevora;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class LoginDatabase
{

    //Lista onde estão todos os dados do respetivo utilizador;
    List<Colaborador> database;
    List<Gestor> databaseG;
    //Iterador da lista de colaboradores
    

    public LoginDatabase()
    {
        this.database = new LinkedList();
        this.databaseG = new LinkedList();
    }

    //Adiciona usarios na base de dados
    public void addColaboradorToDB(String user, String password)
    {

        Colaborador temp = new Colaborador(user, password);
        this.database.add(temp);
    }

    //Verifca se usarios existem
    public boolean verifyCredentialsColaborador(String user, String password)
    {
        Iterator<Colaborador> iterator = this.database.iterator();

        while(iterator.hasNext())
        {
            Colaborador current = iterator.next();
            if(current.getId().equals(user) && current.getPassword().equals(password))
                return true;
        }
        return false;

    }

    public Colaborador getColaboratorData(String user)
    {
        Iterator<Colaborador> iterator = this.database.iterator();
        while(iterator.hasNext())
        {
            Colaborador temp = iterator.next();
            if(temp.getId().equals(user))
            {
                return temp;
            }
        }
        //Não é suposto chegar aqui
        return null;
    }

    //Adiciona usarios na base de dados
    public void addGestorToDB(String user, String password)
    {

        Gestor temp = new Gestor(user, password);
        this.databaseG.add(temp);
    }

    //Verifca se usarios existem
    public boolean verifyCredentialsGestor(String user, String password)
    {
        Iterator<Gestor> iterator = this.databaseG.iterator();

        while(iterator.hasNext())
        {
            Gestor current = iterator.next();
            if(current.getId().equals(user) && current.getPassword().equals(password))
                return true;
        }
        return false;

    }

    public Gestor getGestorData(String user)
    {
        Iterator<Gestor> iterator = this.databaseG.iterator();
        while(iterator.hasNext())
        {
            Gestor temp = iterator.next();
            if(temp.getId().equals(user))
            {
                return temp;
            }
        }
        //Não é suposto chegar aqui
        return null;
    }
    
}
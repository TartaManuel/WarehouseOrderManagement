package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class AbstractDAO<T> {
    protected static final Logger LOGGER=Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type=(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectAllQuery(){
        StringBuilder sb=new StringBuilder(); //metoda care creeaza query-ul de select al unui obiect in functie de un
        //field dat ca parametru, simplu
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createSelectQuery(String field){
        StringBuilder sb=new StringBuilder(); //metoda care creeaza query-ul de select al unui obiect in functie de un
        //field dat ca parametru, simplu
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE "+field+" =?");
        return sb.toString();
    }

    public List<T> findAll() { //metoda care selecteaza o linie anume dintr-o tabela in functie de id
        // System.out.println(type.getSimpleName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query); //stabilim conexiunile si dam executeQuery
            resultSet = statement.executeQuery();
            //System.out.println(statement.toString());

            return createObjects(resultSet);//o sa gasim doar un obiect cu acel id in tabela deci returnam doar
            //primul element din lista
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }catch(Exception e){
            return null;
        } finally {
            ConnectionFactory.close(resultSet); //inchidem conexiunile dupa ce am facut operatia
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) { //metoda care selecteaza o linie anume dintr-o tabela in functie de id
       // System.out.println(type.getSimpleName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id"); //cream query-ul in functie de id
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query); //stabilim conexiunile si dam executeQuery
            statement.setInt(1, id); //setam parametrul query-ului ca parametrul dat de noi
            resultSet = statement.executeQuery();
            //System.out.println(statement.toString());

            return createObjects(resultSet).get(0);//o sa gasim doar un obiect cu acel id in tabela deci returnam doar
            //primul element din lista
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }catch(Exception e){
            return null;
        } finally {
            ConnectionFactory.close(resultSet); //inchidem conexiunile dupa ce am facut operatia
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance(); //instance e de tipul T, care va fi tipul clasei pentru care apelam
                //metodele (una dintre Client, Product sau Order)
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());//obtinem valoarea fieldului actual
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    //pentru metodele de get si set ale campurilor
                    //newinstance creeaza obiectul cu constructorul fara parametrii si dupa noi apelam metoda de set
                    //ca sa setam parametrii cu valorile dorite
                    Method method = propertyDescriptor.getWriteMethod(); //aici punem metoda de set in method
                    method.invoke(instance, value);//aici o invocam pe obiectul dorit, cu valoarea dorita
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createInsertQuery(){
        //query-ul pentru insert
        StringBuilder sb=new StringBuilder();
        sb.append("Insert into " + type.getSimpleName() + " (");
        int i=0;
        for(Field field:type.getDeclaredFields()){
            field.setAccessible(true);
            if(i!=0) { //pentru ca nu o sa inserez id-ul(este autoincrement), sar peste primul field din clase
                if (i > 1) {
                    sb.append("," + field.getName()); //construiesc query-ul
                } else {
                    sb.append(field.getName());
                }
            }
            i++;
        }
        sb.append(") values (");
        sb.append("?");
        for(int j=1;j<type.getDeclaredFields().length-1;j++){
            sb.append(",?");
        }
        sb.append(")");
        return sb.toString();
    }

    //metoda de insert
    public int insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId=-1;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            int i=1;
            for(Field field:t.getClass().getDeclaredFields()){//parcurgem fieldurile
                field.setAccessible(true);
                if(field.getName().equals("id"))//in query am sarit peste id deci si aici trebuie sa fac asta
                    continue;
                try {
                    //nu stiu ce tip e field-ul respectiv asa ca folosesc setObject
                    statement.setObject(i,field.get(t));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                i++;

            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) { //cu rs.nex() iteram prin rezultatele returnate
                //fiecare rs reprezinta un row in tabela
                insertedId = resultSet.getInt(1); //extragem id-ul obiectului inserat pentru ca il returnam dupa
                //daca id-ul ramane -1 inseamna ca nu am putut insera
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return insertedId;
    }

    private String createDeleteQuery(String field){
        StringBuilder sb=new StringBuilder();
        sb.append("Delete from ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE "+field+" =?");
        return sb.toString();
    }

    public int delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id); //avem doar un parametru de setat, id-ul obiectului pe care vrem sa il
            //stergem din tabela
            statement.executeUpdate();

            return 1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }catch(Exception e){
            return -1;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    private String createUpdateQuery(Field[] field){
        StringBuilder sb=new StringBuilder();
        sb.append("Update ");
        sb.append(type.getSimpleName());
        sb.append(" set ");
        int i=0;
        for(Field f:field){
            if(i>0) {
                sb.append(", " + f.getName() + "=?");
            }
            else{
                sb.append(f.getName() + "=?");
            }
            i++;
        }
        sb.append(" WHERE "+field[0].getName()+" =?");
        return sb.toString();
    }

    public void update(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(t.getClass().getDeclaredFields());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i=1;
            for(Field field:t.getClass().getDeclaredFields()){//parcurgem fieldurile
                field.setAccessible(true);
                try {
                    if(i==1){
                        statement.setObject(t.getClass().getDeclaredFields().length+1,field.get(t));
                    }
                    //nu stiu ce tip e field-ul respectiv asa ca folosesc setObject
                    statement.setObject(i,field.get(t));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                i++;

            }
            statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
    }
}

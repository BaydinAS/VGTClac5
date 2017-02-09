import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Created by Аlexander
 * on 16.10.2016.
 */

public class DataBase {
    private static Connection connection = null;
    private static Statement statement = null;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:VGTDataBase.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] getPaintsTypes() {
        try {
            HashSet<String> paintsTypeArrayList = new HashSet<>();
            String sql = "SELECT Type FROM Paints"; // запрос
            ResultSet resultSet = statement.executeQuery(sql); // сохранение запроса в resultSet
            while (resultSet.next()) {// разобрать пришедшую таблицу на куски
                paintsTypeArrayList.add(resultSet.getString("Type"));
            }
            return paintsTypeArrayList.toArray(new String[paintsTypeArrayList.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getPaintsNamesByType(String type) {
        try {
            HashSet<String> paintsNameArrayList = new HashSet<>();
            String sql = "SELECT Name FROM Paints WHERE Type = '" + type + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                paintsNameArrayList.add(resultSet.getString("Name"));
            }
            return paintsNameArrayList.toArray(new String[paintsNameArrayList.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


/*    public static String[] getPaintsNamesByType(String type) {
        try {
            ArrayList<String> paintsNameArrayList = new ArrayList<>();
            String sql = "SELECT Name, Kg FROM Paints WHERE Type = '" + type + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                paintsNameArrayList.add(resultSet.getString("Name") + ", " + resultSet.getFloat("Kg") + " кг");
            }
            return paintsNameArrayList.toArray(new String[paintsNameArrayList.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

}

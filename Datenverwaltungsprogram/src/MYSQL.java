import java.sql.*;
import java.util.ArrayList;

public class MYSQL {
private static String url = "jdbc:mysql://";
private static String host = "127.0.0.1";
private static int port = 3306;
private static String database = "projektdb";
private String errormessage;
private String benutzername = "root";
private String password = "1111";
private Connection con;

public MYSQL( String host,int port,String database) {
	this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?allowPublicKeyRetrieval=true&useSSL=false";
	this.host = host;
	this.port = port;
	this.database = database;
	this.errormessage = null;
	
	
	
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    con = DriverManager.getConnection(this.url, benutzername, password);
	    System.out.println("Verbindung erfolgreich!");
	} catch (ClassNotFoundException e) {
	    System.err.println("Fehler: MySQL JDBC-Treiber nicht gefunden!");
	    e.printStackTrace();
	} catch (SQLException e) {
	    System.err.println("Fehler: Verbindung zur Datenbank fehlgeschlagen!");
	    e.printStackTrace();
	}
}

public void disConnect() {
try {
	con.close();
	System.out.println("Die Verbindung wurde unterbrochen");
}catch(SQLException e) {
	e.printStackTrace();
}
}

public void Update(String qry) {
	try {
		PreparedStatement ps = con.prepareStatement(qry);
		ps.execute();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void executeStatement(String pSQL) {
    try (Statement statement = con.createStatement()) {
        boolean hasResultSet = statement.execute(pSQL);

        if (hasResultSet) {
            try (ResultSet resultSet = statement.getResultSet()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + "\t ");
                }
                System.out.println();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(resultSet.getString(i) + "\t ");
                    }
                    System.out.println();
                }
            }
        } else {
            int updateCount = statement.getUpdateCount();
            System.out.println("Statement ausgeführt. " + updateCount + " Zeilen betroffen.");
        }
    } catch (SQLException e) {
        System.out.println("Fehler bei der Ausführung des Statements: " + e.getMessage());
    }
}

public ArrayList<String> getExecutedStatement(String query) {
    ArrayList<String> klassenListe = new ArrayList<>();
    try (Statement statement = con.createStatement();
         ResultSet rs = statement.executeQuery(query)) {
        while (rs.next()) {
            klassenListe.add(rs.getString("Klassename"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return klassenListe;
}

public String[] getDaten(String query, String columnName) {
    ArrayList<String> Daten = new ArrayList<>();
    try (Statement statement = con.createStatement();
         ResultSet rs = statement.executeQuery(query)) {

        while (rs.next()) {
            Daten.add(rs.getString(columnName));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return Daten.toArray(new String[0]);
}

public void setNewSchueler(String query) {
	try {
		Statement statement = con.createStatement();
		boolean hasResultSet = statement.execute(query);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public int getDatenInt(String query, String columnName) {
    ArrayList<Integer> Daten = new ArrayList<>();
    try (Statement statement = con.createStatement();
         ResultSet rs = statement.executeQuery(query)) {

        while (rs.next()) {
            Daten.add(rs.getInt(columnName));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    int data = Daten.get(0);
    System.out.println("HÖchste ID: " + data);
    
    return data;
}
}
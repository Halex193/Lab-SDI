import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUnitXMLGenerator
{
    public static void main(String[] args) throws DatabaseUnitException, IOException, SQLException
    {
        //java.sql
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MovieRentalDB", "postgres", "admin");
        //org.dbunit.database
        IDatabaseConnection connection = new DatabaseConnection(conn);
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("client", " SELECT * FROM client");
        partialDataSet.addTable("app_user", " SELECT * FROM app_user");
        partialDataSet.addTable("movie", " SELECT * FROM movie");
        partialDataSet.addTable("rental", " SELECT * FROM rental");
        //org.dbunit.dataset.xml
        FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream("db-data.xml"));
        datasetWriter.write(partialDataSet);
    }
}

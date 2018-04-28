/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCAbstractDAO {

    private Connection connection;

    public JDBCAbstractDAO(Connection connection) {
        this.connection = connection;
    }

    protected PreparedStatement prepare(String sql) throws SQLException {

        return connection.prepareStatement(sql);
    }
}

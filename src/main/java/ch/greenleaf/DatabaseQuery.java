package ch.greenleaf;

import java.sql.*;
import java.util.*;

public class DatabaseQuery {

    private final String table;
    private final List<String> selectFields = new ArrayList<>();
    private final List<String> whereClauses = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private final Map<String, Object> updateFields = new LinkedHashMap<>();

    // Default query type
    private QueryType type = QueryType.SELECT;

    /**
     * The possible query types that can be executed
     */
    private enum QueryType {
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }

    /**
     * Execute a query on the database
     * @param table The database table to execute the query on
     */
    private DatabaseQuery(String table) {
        this.table = table;
    }

    /**
     * Define the query as a select
     * @param fields All fields to be selected
     * @return The database query
     */
    public DatabaseQuery select(String... fields) {
        selectFields.addAll(Arrays.asList(fields));
        type = QueryType.SELECT;
        return this;
    }

    /**
     * Add a where clause to the query
     * @param column The database column name
     * @param operator The operator to use
     * @param value The value to check for
     * @return The database query
     */
    public DatabaseQuery where(String column, String operator, Object value) {
        whereClauses.add(column + " " + operator + " ?");
        params.add(value);
        return this;
    }

    /**
     * Define the query as an insert
     * @param fieldName The field name to insert into
     * @param value The value to insert into the field
     * @return The database query
     */
    public DatabaseQuery insert(String fieldName, Object value) {
        type = QueryType.INSERT;
        updateFields.put(fieldName, value);
        return this;
    }

    /**
     * Define the query as an update
     * @param fieldName The field name to update from
     * @param value The value to update the field to
     * @return The database query
     */
    public DatabaseQuery update(String fieldName, Object value) {
        type = QueryType.UPDATE;
        updateFields.put(fieldName, value);
        return this;
    }

    /**
     * Define the query as a <b>destructive</b> delete
     * @return The database query
     */
    public DatabaseQuery delete() {
        type = QueryType.DELETE;
        return this;
    }

    /**
     * Execute the database query
     * @return The database results
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {
        Connection conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement(buildSQL());
        setParameters(stmt);
        return stmt.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        Connection conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement(buildSQL());
        setParameters(stmt);
        return stmt.executeUpdate();
    }

    /**
     * Builds the SQl query depending on the query type
     * @return The built query statement
     */
    private String buildSQL() {
        return switch (type) {
            case SELECT -> buildSelect();
            case INSERT -> buildInsert();
            case UPDATE -> buildUpdate();
            case DELETE -> buildDelete();
        };
    }

    /**
     * Build a select query
     * @return The select query
     */
    private String buildSelect() {
        String fields = selectFields.isEmpty() ? "*" : String.join(", ", selectFields);
        String sql = "SELECT " + fields + " FROM " + table;
        if (!whereClauses.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", whereClauses);
        }
        return sql;
    }

    /**
     * Build an insert query
     * @return The insert query
     */
    private String buildInsert() {
        String cols = String.join(", ", updateFields.keySet());
        String qs = String.join(", ", Collections.nCopies(updateFields.size(), "?"));
        return "INSERT INTO " + table + " (" + cols + ") VALUES (" + qs + ")";
    }

    /**
     * Build an update query
     * @return The update query
     */
    private String buildUpdate() {
        String setPart = String.join(", ",
                updateFields.keySet().stream().map(key -> key + " = ?").toList()
        );
        String sql = "UPDATE " + table + " SET " + setPart;
        if (!whereClauses.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", whereClauses);
        }
        return sql;
    }

    /**
     * Build a delete query
     * @return The delete query
     */
    private String buildDelete() {
        String sql = "DELETE FROM " + table;
        if (!whereClauses.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", whereClauses);
        }
        return sql;
    }

    /**
     * Insert the parameters in the prepared query statement
     * @param stmt The query statement
     * @throws SQLException
     */
    private void setParameters(PreparedStatement stmt) throws SQLException {
        int i = 1;
        for (Object value : updateFields.values()) {
            stmt.setObject(i++, value);
        }
        for (Object value : params) {
            stmt.setObject(i++, value);
        }
    }
}


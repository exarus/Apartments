package ua.kiev.prog;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Loads the data to DB.
 * Created by User on 09.07.2014.
 */
public class ApartmentDatabase implements AutoCloseable {

    public static ApartmentDatabase getInstance() {
        return INSTANCE;
    }

    public void add(Apartment apartment) throws SQLException {
        addStatement.setString(1, apartment.getDistrict());
        addStatement.setString(2, apartment.getAddress());
        addStatement.setFloat(3, apartment.getArea());
        addStatement.setByte(4, apartment.getRoomCount());
        addStatement.setDouble(5, apartment.getPrice());
        addStatement.setDate(6, apartment.getCreatedDate());
        addStatement.execute();
        addStatement.clearParameters();
    }

    public List<Apartment> select(ApartmentSelector selector) throws SQLException
    {
        if (selector.getDistrict() == null) {
            selectStatement.setString(1, "ANY");
        } else {
            selectStatement.setString(1, selector.getDistrict());
        }
        selectStatement.setFloat(2, selector.getMinArea());
        selectStatement.setFloat(3, selector.getMaxArea());
        selectStatement.setByte(4, selector.getRoomCount());
        selectStatement.setDouble(5, selector.getMinPrice());
        selectStatement.setDouble(6, selector.getMaxPrice());

        ResultSet set = selectStatement.executeQuery();
        return getApartmentList(set);
    }


    @Override
    public void close() throws SQLException {
        if (addStatement != null) {
            addStatement.close();
        }
        if (selectStatement != null) {
            selectStatement.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    // Statistics

    public Set<String> getDistrictSet() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT district FROM apartments;");
        HashSet<String> set = new HashSet<>(resultSet.getFetchSize());

        while (resultSet.next()) {
            set.add(resultSet.getString("district"));
        }
        return set;
    }

    public double getMaxPrice() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MAX(price) AS price FROM apartments;");
        set.next();
        return set.getDouble(1);
    }

    public double getMinPrice() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MIN(price) AS price FROM apartments;");
        set.next();
        return set.getDouble(1);
    }

    public float getMaxArea() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MAX(area) AS area FROM apartments;");
        set.next();
        return set.getFloat(1);
    }

    public float getMinArea() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MIN(area) AS area FROM apartments;");
        set.next();
        return set.getFloat(1);
    }

    public byte getMaxRoomCount() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MAX(room_count) AS room_count FROM apartments;");
        set.next();
        return set.getByte(1);
    }

    public byte getMinRoomCount() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT MIN(room_count) AS room_count FROM apartments;");
        set.next();
        return set.getByte(1);
    }

    private List<Apartment> getApartmentList(ResultSet set) throws SQLException {
        List<Apartment> list = new ArrayList<>(set.getFetchSize());

        while (set.next()) {
            String district = set.getString("district");
            String address = set.getString("address");
            float area = set.getFloat("area");
            byte roomCount = set.getByte("room_count");
            double price = set.getDouble("price");
            Date createdDate = set.getDate("created_date");
            list.add(new Apartment(district, address, area,
                    roomCount, price, createdDate));
        }
        return list;
    }

    // Initialize

    private ApartmentDatabase() throws SQLException, ClassNotFoundException {
        this(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

    private ApartmentDatabase(String connectionInfo, String user, char[] password)
            throws SQLException, ClassNotFoundException {
        this.connectionInfo = connectionInfo;
        this.user = user;
        this.password = password;
        dbConnection = getDBConnection();
        statement = dbConnection.createStatement();
        addStatement = dbConnection.prepareStatement(addSQL);
        selectStatement = dbConnection.prepareStatement(selectSQL);

        createTable();
    }

    private void createTable() throws SQLException {
        // ��������� SQL ������
        statement.execute(createTableSQL);
    }

    private void dropTable() throws SQLException {
        this.statement.execute("DROP TABLE apartments;");
    }

    private Connection getDBConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("No driver class found.");
        }

        Connection dbConnection = DriverManager.getConnection
                (connectionInfo, user, new String(password));
        if (!Arrays.equals(password, DB_PASSWORD)) {
            for (int i = 0; i < password.length; i++) {
                password[i] = '\0';
            }
        }
        return dbConnection;
    }

    // Cache
//    private byte maxRoomCount;
//    private byte minRoomCount;
//    private float minArea;
//    private float maxArea;
//    private double minPrice;
//    private double maxPrice;
//    private List<String> districtList;


    // Connection

    private final static ApartmentDatabase INSTANCE;
    private final Connection dbConnection;
    private final PreparedStatement addStatement;
    private final PreparedStatement selectStatement;
    private final Statement statement;

    // SQLs

    private static final String selectSQL = "SELECT * FROM apartments " +
            "WHERE (district = ?) AND (area >= ?) AND (area <= ?) " +
            "AND (room_count = ?) AND (price >= ?) AND (price <= ?) " +
            "ORDER BY price;";
    private static final String addSQL = "INSERT INTO apartments" +
            "(district, address, area, room_count, price, created_date) " +
            "VALUES(?, ?, ?, ?, ?, ?);";
    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS apartments (" +
            "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "district VARCHAR(20) NOT NULL, " +
            "address VARCHAR(255) NOT NULL, " +
            "area FLOAT (150, 2) UNSIGNED NOT NULL, " +
            "room_count TINYINT UNSIGNED NOT NULL, " +
            "price REAL (11, 2) UNSIGNED NOT NULL, " +
            "created_date DATE NOT NULL);";

    // Strings

    private final String connectionInfo;
    private final String user;
    private final char[] password;

    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/db";
    private static final String DB_USER = "root";
    private static final char []DB_PASSWORD = "thunder0bolt".toCharArray();

    static {
        ApartmentDatabase database = null;
        try {
            database = new ApartmentDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        INSTANCE = database;
    }
}

package hospitalmanagementsys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MYSQLConnection {

    private String username = "root";
    private String password = "";
    private String dbName = "hospital_db";
    private String host = "localhost";
    private int port = 3306;

    private Connection connection = null;
    private static MYSQLConnection instance = null;

    private MYSQLConnection() {
        this.connection = getConnection();
    }

    public static MYSQLConnection getInstance() {
        if (instance == null) {
            synchronized (MYSQLConnection.class) {
                if (instance == null) {
                    instance = new MYSQLConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Not Found");
            ex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        } catch (SQLException ex) {
            System.out.println("Connection Unsuccessful: " + ex.getMessage());
            ex.printStackTrace();
        }
        return connection;
    }

    public void addPatient(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot add patient.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePatient(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot update patient.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDoctor(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot update doctor.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAppointment(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot update appointment.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAppointment(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot delete appointment.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAppointment(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot add appointment.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDoctor(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot add doctor.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addHospital(String query) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot add hospital.");
            return;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean controlPatientLogin(String id, String password) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot check patient login.");
            return false;
        }
        String sql = "SELECT patient_id, patient_password FROM patient_table";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String patientId = rs.getString("patient_id");
                String patientPassword = rs.getString("patient_password");
                if (patientId.equals(id) && patientPassword.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean controlDoctorLogin(String id, String password) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot check doctor login.");
            return false;
        }
        String sql = "SELECT doctor_id, doctor_password FROM doctor_table";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String doctorId = rs.getString("doctor_id");
                String doctorPassword = rs.getString("doctor_password");
                if (doctorId.equals(id) && doctorPassword.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void showHospitals(JComboBox box) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot show hospitals.");
            return;
        }
        String sql = "SELECT hospital_name FROM hospital_table";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String item = rs.getString("hospital_name");
                box.addItem(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showDoctors(JComboBox doctorBox, JComboBox hospitalBox, JComboBox departmentBox) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot show doctors.");
            return;
        }
        String sql = "SELECT doctor_name, doctor_surname FROM doctor_table WHERE doctor_hospital='"
                + hospitalBox.getSelectedItem() + "' AND doctor_department='" + departmentBox.getSelectedItem() + "'";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            doctorBox.removeAllItems();
            while (rs.next()) {
                String doctorName = rs.getString("doctor_name");
                String doctorSurname = rs.getString("doctor_surname");
                doctorBox.addItem(doctorName + " " + doctorSurname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String findDoctorID(JComboBox doctorBox, JComboBox hospitalBox, JComboBox departmentBox, JComboBox nameBox) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot find doctor ID.");
            return null;
        }
        String sql = "SELECT doctor_id, doctor_name, doctor_surname FROM doctor_table WHERE doctor_hospital='"
                + hospitalBox.getSelectedItem() + "' AND doctor_department='" + departmentBox.getSelectedItem() + "'";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String doctorName = rs.getString("doctor_name");
                String doctorSurname = rs.getString("doctor_surname");
                if (nameBox.getSelectedItem().equals(doctorName + " " + doctorSurname)) {
                    return rs.getString("doctor_id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void showAppointments(JTable table) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot show appointments.");
            return;
        }
        String sql = "SELECT * FROM appointment_table WHERE patient_id='" + LoginScreen.patientid + "'";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (rs.next()) {
                String patientId = rs.getString("patient_id");
                String hospitalName = rs.getString("hospital_name");
                String doctorName = rs.getString("doctor_name");
                Object[] row = {patientId, hospitalName, doctorName};
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showDoctorAppointments(JTable table) {
        if (connection == null) {
            System.out.println("No database connection available! Cannot show doctor appointments.");
            return;
        }
        String sql = "SELECT * FROM patient_table WHERE patient_id IN (SELECT patient_id FROM appointment_table WHERE doctor_id='"
                + LoginScreen.doctorid + "')";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (rs.next()) {
                String patientId = rs.getString("patient_id");
                String patientName = rs.getString("patient_name");
                String patientSurname = rs.getString("patient_surname");
                Object[] row = {patientId, patientName, patientSurname};
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showDepartments(JComboBox box) {
        String[] departments = {
            "Cardiology", "Dermatology", "Endocrinologist", "Hematologist",
            "Neurologist", "Oncologist", "Pathologists", "Physiatrist"
        };
        for (String department : departments) {
            box.addItem(department);
        }
    }
}

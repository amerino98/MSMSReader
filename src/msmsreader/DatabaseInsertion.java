/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msmsreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Álvaro Merino
 */
public class DatabaseInsertion {

    public Statement statement;
    public Connection c;
    public ResultSet rs;

    public void connect() {
        try {
            // Open database connection
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.c = DriverManager.getConnection("jdbc:mysql://localhost/msms?useSSL=true&serverTimezone=UTC", "root", "131427amm");// this connects to
            // the
            // database

            System.out.println("Database connection opened.");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection() {
        return c;
    }

    public ResultSet getRs() {
        return rs;
    }

    public ResultSet executeQuery(String query) {
        DatabaseInsertion e = new DatabaseInsertion();
        try {
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {

        }
        return null;
    }

    public void disconnect() {
        try {
            c.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param a
     * @return the compound_id or -1 if not exists
     */
    public int checkInchi(Compound a) {

        try {
            String inchi = a.inchikey;
            String s = "SELECT compound_id FROM compound_identifiers  WHERE inchi_key=?";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setString(1, inchi);
            rs = ps.executeQuery();
            while (rs.next()) {
                int compound_id = rs.getInt("compound_id");
                return compound_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int checkMsms(Compound j, Integer a) {

        try {

            String s = "SELECT msms_id FROM msms  WHERE compound_id=? AND voltage=? AND ionization_mode=? AND peak_count=? AND predicted=? ;";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setInt(1, a);
            int voltage = 20;

            ps.setInt(2, voltage);
            if (j.ionMode.equals("+")) {
                int ion_mode = 1;
                ps.setInt(3, ion_mode);
            } else {
                int ion_mode = 2;
                ps.setInt(3, ion_mode);
            }
            ps.setInt(4, j.numPeaks);
            int predicted = 1;
            ps.setInt(5, predicted);
            rs = ps.executeQuery();
            while (rs.next()) {
                int msms_id = rs.getInt("msms_id");
                return msms_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int checkMsms2(Compound j, Integer a) {

        try {

            String s = "SELECT msms_id FROM msms  WHERE compound_id=? AND voltage=? AND ionization_mode=? AND predicted=? ;";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setInt(1, a);
            int voltage = 20;

            ps.setInt(2, voltage);
            if (j.ionMode.equals("+")) {
                int ion_mode = 1;
                ps.setInt(3, ion_mode);
            } else {
                int ion_mode = 2;
                ps.setInt(3, ion_mode);
            }

            int predicted = 1;
            ps.setInt(4, predicted);
            rs = ps.executeQuery();
            while (rs.next()) {
                int msms_id = rs.getInt("msms_id");
                return msms_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param a
     * @param j
     */
    public int InsertCompoundMsms(Compound a, Integer j) {
        try {
            int b = chekLastMsmsId() + 1;
            if (b != -1) {
                if (checkMsms(a, j) == -1) {
                    String sql = "INSERT into msms (msms_id,voltage,voltage_level,ionization_mode,peak_count,compound_id,predicted)" + " VALUES(?,?,?,?,?,?,?);";
                    PreparedStatement ps = c.prepareStatement(sql);
                    int voltage = 20;
                    ps.setInt(1, b);
                    ps.setInt(2, voltage);
                    String voltage_level;
                    if (voltage < 15) {
                        voltage_level = "low";
                        ps.setString(3, voltage_level);
                    }
                    if (voltage > 30) {
                        voltage_level = "high";
                        ps.setString(3, voltage_level);
                    }
                    if (voltage >= 15 && voltage <= 30) {
                        voltage_level = "med";
                        ps.setString(3, voltage_level);
                    }

                    if (a.ionMode.equals("+")) {
                        int ion_mode = 1;
                        ps.setInt(4, ion_mode);
                    } else {
                        int ion_mode = 2;
                        ps.setInt(4, ion_mode);
                    }
                    ps.setInt(5, a.numPeaks);
                    ps.setInt(6, j);
                    int predicted = 1;
                    ps.setInt(7, predicted);
                    ps.executeUpdate();
                    ps.close();
                    return b;
                }
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return -1;
    }

    public int InsertCompoundMsms2(Compound a, Integer j) {
        try {
            int b = chekLastMsmsId() + 1;
            if (b != -1) {
                if (checkMsms2(a, j) == -1) {
                    String sql = "INSERT into msms (msms_id,voltage,voltage_level,ionization_mode,peak_count,compound_id,predicted)" + " VALUES(?,?,?,?,?,?,?);";
                    PreparedStatement ps = c.prepareStatement(sql);
                    int voltage = 20;
                    ps.setInt(1, b);
                    ps.setInt(2, voltage);
                    String voltage_level;
                    if (voltage < 15) {
                        voltage_level = "low";
                        ps.setString(3, voltage_level);
                    }
                    if (voltage > 30) {
                        voltage_level = "high";
                        ps.setString(3, voltage_level);
                    }
                    if (voltage >= 15 && voltage <= 30) {
                        voltage_level = "med";
                        ps.setString(3, voltage_level);
                    }

                    if (a.ionMode.equals("+")) {
                        int ion_mode = 1;
                        ps.setInt(4, ion_mode);
                    } else {
                        int ion_mode = 2;
                        ps.setInt(4, ion_mode);
                    }
                    ps.setInt(5, a.numPeaks);
                    ps.setInt(6, j);
                    int predicted = 1;
                    ps.setInt(7, predicted);
                    ps.executeUpdate();
                    ps.close();
                    return b;
                }
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return -1;
    }

    public int chekLastMsmsId() throws SQLException {
        try {
            String sql = "SELECT msms_id FROM msms ORDER BY msms_id DESC LIMIT 1;";
            PreparedStatement ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int msms_id = rs.getInt("msms_id");
                return msms_id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void InsertCompoundMsPeaks(Compound a, Integer j) {
        try {
            int b = chekLastPeakId();
            if (b != -1) {
                int i;

                String sql = "INSERT into msms_peaks (peak_id,intensity,mz,msms_id)" + " VALUES(?,?,?,?);";
                PreparedStatement ps = c.prepareStatement(sql);
                if (a.numPeaks == 0) {
                    if (checkMsPeaks(0, 0.0, j) == -1) {
                        b++;
                        ps.setInt(1, b);
                        ps.setInt(2, 0);
                        ps.setDouble(3, 0.0);
                        ps.setInt(4, j);
                        ps.executeUpdate();
                    } else {
                        System.out.println("ya contenia estos mismos picos");
                    }
                } else {
                    for (i = 0; i < a.numPeaks; i++) {

                        if (checkMsPeaks(a.peaks.intensity.get(i), a.peaks.mz.get(i), j) == -1) {
                            b++;
                            ps.setInt(1, b);
                            ps.setInt(2, a.peaks.intensity.get(i));
                            ps.setDouble(3, a.peaks.mz.get(i));
                            ps.setInt(4, j);
                            ps.executeUpdate();

                        }
                    }
                }
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public int chekLastPeakId() throws SQLException {
        try {
            String sql = "SELECT peak_id FROM msms_peaks ORDER BY peak_id DESC LIMIT 1;";
            PreparedStatement ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int peak_id = rs.getInt("peak_id");
                return peak_id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int checkMsPeaks(Integer a, Double b, Integer j) {
        try {

            String s = "SELECT peak_id FROM msms_peaks  WHERE intensity=? AND mz=? AND msms_id=? ;";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setInt(1, a);
            ps.setDouble(2, b);
            ps.setInt(3, j);
            rs = ps.executeQuery();
            while (rs.next()) {
                int peak_id = rs.getInt("peak_id");
                return peak_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void InsertOrganism(OrganOrganism a) {
        try {
            int i;
            for (i = 0; i < a.organism.size(); i++) {
                if (checkOrganismId(a.organism.get(i)) == -1) {
                    String sql = "INSERT INTO organism (name_organism)" + " VALUES(?);";
                    PreparedStatement ps = c.prepareStatement(sql);
                    ps.setString(1, a.organism.get(i));
                    ps.executeUpdate();
                    ps.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void InsertOrgan(OrganOrganism a) throws SQLException {
        try {
            int i;
            String sql = "INSERT INTO organ (organ_id,organism_id,name_organ) " + "VALUES (?,?,?);";
            PreparedStatement ps = c.prepareStatement(sql);
            for (i = 0; i < a.id.size(); i++) {
                if (checkOrganId(a.organ.get(i), a.id.get(i)) == -1) {
                    int j = a.id.get(i);
                    int k = checkOrganismId(a.organism.get(i));;
                    String s = a.organ.get(i);

                    ps.setInt(1, j);
                    ps.setInt(2, k);
                    ps.setString(3, s);
                    ps.executeUpdate();
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public int InsertOrganCompound(Compound a, Integer b) {
        try {

            String sql = "INSERT INTO organ_compound (organ_id,compound_id) " + "VALUES(?,?);";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, a.id);
            ps.setInt(2, b);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entr")) {

                return -1;

            } else {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public int checkOrganismId(String organism) {
        try {

            String s = "SELECT organism_id FROM organism  WHERE name_organism=? ;";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setString(1, organism);
            rs = ps.executeQuery();
            while (rs.next()) {
                int organism_id = rs.getInt("organism_id");
                return organism_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int checkOrganId(String organ, Integer j) {
        try {

            String s = "SELECT organism_id FROM organ  WHERE name_organ=? AND organ_id=? ;";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setString(1, organ);
            ps.setInt(2, j);
            rs = ps.executeQuery();
            while (rs.next()) {
                int organ_id = rs.getInt("organism_id");
                return organ_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void createOrgan() throws SQLException {
        try {
            statement = c.createStatement();
            String sql1 = "CREATE TABLE organ (" + "organ_id int NOT NULL ,"
                    + "organism_id int NOT NULL,"
                    + "name_organ varchar(50) DEFAULT NULL,"
                    + "CONSTRAINT organism_id FOREIGN KEY (organism_id) REFERENCES organism (organism_id) ON DELETE CASCADE,"
                    + "PRIMARY KEY (organ_id)"
                    + ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            statement.executeUpdate(sql1);
            statement.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Tables are already created.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void createOrganism() throws SQLException {
        try {
            statement = c.createStatement();
            String sql1 = "CREATE TABLE organism (" + "organism_id int NOT NULL AUTO_INCREMENT ,"
                    + "name_organism varchar(50) DEFAULT NULL,"
                    + "PRIMARY KEY (organism_id)"
                    + ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            statement.executeUpdate(sql1);
            statement.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Tables are already created.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void createOrgan_Compound() throws SQLException {
        try {
            statement = c.createStatement();
            String sql1 = "CREATE TABLE organ_compound (" + "organ_id int NOT NULL,"
                    + "compound_id int NOT NULL,"
                    + "PRIMARY KEY (organ_id,compound_id),"
                    + "FOREIGN KEY (compound_id) REFERENCES compounds(compound_id),"
                    + "FOREIGN KEY (organ_id) REFERENCES organ(organ_id) "
                    + ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            statement.executeUpdate(sql1);
            statement.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Tables are already created.");
            } else {
                e.printStackTrace();
            }
        }
    }
}

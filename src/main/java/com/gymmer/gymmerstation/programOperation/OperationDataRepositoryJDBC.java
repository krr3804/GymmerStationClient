package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OperationDataRepositoryJDBC implements OperationDataRepository {

    @Override
    public void save(OperationDataProgram dataProgram) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        Long program_id = dataProgram.getProgram().getId();
        Long week = dataProgram.getWeek();
        Long division = dataProgram.getDivision();

        try {
            String query = "INSERT INTO performanceData VALUES (?,?,?,?,?,?)";
            psmt = conn.prepareStatement(query);
            for(OperationDataExercise dataExercise : dataProgram.getOdExerciseList()) {
                psmt.setLong(1,week);
                psmt.setLong(2,division);
                psmt.setString(3,dataExercise.getTimeConsumed());
                psmt.setString(4,dataExercise.getName());
                psmt.setLong(5,dataExercise.getDivision());
                psmt.setLong(6,program_id);
                psmt.addBatch();
                psmt.clearParameters();
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public void delete(Program program) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        try {
            String query = "DELETE FROM performanceData where program = ?";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,program.getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public List<OperationDataProgram> getDataListByProgram(Program program) {
        return null;
    }

    @Override
    public int getProgress(Program program) {
        return 0;
    }

    @Override
    public List<Program> getProgramsInProgress() {
        return null;
    }

    private Connection getConnection() {
        Connection conn = null;
        String server = "localhost:3306";
        String database = "program_db";
        String option = "?useSSL=false&serverTimezone=UTC";
        String userName = "root";
        String password = "Yk2469804!";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver load error!!" + e.getMessage());
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + option, userName, password);
            System.out.println("Connection completed!");
        } catch (SQLException e) {
            System.err.println("Connection Error:" + e.getMessage());
        }
        return conn;
    }

    private void closePreparedStatement(PreparedStatement psmt) {
        try {
            if (psmt != null) {
                psmt.close();
            }
        } catch (SQLException e) {
            System.err.println("psmt 오류 : " + e.getMessage());
        }
    }

    private void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("conn 오류 : " + e.getMessage());
        }
    }
}

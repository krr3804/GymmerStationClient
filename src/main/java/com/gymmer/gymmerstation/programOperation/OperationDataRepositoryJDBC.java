package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.sql.*;
import java.util.ArrayList;
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
            String query = "INSERT INTO UserPerformanceData VALUES (?,?,?,?,?,?)";
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
            String query = "DELETE FROM UserPerformanceData where program = ?";
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
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<OperationDataProgram> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM userperformancedata INNER JOIN exercise on exercise.program = userperformancedata.program " +
                    " and exercise.exercise_name = userperformancedata.exercise_name and " +
                    "exercise.division = userperformancedata.exercise_division where userperformancedata.program = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,program.getId());
            rs = psmt.executeQuery();
            OperationDataProgram currentDataProgram = null;
            while (rs.next()) {
                Long week = rs.getLong("week");
                Long division = rs.getLong("division");
                if (currentDataProgram == null) {
                    currentDataProgram = mapOperationDataProgram(program,week,division,rs);
                } else if(!currentDataProgram.getWeek().equals(week) || !currentDataProgram.getDivision().equals(division)) {
                    list.add(currentDataProgram);
                    currentDataProgram = mapOperationDataProgram(program,week,division,rs);
                }
                currentDataProgram.getOdExerciseList().add(mapOperationDataExercise(rs));
            }
            if (currentDataProgram != null) {
                list.add(currentDataProgram);
            }
            rs.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
        return list;
    }

    private OperationDataProgram mapOperationDataProgram(Program program, Long week, Long division, ResultSet rs) throws SQLException {
        OperationDataProgram dataProgram = new OperationDataProgram(
                program, week, division, new ArrayList<>()
        );
        return dataProgram;
    }

    private OperationDataExercise mapOperationDataExercise(ResultSet rs) throws SQLException {
        OperationDataExercise dataExercise = new OperationDataExercise(
                rs.getString("exercise.exercise_name"), rs.getLong("exercise.sets"), rs.getLong("exercise.reps"),
                rs.getLong("exercise.weight"), rs.getString("exercise.rest"), rs.getLong("exercise.division"),
                rs.getString("userperformancedata.timeConsumed")
        );
        return dataExercise;
    }

    @Override
    public int getProgress(Program program) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        int progress = 0;
        try {
            String query = "SELECT COUNT(DISTINCT(CONCAT(week,'-',division))) AS PROGRESS FROM UserPerformanceData WHERE program = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,program.getId());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                progress = rs.getInt("progress");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
        return progress;
    }

    @Override
    public List<Program> getProgramsInProgress() {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<Program> programList = new ArrayList<>();
        try {
            String query = "SELECT program.program_id, program.name, program.purpose, program.length, program.divisionQty FROM UserPerformanceData " +
                    "INNER JOIN program ON UserPerformanceData.program = program.program_id GROUP BY userperformancedata.program;";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            while (rs.next()) {
                programList.add(mapProgram(rs));
            }
            rs.close();;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
        return programList;
    }

    private Program mapProgram(ResultSet rs) throws SQLException {
        Program program = new Program(
                rs.getLong("program.program_id"),
                rs.getString("program.name"),
                rs.getString("program.purpose"),
                rs.getLong("program.length"),
                rs.getLong("program.divisionQty")
        );
        return program;
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

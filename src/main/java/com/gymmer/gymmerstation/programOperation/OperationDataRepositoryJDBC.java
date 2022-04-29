package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.*;

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
            String query = "INSERT INTO performance_data_exercise VALUES (?,?,?,?,?,?,?,?,?)";
            psmt = conn.prepareStatement(query);
            for(OperationDataExercise dataExercise : dataProgram.getOdExerciseList()) {
                psmt.setString(1,dataExercise.getName());
                psmt.setLong(2,dataExercise.getCurrentSet());
                psmt.setLong(3,dataExercise.getRep());
                psmt.setLong(4,dataExercise.getWeight());
                psmt.setString(5,dataExercise.getRestTime());
                psmt.setLong(6,week);
                psmt.setLong(7,division);
                psmt.setString(8,dataExercise.getTimeConsumed());
                psmt.setLong(9,program_id);
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
    public void terminate(Program program) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        long key = 0L;
        try {
            String query = "INSERT INTO program SELECT null, name, purpose, length, divisionQty, true FROM program where program_id = ?";
            psmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psmt.setLong(1,program.getId());
            psmt.executeUpdate();

            ResultSet rs = psmt.getGeneratedKeys();
            if(rs.next()) {
                key = rs.getLong(1);
            }
            rs.close();

            query =  "UPDATE performance_data_exercise SET program = ? WHERE program = ?";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,key);
            psmt.setLong(2,program.getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public void delete(Program program, boolean status) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        String query;
        try {
            query = "DELETE FROM performance_data_exercise USING program LEFT JOIN " +
                    "performance_data_exercise ON program_id = performance_data_exercise.program where program.program_id = ?";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,program.getId());
            psmt.executeUpdate();
            if(status) {
                query = "DELETE FROM program where program_id = ?";
                psmt = conn.prepareStatement(query);
                psmt.setLong(1,program.getId());
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }


    @Override
    public List<OperationDataProgram> getProgramData(Program program) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs;
        List<OperationDataProgram> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM performance_data_exercise where program = ?";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,program.getId());
            rs = psmt.executeQuery();
            OperationDataProgram currentDataProgram = null;
            while (rs.next()) {
                Long week = rs.getLong("week");
                Long division = rs.getLong("division");
                if (currentDataProgram == null) {
                    currentDataProgram = mapOperationDataProgram(program,week,division);
                } else if(!currentDataProgram.getWeek().equals(week) || !currentDataProgram.getDivision().equals(division)) {
                    list.add(currentDataProgram);
                    currentDataProgram = mapOperationDataProgram(program,week,division);
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


    private OperationDataProgram mapOperationDataProgram(Program program, Long week, Long division) {
        OperationDataProgram dataProgram = new OperationDataProgram(
                program, week, division, new ArrayList<>()
        );
        return dataProgram;
    }

    private OperationDataExercise mapOperationDataExercise(ResultSet rs) throws SQLException {
        OperationDataExercise dataExercise = new OperationDataExercise(
                rs.getString("name"), rs.getLong("c_set"), rs.getLong("reps"), rs.getLong("weight"),
                rs.getString("rest"),rs.getString("time_consumed")
        );
        return dataExercise;
    }

    @Override
    public int getProgress(Program program) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        int progress = 0;
        try {
            String query = "SELECT COUNT(DISTINCT(CONCAT(week,'-',division))) AS PROGRESS FROM performance_data_exercise WHERE program = ?;";
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
    public List<Program> getPrograms(boolean status) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<Program> programList = new ArrayList<>();
        try {
            String query = "SELECT program.program_id, program.name, program.purpose, program.length, program.divisionQty FROM performance_data_exercise " +
                    "INNER JOIN program ON performance_data_exercise.program = program.program_id WHERE program.termination_status = ? " +
                    "GROUP BY performance_data_exercise.program ;";
            psmt = conn.prepareStatement(query);
            psmt.setBoolean(1,status);
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

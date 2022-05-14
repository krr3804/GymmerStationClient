package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramRepositoryJDBC implements ProgramRepository{

    @Override
    public void addProgram(Program program){
        Long key = 0L;
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        try {
            String query = "INSERT INTO program values (null,?,?,?,?,false);";
            psmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, program.getName());
            psmt.setString(2, program.getPurpose());
            psmt.setLong(3, program.getLength());
            psmt.setLong(4, program.getDivisionQty());
            psmt.executeUpdate();

            ResultSet rs = psmt.getGeneratedKeys();
            if(rs.next()) {
                key = rs.getLong(1);
            }
            rs.close();
            psmt.clearParameters();
            query = "INSERT INTO exercise values (?,?,?,?,?,?,?);";
            psmt = conn.prepareStatement(query);
            for(Exercise exercise : program.getExerciseList()) {
                psmt.setString(1, exercise.getName());
                psmt.setLong(2, exercise.getSet());
                psmt.setLong(3, exercise.getRep());
                psmt.setLong(4, exercise.getWeight());
                psmt.setString(5, exercise.getRestTime());
                psmt.setLong(6, exercise.getDivision());
                psmt.setLong(7, key);
                psmt.addBatch();
                psmt.clearParameters();
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public List<Program> showProgramList() {
        List<Program> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from program left join exercise on exercise.program = program.program_id where program.termination_status = false;";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery(query);
            Program currentProgram = null;
            while (rs.next()) {
                Long id = rs.getLong("program_id");
                if(currentProgram == null) {
                    currentProgram = mapProgram(rs);
                }
                if(!id.equals(currentProgram.getId())) {
                    list.add(currentProgram);
                    currentProgram = mapProgram(rs);
                }
                currentProgram.getExerciseList().add(mapExercise(rs));
            }
            if(currentProgram != null) {
                list.add(currentProgram);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }

        return list;
    }

    @Override
    public void removeEntireDivision(Long programId, Long removedDivision) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        String query;
        try {
            query = "UPDATE program SET divisionQty = divisionQty - 1 WHERE program_id = ?";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,programId);
            psmt.executeUpdate();
            psmt.clearParameters();

            query = "DELETE FROM exercise WHERE division = ? AND program = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1, removedDivision);
            psmt.setLong(2, programId);
            psmt.executeUpdate();
            psmt.clearParameters();

            query = "UPDATE exercise SET division = division - 1 WHERE division > ? AND program = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1, removedDivision);
            psmt.setLong(2, programId);
            psmt.executeUpdate();
            psmt.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public void addExercises(Long program_id, List<Exercise> exerciseList) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        String query;
        try {
            query = "INSERT INTO exercise values (?,?,?,?,?,?,?);";
            psmt = conn.prepareStatement(query);
            for (Exercise exercise : exerciseList) {
                psmt.setString(1,exercise.getName());
                psmt.setLong(2,exercise.getSet());
                psmt.setLong(3,exercise.getRep());
                psmt.setLong(4,exercise.getWeight());
                psmt.setString(5,exercise.getRestTime());
                psmt.setLong(6,exercise.getDivision());
                psmt.setLong(7,program_id);
                psmt.addBatch();
                psmt.clearParameters();
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public void deleteExercises(Long program_id, List<Exercise> exerciseList) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        String query;
        try {
            query = "DELETE FROM exercise WHERE exercise_name = ? AND division = ? AND program = ?;";
            psmt = conn.prepareStatement(query);
            for(Exercise exercise : exerciseList) {
                psmt.setString(1, exercise.getName());
                psmt.setLong(2, exercise.getDivision());
                psmt.setLong(3, program_id);
                psmt.addBatch();
                psmt.clearParameters();
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public void editProgram(Program newProgram) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        String query;
        try {
            query = "UPDATE program SET name = ?, purpose = ?, length = ?, divisionQty = ? WHERE program_id = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, newProgram.getName());
            psmt.setString(2, newProgram.getPurpose());
            psmt.setLong(3, newProgram.getLength());
            psmt.setLong(4,newProgram.getDivisionQty());
            psmt.setLong(5, newProgram.getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    private Program mapProgram(ResultSet rs) throws SQLException {
        Program program = new Program(
                rs.getLong("program_id"),
                rs.getString("name"),
                rs.getString("purpose"),
                rs.getLong("length"),
                rs.getLong("divisionQty"),
                new ArrayList<>());
        return program;
    }

    private Exercise mapExercise(ResultSet rs) throws SQLException {
        Exercise exercise = new Exercise(
                rs.getString("exercise_name"),
                rs.getLong("sets"),
                rs.getLong("reps"),
                rs.getLong("weight"),
                rs.getString("rest"),
                rs.getLong("division"));
        return exercise;
    }

    @Override
    public void deleteProgram(Long id) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        try {
            String query = "delete from exercise where program = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,id);
            psmt.executeUpdate();
            psmt.clearParameters();
            query = "delete from program where program_id = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,id);
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("sql : " + e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
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

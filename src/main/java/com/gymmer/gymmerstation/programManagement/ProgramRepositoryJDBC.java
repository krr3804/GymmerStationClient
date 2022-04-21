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
            String query = "INSERT INTO program values (null,?,?,?);";
            psmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, program.getName());
            psmt.setString(2, program.getPurpose());
            psmt.setLong(3, program.getLength());
            psmt.executeUpdate();

            ResultSet rs = psmt.getGeneratedKeys();
            if(rs.next()) {
                key = rs.getLong(1);
            }
            rs.close();
            psmt.clearParameters();

            query = "INSERT INTO exercise values (?,?,?,?,?,?,?,?)";
            psmt = conn.prepareStatement(query);
            for(Exercise exercise : program.getExerciseList()) {
                psmt = addExerciseData(psmt,exercise,key);
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    private PreparedStatement addExerciseData(PreparedStatement psmt, Exercise exercise, Long programId) throws SQLException{
        psmt.setString(1,exercise.getName());
        psmt.setLong(2,exercise.getSet());
        psmt.setLong(3,exercise.getRep());
        psmt.setLong(4,exercise.getWeight());
        psmt.setString(5,exercise.getMinute());
        psmt.setString(6,exercise.getSecond());
        psmt.setLong(7,exercise.getDivision());
        psmt.setLong(8,programId);
        psmt.addBatch();
        psmt.clearParameters();
        return psmt;
    }

    private PreparedStatement deleteExerciseData(PreparedStatement psmt, Exercise exercise, Long programId) throws SQLException {
        psmt.setString(1,exercise.getName());
        psmt.setLong(2, exercise.getDivision());
        psmt.setLong(3,programId);
        psmt.addBatch();
        psmt.clearParameters();
        return psmt;
    }

    @Override
    public List<Program> showProgramList() {
        List<Program> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from program left join exercise on exercise.program = program.program_id;";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery(query);
            Program currentProgram = null;
            while (rs.next()) {
                Long id = rs.getLong("program_id");
                if(currentProgram == null) {
                    currentProgram = mapProgram(rs);
                }
                if(id != currentProgram.getId()) {
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
        }
        finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
        return list;
    }

    private Program mapProgram(ResultSet rs) throws SQLException {
        Program program = new Program(
                rs.getLong("program_id"),
                rs.getString("name"),
                rs.getString("purpose"),
                rs.getLong("length"),
                new ArrayList<>());
        return program;
    }

    private Exercise mapExercise(ResultSet rs) throws SQLException {
        Exercise exercise = new Exercise(
                rs.getString("exercise_name"),
                rs.getLong("sets"),
                rs.getLong("reps"),
                rs.getLong("weight"),
                rs.getString("rest_min"),
                rs.getString("rest_sec"),
                rs.getLong("division"));
        return exercise;
    }

    @Override
    public void editProgram(Program program, List<Exercise> additionList, List<Exercise> deletionList) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        try {
            String query = "UPDATE program SET name = ?, purpose = ?, length = ? WHERE program_id = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setString(1,program.getName());
            psmt.setString(2,program.getPurpose());
            psmt.setLong(3,program.getLength());
            psmt.setLong(4,program.getId());
            psmt.executeUpdate();
            psmt.clearParameters();
            Long program_id = program.getId();

            if(!additionList.isEmpty()) {
                query = "INSERT INTO exercise values (?,?,?,?,?,?,?,?);";
                psmt = conn.prepareStatement(query);
                for (Exercise exercise : additionList) {
                    psmt = addExerciseData(psmt, exercise, program_id);
                }
                psmt.executeBatch();
                psmt.clearParameters();
            }

            if(!deletionList.isEmpty()) {
                query = "DELETE FROM exercise WHERE exercise_name = ? AND division = ? AND program = ?;";
                psmt = conn.prepareStatement(query);
                for (Exercise exercise : deletionList) {
                    psmt = deleteExerciseData(psmt,exercise,program_id);
                }
                psmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
    }

    @Override
    public Program getProgramById(Long id) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        Program program = null;
        try {
            String query = "select * from program left join exercise on exercise.program = program.program_id where program.program_id = ?;";
            psmt = conn.prepareStatement(query);
            psmt.setLong(1,id);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                if (program == null) {
                    program = mapProgram(rs);
                }
                program.getExerciseList().add(mapExercise(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("sql : " + e.getMessage());
        } finally {
            closePreparedStatement(psmt);
            closeConnection(conn);
        }
        return program;
    }

    @Override
    public void deleteProgram(Long id) {
        Connection conn = getConnection();
        PreparedStatement psmt = null;
        try {
            String query = "delete from exercise where program.program_id = ?;";
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

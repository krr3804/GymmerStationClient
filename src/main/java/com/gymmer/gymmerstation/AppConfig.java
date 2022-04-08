package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.programManagement.*;

public class AppConfig {
    public static ProgramRepository programRepository() {
        return new MemoryProgramRepository();
    }

    public static ProgramService programModel() {
        return new ProgramServiceImpl(programRepository());
    }
}
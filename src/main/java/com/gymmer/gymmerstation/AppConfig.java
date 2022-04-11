package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.programManagement.*;
import com.gymmer.gymmerstation.programOperation.MemoryOperationDataRepository;
import com.gymmer.gymmerstation.programOperation.OperationDataRepository;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
import com.gymmer.gymmerstation.programOperation.ProgramOperationServiceImpl;

public class AppConfig {
    public static ProgramRepository programRepository() {
        return new MemoryProgramRepository();
    }

    public static ProgramService programService() {
        return new ProgramServiceImpl(programRepository());
    }

    public static ProgramOperationService programOperationService() {
        return new ProgramOperationServiceImpl(operationDataRepository());
    }

    public static OperationDataRepository operationDataRepository() {
        return new MemoryOperationDataRepository();
    }

}

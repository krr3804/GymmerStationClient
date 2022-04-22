package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.programManagement.*;
import com.gymmer.gymmerstation.programOperation.*;

public class AppConfig {
    public static ProgramRepository programRepository() {
        return new ProgramRepositoryJDBC();
    }

    public static ProgramService programService() {
        return new ProgramServiceImpl(programRepository());
    }

    public static ProgramOperationService programOperationService() {
        return new ProgramOperationServiceImpl(operationDataRepository());
    }

    public static OperationDataRepository operationDataRepository() {
        return new OperationDataRepositoryJDBC();
    }

}

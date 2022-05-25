package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.programManagement.*;
import com.gymmer.gymmerstation.programOperation.*;

public class AppConfig {
    private static final ProgramService programService = new ProgramServiceImpl();
    private static final ProgramOperationService programOperationService = new ProgramOperationServiceImpl();
    public static ProgramService programService() {
        return programService;
    }

    public static ProgramOperationService programOperationService() {
        return programOperationService;
    }

}

package com.orangehrmproject.demo.dataprovider;

import com.demoproject.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.nio.file.Paths;

public class ExcelDataProvider {

    private static final String LOGIN_DATA_PATH = Paths
            .get("src", "test", "resources", "testdata", "LoginData.xlsx")
            .toString();

    @DataProvider(name = "loginData", parallel=true)
    public Object[][] getLoginData() {
        return ExcelUtils.getSheetData(LOGIN_DATA_PATH, "LoginData");
    }
}

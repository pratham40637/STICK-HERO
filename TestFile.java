package com.example.stick_hero_final;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.*;

import static org.testng.AssertJUnit.assertTrue;

public class TestFile {

    @Test
    public void Test_0() throws IOException {
        BufferedReader in= new BufferedReader(new FileReader("SavedGame.txt"));
        boolean val;
        String l = in.readLine();
        if (l != null){
            val = true;
        }else{
            val = false;
        }

        assertTrue(val);
    }

}

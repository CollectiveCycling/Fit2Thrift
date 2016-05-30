package com.collectivecycling.fit2thrift;

import com.collectivecycling.fit2thrift.thrift.fit.FitData;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by nick on 30/05/2016.
 */
public class TestFit {
    final static String fitDataFileName="target/classes/2016-03-04-05-44-27.fit";
    @Test
    public void fitFileExists() {
        assertTrue(fitDataFileName + " does not exist",new File(fitDataFileName).isFile());
    }

    @Test
    public void testFit2Thrift () {
        FitData fitData = Fit.toThrift(fitDataFileName);
        assertTrue(fitDataFileName + " has no Activities", fitData.isSetActivities());
    }
}

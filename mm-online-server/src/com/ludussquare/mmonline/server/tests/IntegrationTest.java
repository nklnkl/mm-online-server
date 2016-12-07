package com.ludussquare.mmonline.server.tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ludussquare.mmonline.server.Main;

import spark.Spark;

public class IntegrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Main.main(null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Spark.stop();
	}

	@Test
	public void test() {
	}

}

package io.javabrains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathUtilsTest {
	MathUtils mathUtils;
	
	@BeforeAll
	void clean()
	{
		System.out.println("start...");
	}

	@BeforeEach
	void init()
	{
		mathUtils=new MathUtils();
	}
	@AfterEach
	void cleanup()
	{
		System.out.println("cleanup...");
	}
	
    @DisplayName("Testing add method")
	@Test
	void testAdd() {
		
		int expected=2;
		int actual=mathUtils.add(1, 1);
		assertEquals(expected, actual,"the add method should add two numbers");
		}
	@Test
	void testComputeCircleRadius()
	{
		
		assertEquals(314.1592653589793, mathUtils.computeCircleArea(10),"should retuen right circle area");
		
	}
	@Test
	void testDiv()
	{
		
		assertThrows(ArithmeticException.class, ()-> mathUtils.div(1, 0),"divide by zero should throw");
	}

}

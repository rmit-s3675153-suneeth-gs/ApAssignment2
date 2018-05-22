package JUnitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Adult;
import model.Person;

public class AdultTest {
	Person adult;
	@Before
	public void setUp() throws Exception {
		adult = new Adult("Joe Rogan","Joe.jpg",45,"Podcast","SA","M");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void test1() {
		assertEquals("Joe Rogan" , adult.getName());
	}
	@Test
	public void test2() {
		assertEquals("Joe.jpg", adult.getPhoto());
	}
	@Test
	public void testAge() throws NumberFormatException {
		assertEquals("asd" , adult.getAge());
	}
	@Test
	public void test3() {
		assertEquals(45 , adult.getAge());
	}
	
	@Test
	public void test4() {
		assertEquals("F" , adult.getName());
	}
	@Test
	public void test5() {
		assertEquals("M" , adult.getGender());
	}

}

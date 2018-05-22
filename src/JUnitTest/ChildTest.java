package JUnitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Child;

public class ChildTest {
	Child child;
	@Before
	public void setUp() throws Exception {
		child = new Child("Joe Rogan","Joe.jpg",12,"Podcast","SA","M","Dana White","Ronda Rousey");
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
		assertEquals("Joe Rogan",child.getName());
	}
	@Test
	public void test2() {
		assertEquals("Joe.jpg",child.getPhoto());
	}
	@Test
	public void test3() {
		assertEquals(35,child.getAge());
	}
	@Test
	public void testAge() {
		assertEquals(12,child.getAge());
	}
	@Test
	public void test4() {
		assertEquals("Dana White",child.getParent2());
	}
	@Test
	public void test5() {
		assertEquals("Dana White",child.getParent1());
	}
	@Test
	public void test6() {
		assertEquals("M",child.getGender());
	}

}

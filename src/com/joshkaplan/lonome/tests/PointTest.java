package com.joshkaplan.lonome.tests;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.joshkaplan.lonome.Point;

public class PointTest {
	
	Point point;

	@Before
	public void setUp() throws Exception {
		point = new Point();
	}

	@After
	public void tearDown() throws Exception {
		point = null;
	}
	
	@Test
	public void testSetAndGetX() {
		point.setX(10);
		assertEquals(point.getX(),10);
	}
	
	@Test
	public void testSetAndGetY() {
		point.setY(10);
		assertEquals(point.getY(),10);
	}
	
	@Test
	public void testSetAndGetRowCol() {
		point.setRowCol(10, 20);
		assertEquals(point.getRow(), 10);
		assertEquals(point.getCol(), 20);
		assertEquals(point.getX(), 20);
		assertEquals(point.getY(), 10);
	}
	

}

package com.baishu.tetris;

import org.junit.Test;

public class TestRanShape {
	@Test
	public void TestRanShape1() {
		Tetromino t = Tetromino.ranShape();
		System.out.println(t);
	}
}

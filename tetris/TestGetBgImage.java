package com.baishu.tetris;

import java.awt.List;
import java.util.Arrays;

import org.junit.Test;

public class TestGetBgImage {
	@Test
	public void TestBgImage() {
		Cell cell = new Z();
		System.out.println(cell.getBgImage());
	}
}

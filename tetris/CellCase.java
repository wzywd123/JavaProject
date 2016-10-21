package com.baishu.tetris;

import java.awt.image.BufferedImage;

public class CellCase {
	private int row;
	private int col;
	private BufferedImage image;
	/*
	 * set,get方法可以由IDE智能创建
	 */
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * row代表行，格子下降即row+1
	 */
	public void moveDown() {
		row++;
	}
}

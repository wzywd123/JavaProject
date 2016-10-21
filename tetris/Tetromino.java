package com.baishu.tetris;

import java.awt.image.BufferedImage;
import java.util.Random;


public class Tetromino extends Cell{

	protected Cell[] cells = new Cell[4];
	
	/*
	 * Tetromino类中的移动方法，通过重写和向上调用父类中方法实现
	 * @see com.baishu.tetris.Cell#moveDown()
	 */
	public void moveDown() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].moveDown();
		}
	}
	public void moveLeft() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].moveLeft();
		}
	}
	public void moveRight() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].moveRight();
		}
	}
	/**
	 * 返回随机形状参数
	 */
	public static Tetromino ranShape() {
		Random random = new Random();
		int index = random.nextInt(7);
		switch (index) {
		case 0:return new J();
		case 1:return new L();
		case 2:return new O();
		case 3:return new Z();
		case 4:return new S();
		case 5:return new I();
		case 6:return new T();
		}
		return null;
	}
	/**
	 * Tetromino对象旋转方法
	 * @return Tetromino对象旋转结果，以Cell数组形式返回，用以判定旋转结果是否合法
	 */
	public Cell[] spin() {
		//因为学的不多，暂时用下方法判断是否调用对象为O型对象,其他方法在类中重写toString(),equals()等
		if(this.getClass().equals(new O().getClass()))return null;
		Cell[] iCells = new Cell[4];
		int iRow = this.cells[2].getRow();
		int iCol = this.cells[2].getCol();
		for (int i = 0; i < this.cells.length; i++) {
			int nRow = this.cells[i].getRow();
			int nCol = this.cells[i].getCol();
			//对iCells内元素进行初始化，防止出现nullPointException异常
			iCells[i] = new Cell(iRow-iCol+nCol, iRow+iCol-nRow, this.cells[i].getBgImage());
		}
		return iCells;
	}
	/* 
	 * 重写toString() 方法，为了能够方便的测试
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < cells.length; i++){
			str = str + cells[i].getRow()+" "+cells[i].getCol()+"\n";
		}
		return str;
	}
}
class J extends Tetromino {
	public J() {
		cells[0] = new Cell(2, 5, Tetris.J);
		cells[1] = new Cell(0, 6, Tetris.J);
		cells[2] = new Cell(2, 6, Tetris.J);
		cells[3] = new Cell(1, 6, Tetris.J);
	}
}
class L extends Tetromino {
	
	public L() {
		cells[0] = new Cell(2, 6, Tetris.L);
		cells[1] = new Cell(0, 5, Tetris.L);
		cells[2] = new Cell(2, 5, Tetris.L);
		cells[3] = new Cell(1, 5, Tetris.L);
	}
}
class O extends Tetromino {
	public O() {
		cells[0] = new Cell(0, 5, Tetris.O);
		cells[1] = new Cell(0, 6, Tetris.O);
		cells[2] = new Cell(1, 5, Tetris.O);
		cells[3] = new Cell(1, 6, Tetris.O);
	}
}
class Z extends Tetromino {
	/*
	 * 实现cells中的四个格子的特殊排列
	 * Tetris.Z代表特例图片（颜色）——来自Tetris类中加载的静态资源
	 */
	public Z() {
		cells[0] = new Cell(0, 4, Tetris.Z);
		cells[1] = new Cell(0, 5, Tetris.Z);
		cells[2] = new Cell(1, 5, Tetris.Z);
		cells[3] = new Cell(1, 6, Tetris.Z);
	}
}
class S extends Tetromino {
	public S() {
		cells[0] = new Cell(1, 4, Tetris.S);
		cells[1] = new Cell(1, 5, Tetris.S);
		cells[2] = new Cell(0, 5, Tetris.S);
		cells[3] = new Cell(0, 6, Tetris.S);
	}
}
class I extends Tetromino {
	public I() {
		cells[0] = new Cell(0, 5, Tetris.I);
		cells[1] = new Cell(1, 5, Tetris.I);
		cells[2] = new Cell(2, 5, Tetris.I);
		cells[3] = new Cell(3, 5, Tetris.I);
	}
}
class T extends Tetromino {
	public T() {
		cells[0] = new Cell(0, 4, Tetris.T);
		cells[1] = new Cell(0, 6, Tetris.T);
		cells[2] = new Cell(0, 5, Tetris.T);
		cells[3] = new Cell(1, 5, Tetris.T);
	}
}


package com.baishu.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Tetris extends JPanel{
	/**
	 * 下一个下落对象
	 */
	private Tetromino nextone;
	/**
	 * 当前下落对象
	 */
	private Tetromino tetromino;
	/**
	 * 行，列
	 */
	private static final int ROWS = 20;
	private static final int COLS = 10;
	/**
	 * 提示信息
	 */
	private int score = 0;
	private int lines = 0;
	private int level = 5;
	/**
	 * 墙
	 */
	private Cell[][] wall = new Cell[ROWS][COLS];
	/**
	 * 各种状态，STATE 程序状态， BGI 背景图片状态
	 */
	private boolean STATE = true;
	private int BGI = 0;
	/**
	 * 格子长度的换算
	 */
	public static final int CELL_SIZE = 26;
	public static BufferedImage Z;
	public static BufferedImage S;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage O;
	public static BufferedImage I;
	public static BufferedImage T;
	public static BufferedImage[] bgi = new BufferedImage[4];
	public static BufferedImage pause;
	public static BufferedImage tetris;
	public static BufferedImage gameover;
	static {
		try {
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			pause = ImageIO.read(Tetris.class.getResource("pause.png"));
			tetris = ImageIO.read(Tetris.class.getResource("tetris1.png"));
			gameover = ImageIO.read(Tetris.class.getResource("gameover.png"));
			for(int i = 0; i < bgi.length; i++) {
				String name = "bgi0"+i+".jpg";
				bgi[i] = ImageIO.read(Tetris.class.getResource(name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	public void action() {
		tetromino = Tetromino.ranShape();
		nextone = Tetromino.ranShape();
	
		KeyListener kl = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				keyMoveAction(k);
				repaint();
			}
		};
		//Tetris 是面板类型 l 监听 面板的动作
		this.addKeyListener(kl);
		this.setFocusable(true);
		//request 请求  Focus 焦点  
		this.requestFocus();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
		
			int moveIndex = 0;
			int bgiIndex = 0;
			int speed = 5*level;
			@Override
			public void run() {
				if (STATE) {
					//speed = speed*level;
					if (moveIndex % speed == 0) {
						moveDownAction();
						moveIndex = 0;
					}
				}
				//testAction();
				//isBottom();
				moveIndex++;
				bgiIndex++;
				repaint();
			}
		};
		timer.schedule(task, 10, 20);
	}
	public void changeBgiAction() {
		
	}
	/**
	 * 
	 */
	public void testAction() {
		for (int i = 0; i < tetromino.cells.length; i++) {
			System.out.println(tetromino.cells[i].getRow()+" "+tetromino.cells[i].getCol());
		}
	}
	/**
	 * @param k
	 */
	public void keyMoveAction(int k) {
		switch (k) {
			case KeyEvent.VK_RIGHT:moveRightAction();break;
			case KeyEvent.VK_LEFT:moveLeftAction();break;
			case KeyEvent.VK_DOWN:moveDownAction();break;
			case KeyEvent.VK_UP:spinCellAction();break;
			case KeyEvent.VK_I:moveInitAction();break;
			case KeyEvent.VK_P:STATE = false;break;
			case KeyEvent.VK_C:STATE = true;break; 
			case KeyEvent.VK_E:System.exit(0);break;
			
		}
	}
	/**
	 * 初始化方法
	 */
	public void moveInitAction() {
		STATE = false;
		wall = new Cell[ROWS][COLS];
		tetromino = Tetromino.ranShape();
		nextone = Tetromino.ranShape();
		score = 0;
		lines = 0;
		level = 0;
	}
	/**
	 * 旋转方法
	 */
	public void spinCellAction() {
		Cell[] nCells = tetromino.spin(); 
		if (nCells == null) return;
		for (int i = 0; i < nCells.length; i++) {
			int nRow = nCells[i].getRow();
			int nCol = nCells[i].getCol();
			//System.out.println(nRow+" "+nCol);
			if (nRow < 0 || nRow >= ROWS || nCol < 0 || nCol >= COLS || wall[nRow][nCol] != null) return;
		}
		//System.out.println(111);
		tetromino.cells = nCells;
	}
	/**
	 * 
	 */
	public void moveLeftAction() {
		if (canLeftMove()&&!isBottom()) {
			tetromino.moveLeft();
		}
	}
	/**
	 * 
	 */
	public void moveRightAction() {
		if (canRightMove()&&!isBottom()) {
			tetromino.moveRight();
		}
	}
	/**
	 * 
	 */
	public void moveDownAction() {
		if (tetromino == null) return;
		if (!isBottom()) {
			tetromino.moveDown();
		}
		
	}
	/**
	 * 
	 */
	public void removeLine() {
		boolean flag = true;
		int rowStart = 20;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (wall[row][col] == null) {
					flag = false;
					break;
				}
			}
			if (flag) {
				for (int col = 0; col < COLS; col++) {
					wall[row][col] = null;
				} 
				rowStart = row;
				score += 10;
				lines += 1;
				level = lines%10 == 0?level == 1?level:level-1:level;
				for (int row1 = rowStart; row1 > 0; row1--) {
					for (int col1 = 0; col1 < COLS; col1++) {
						wall[row1][col1] = wall[row1-1][col1];
					}
				}
			}else {
				flag = true;
			}
		}
	}
	/**
	 * @return 是否可以继续下落
	 */
	public boolean isBottom() {
		if (tetromino == null) return false;
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell c = cells[i];
			int col = c.getCol();
			int row = c.getRow();
			//此处利用“||”的逻辑短路特点，减少代码量
			if ((row+1) == ROWS || wall[row+1][col] != null) {
				//当确定当前对象运动到底部即停止时，将该对象的cells内的格子元素存入wall内
				for(int j = 0; j < cells.length; j++) {
					Cell cell = cells[j];
					int col1 = cell.getCol();
					int row1 = cell.getRow();
					wall[row1][col1] = cell;
				}
				removeLine();
				//控制背景图片
				BGI = (BGI == 3)?0:BGI+1;
				tetromino = nextone;
				nextone = Tetromino.ranShape();
				return true;
			}
		}
		return false;
	}
	/**
	 * 判定是否可以右移
	 * @return
	 */
	public boolean canRightMove() {
		if (tetromino == null) return false;
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell c = cells[i];
			int row = c.getRow();
			int col = c.getCol();
			if ((col+1) == COLS || wall[row][col+1] != null) return false;
		}
		return true;
	}
	/**
	 * @return
	 */
	public boolean canLeftMove() {
		if (tetromino == null) return false;
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell c = cells[i];
			int row = c.getRow();
			int col = c.getCol();
			if (col == 0 || wall[row][col-1] != null) return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		
		g.drawImage(bgi[BGI], 0, 0, null);
		g.drawImage(tetris, 0, 0, null);
		g.translate(15, 15);
		
		//画墙
		paintWall(g);
		//画当前下落对象和预备下落对象
		paintTetromino(g);
		paintNextone(g);
		//画提示信息，得分，消除行数，等级
		paintTabs(g);
		//画暂停画面
		paintGamePause(g);
		//画GameOver画面
		paintGameOver(g);
	}
	/**
	 * @return
	 */
	public boolean isGameOver() {
		for(int col = 0; col < COLS; col++) {
			if (wall[0][col] != null)return true;
		}
		return false;
	}
	/**
	 * @param g
	 */
	public void paintGameOver(Graphics g) {
		if (isGameOver()) {
			tetromino = null;
			g.drawImage(gameover, -15, -15, null);
			Color color = new Color(0,71,157);
			g.setColor(color);
			Font font = new Font(Font.SERIF,Font.BOLD,30);
			g.setFont(font);
			g.drawString(""+score, 260, 207);
			g.drawString(""+lines, 260, 253);
			g.drawString(""+level, 260, 300);
			STATE = false;
		}
	}
	/**
	 * @param g
	 */
	public void paintGamePause(Graphics g){
		if (!STATE && !isGameOver()) {
			g.drawImage(pause, -15, -15, null);
		}
	}
	/**
	 * @param g
	 */
	public void paintTabs(Graphics g) {
		//确定绘制的地点
		int x = 410;
		int y = 160;
		//设置颜色
		Color color = new Color(240,234,34);
		g.setColor(color);
		//设置字体
		Font f = new Font(Font.SERIF,Font.BOLD,30);
		g.setFont(f);
		g.drawString(""+score, x, y);
		y+=56;
		g.drawString(""+lines, x, y);
		y+=56;
		g.drawString(""+level, x, y);
	}
	/**
	 * 画Nextone
	 * @param g
	 */
	public void paintNextone(Graphics g) {
		 if (nextone == null)return;
		 Cell[] cells = nextone.cells;
		 for(int i = 0; i < cells.length; i++){
			 Cell c = cells[i];
			 int row = c.getRow() ;
			 int col = c.getCol() + 9;
			 int x = col * CELL_SIZE;
			 int y = row * CELL_SIZE;
			 g.drawImage(c.getBgImage(), x, y, null);
		 }
	}
	/**
	 * 画当前tetromino
	 * @param g
	 */
	public void paintTetromino(Graphics g) {
		//cells 引用了正在下落方块的4个格子数组
		if(tetromino == null)return;
		
		//System.out.println(tetromino == null);
		Cell[] cells = tetromino.cells;
		for(int i=0; i < cells.length; i++){
			//c 是正在下落方块的每个格子的引用
			Cell c = cells[i];
			int col = c.getCol();
			int row = c.getRow();
			int x = col * CELL_SIZE;
			int y = row * CELL_SIZE;
			g.drawImage(c.getBgImage(), x, y, null);
		}
	}
	
	/**
	 * 画墙
	 * @param g
	 */
	public void paintWall(Graphics g) {
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				Cell cell = wall[row][col];
				int rows = row * CELL_SIZE;
				int cols = col * CELL_SIZE;
				//g.drawImage(O, rows, cols, null);
				if (cell == null) {
					//System.out.println(0);
				}else{
					g.drawImage(cell.getBgImage(), cols, rows, null);
				}
			}
		}
	}
	
	public static void startTetris() {
		JFrame frame = new JFrame();
		
		Tetris tetris = new Tetris();
		
		frame.add(tetris);
		frame.setSize(525, 600);
		frame.setLocationRelativeTo(null);
		//frame.setUndecorated(true);
		frame.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		//显示窗口时候会尽快调用面板的paint()方法
		//绘制显示的内容（绘制背景等）
		frame.setVisible(true);
		//被重写的paint（）方法绘制了图片！
		tetris.action();
	}
	
}

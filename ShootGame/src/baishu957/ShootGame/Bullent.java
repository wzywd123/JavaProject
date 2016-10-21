package baishu957.ShootGame;

import java.util.Random;

/**
 * 子弹类（Bullent）继承自飞行物类（FlyingObj）
 * @author baishu
 * @version	1.02	2016-05-19
 */
public class Bullent extends FlyingObj{

	private int speed; 				//子弹对象的运动速度
	Random random = new Random();
	
	/**
	 * 子弹类的构造方法
	 * @param hx	传入参数设置子弹对象的x
	 * @param hy	传入参数设置子弹对象的y
	 */
	
	public Bullent(int hx,int hy){
		image  = ShootGame.bullet;  //设置对象的image
		width  = image.getWidth();  //设置宽
		height = image.getHeight(); //设置高
		x 	   = hx ;				//设置x
		y      = hy ;				//设置y
		speed  = 2; 				//设置速度speed
	}
	
	/**
	 * 重写父类飞行类（FlyingObj）中的step()抽象方法
	 * 实现子弹对象的移动
	 */
	public void step() {
		y -= speed;//子弹对象向上飞，所以用“-”
	}
	
	/**
	 * 撞击事件
	 * @return boolean
	 */
	public boolean beBoom(FlyingObj obj){
		int x1 = x-obj.width/2;
		int y1 = y-obj.height/2;
		int x2 = x+obj.width/2;
		int y2 = y+obj.height/2;
		
		int x0 = obj.x + obj.width/2;
		int y0 = obj.y + obj.height/2;
		
		return  x0 >= x1 && x0 <= x2
				&&
				y0 >= y1 && y0 <= y2;
		
	}
}




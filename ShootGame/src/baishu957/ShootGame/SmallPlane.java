package baishu957.ShootGame;

import java.util.Random;

/**
 * 小飞机类（BigPlane）继承自飞行物类（FlyingObj），实现了敌机（Enemy）接口
 * @author baihsu
 * @version	1.02	2016-05-19
 */
public class SmallPlane extends FlyingObj implements Enemy{
	
	private int speed;//小飞机对象运动速度
	
	Random random = new Random();
	/**
	 * 声明构造方法
	 */
	public SmallPlane(){
		image  = ShootGame.airplane;
		width  = image.getWidth();
		height = image.getHeight();
		x      = random.nextInt(400)-width;//防止机身在窗口外面
		y      = -this.height;
		speed  = 2;
	}
	/**	
	 * 重写撞击（Knocks）接口中的beKnocked()抽象方法，返回值为boolean
	 */
	public boolean beKnocked() {
		return false;
	}
	/**
	 * 重写撞击（Knocks）接口中的getType()抽象方法
	 */
	public void getType() {
		
	}
	/**
	 * 重写父类飞行类（FlyingObj）中的step()抽象方法
	 * 实现小敌机对象的移动
	 */
	public void step() {
		y += speed;//下落运动
	}
}

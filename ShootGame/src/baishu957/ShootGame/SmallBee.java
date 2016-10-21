package baishu957.ShootGame;

import java.util.Random;

/**
 * 小蜜蜂类（BigBee）继承自飞行物类（FlyingObj），实现了奖励（Award）接口
 * @author baishu
 * @version 1.02	2016-05-19
 */
public class SmallBee extends FlyingObj implements Award{

	private int xSpeed;//横移（X轴）速度
	private int ySpeed;//下落（Y轴）速度
	Random random = new Random();
	
	/**	声明构造方法	*/
	public SmallBee() {
		image  = ShootGame.bee;//设置小蜜蜂的图片
		width  = image.getWidth();//设置width的值
		height = image.getHeight();//设置height的值
		x 	   = random.nextInt(400);//设置x的值，在窗口上放随机位置出现
		y 	   = -this.height;//设置y的值，生成小蜜蜂对象时，应该在上方窗口之外
		xSpeed = 1;//设置横移速度
		ySpeed = 2;//设置下落速度
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
	 * 实现大蜜蜂对象的移动
	 */
	public void step() {
		x += xSpeed;//小蜜蜂对象做X轴的横向移动
		/*
		 * 当小蜜蜂对象到达窗口边缘，即x >= 窗口width-this.width时
		 * 改变xSpeed = -xSpeed,使小蜜蜂对象在X轴上做反向移动
		 */
		if (x >= (ShootGame.WIDTH - width)) {
			xSpeed = -xSpeed;
		}
		y += ySpeed;//小蜜蜂对象做下落运动
	}
}

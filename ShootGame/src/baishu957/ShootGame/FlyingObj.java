package baishu957.ShootGame;
import java.awt.image.BufferedImage;//引入图片对象
import java.util.Random;

/**
 * 抽象类：飞行物（FlyingObj）
 * @author baishu
 * @version	1.02	2016-05-19
 */
abstract public class FlyingObj {
	
	protected int 			x;		//x坐标
	protected int 			y;		//y坐标
	protected int 			width;  //图片宽
	protected int 			height; //图片高
	protected BufferedImage image;  //图片对象
	
	/**
	 * 每次对象运动一步的抽象方法step()
	 */
	abstract public void step();
}

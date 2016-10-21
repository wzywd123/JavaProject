package baishu957.ShootGame;
import java.awt.image.BufferedImage;

/**
 * 英雄机类（hero）继承自飞行物类（FlyingObj）
 * @author baishu
 * @version 1.02 2016-05-19
 */
public class Hero extends FlyingObj{
	
	private BufferedImage[] images ; //图片对象数组
	private int index;				 //step()图片运动参数
	
	/**
	 * 英雄机构造方法
	 */
	public Hero() {
		image  = ShootGame.hero0;	 //设置image
		width  = image.getWidth();	 //设置width
		height = image.getHeight();	 //设置height
		x 	   = 150;				 //设置x
		y 	   = 400;				 //设置y
		index  = 0;					 //设置index
		images = new BufferedImage[]{//设置images图片数组
				ShootGame.hero0,
				ShootGame.hero1
		};
	}
	
	/**
	 * 重写父类飞行类（FlyingObj）中的step()抽象方法
	 * 实现英雄机对象的图片运动
	 */
	public void step() {
		//具体的运动设置
		image = images[index++/10%images.length];
	}
	/**
	 * 返回英雄机对象的宽
	 * @return	width
	 */
	public int getWidth(){
		return this.width;
	}
	/**
	 * 返回英雄机对象的高
	 * @return	height
	 */
	public int getHeight(){
		return this.height;
	}
	/**
	 * 英雄机移动
	 * @param x
	 * @param y
	 */
	public void move(int x,int y){
		this.x = x ;
		this.y = y ;
	}
}

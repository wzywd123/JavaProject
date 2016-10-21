package baishu957.ShootGame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ShootGame主类继承自画板类JPanel
 * @author baishu
 * @version 1.02 2016-05-20
 */

public class ShootGame extends JPanel{

	public final static int WIDTH  = 400;//设置画板的宽
	public final static int HEIGHT = 654;//设置画板的高
	
	
	private final static int START = 0;//初始状态
	private final static int PAUSER = 1;//暂停状态
	private final static int RUNNING = 2;//运行状态
	private final static int GAME_OVER = 3;//结束状态
	private static int state = 0;//控制状态参数
	
	public static BufferedImage start;//初始状态图片
	public static BufferedImage background;//背景图片
	public static BufferedImage pause;//暂停状态图片
	public static BufferedImage gameover;//结束状态图片
	public static BufferedImage airplane;//敌机图片
	public static BufferedImage bee;//蜜蜂图片
	public static BufferedImage hero0;//英雄机图片0
	public static BufferedImage hero1;//英雄机图片1
	public static BufferedImage bullet;//子弹图片

	private FlyingObj[] flyings ={};//飞行物（敌人）对象数组
	private Bullent[] bullents ={};//子弹对象数组
	private Hero hero = new Hero();//英雄机对象初始化
	
	static{//加载静态资源
		try {
			//加载图片
			start      = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause	   = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover   = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane   = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee        = ImageIO.read(ShootGame.class.getResource("bee.png"));
			hero0      = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1      = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			bullet     = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * nextOne:FlyingObject对象工厂
	 * @功能：随机生成敌机或蜜蜂对象，并返回
	 */
	
	public static FlyingObj nextOne(){
		Random random = new Random();
		
		int type = random.nextInt(20);//控制对象类型
		if(type % 10 == 0){//返回大蜜蜂对象
			return new BigBee();
		}else if (type % 5 == 0) {//返回小蜜蜂对象
			return new SmallBee();
		}else {//返回敌机对象
			return new SmallPlane();
		}
	}
	private int flyEnterIndex = 0;//控制飞行物入场参数
	/**
	 * enterAction:飞行物和子弹入场方法
	 * @功能：向飞行物（敌机）数组flyings中加入敌机对象
	 */
	public void enterAction(){
		flyEnterIndex ++;//参数自增
		if(flyEnterIndex % 40 == 0){//控制飞行物入场节奏
			flyings = Arrays.copyOf(flyings, flyings.length+1);//飞行物数组扩容
			//调用飞行物对象工厂方法，生成新的飞行物对象加入flyings数组最后一项
			flyings[flyings.length - 1] = ShootGame.nextOne();
			/*同理实现子弹数组的自增和子弹入场*/
			Bullent b = new Bullent(hero.x+hero.getWidth()/2, hero.y - 20);
			bullents = Arrays.copyOf(bullents, bullents.length+1);
			bullents[bullents.length-1] = b;
		}
	}
	
	/**
	 * stepAction:对象运动方法
	 * @功能：控制对象的运动
	 */
	public void stepAction(){
		/*遍历飞行物数组，使飞行物数组中每个对象都调用运动方法step()*/
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
		/*遍历子弹数组，使子弹数组中每个对象都调用运动方法step()*/
		for (int i = 0; i < bullents.length; i++) {
			bullents[i].step();
		}
		/*英雄机对象移动*/
		hero.step();
	}
	
	private int bullentsIndex = 0;//控制子弹入场参数
	
	/**
	 * shootAction: 子弹入场
	 * @功能：实现子弹入场
	 */
	/*public void shootAction(){
		bullentsIndex ++;
		if(bullentsIndex % 40 == 0){
			Bullent b = new Bullent(hero.x+hero.getWidth()/2, hero.y - 20);
			bullents = Arrays.copyOf(bullents, bullents.length+1);
			bullents[bullents.length-1] = b;
		}
	}*/
	
/*	public void heroMoveAction() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				System.out.println(keyCode);
			}
		});
	}*/
	/**
	 * 子弹和敌机撞击判断方法
	 */
	public void boomAction(){
		for (int i = 0; i < bullents.length; i++) {
			for (int j = 0; j < flyings.length; j++) {
				if(bullents[i].beBoom(flyings[j])){
					FlyingObj temp = flyings[flyings.length-1];
					flyings[flyings.length-1] = flyings[j];
					flyings[j] = temp;
					flyings = Arrays.copyOf(flyings, flyings.length-1);
					return;
				}
			}
		}
	}
	private int time01 = 10;//时间间隔
	private KeyAdapter l;
	/**
	 * action：主要方法，控制器，时间间隔启动其他方法
	 * @功能：控制其他方法的执行。按照时间间隔运行其他方法
	 */
	public void action(){
		
		/*	声明一个定时器对戏那个timer	*/
		Timer timer = new Timer();
		/*控制器自带方法*/
		timer.schedule(new TimerTask() {//声明重写匿名内部类
			/**
			 * run：重写的run方法
			 * @功能：控制定时启动运行的方法
			 */
			@Override
			public void run() {
				enterAction();//飞行物和子弹入场方法
				stepAction();//对象运动方法
				boomAction();//判断子弹和敌机相撞
				//heroMoveAction();//英雄机移动
				//shootAction();
				repaint();//重画
			}
		}, time01, time01);
	}
	/**
	 * 重写的paint方法
	 * 画图
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, null);//画背景图
		paintHero(g);//画英雄机图
		paintKnocks(g);//画敌人图
		paintBullents(g);//画子弹图
	}
	//画英雄机图
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	//画敌人图
	public void paintKnocks(Graphics g){
		for (int i = 0; i < flyings.length; i++) {
			FlyingObj f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	//画子弹图
	public void paintBullents(Graphics g){
		for (int i = 0; i < bullents.length; i++) {
			Bullent b = bullents[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	/**
	 * 布置背景画板方法，将frame的布置提出成一个独立的方法，为了避免在staitc中不能访问非静态对象
	 * @param game
	 */
	public void window(ShootGame game) {
		JFrame frame = new JFrame("Fly"); //创建窗口对象
		frame.add(game); //将面板添加到窗口中
		
		frame.setSize(WIDTH, HEIGHT); //设置窗口大小
		frame.setAlwaysOnTop(true); //设置总在最上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置窗口默认关闭操作(关闭窗口则退出程序)
		frame.setLocationRelativeTo(null); //设置窗口初始位置(居中)
		frame.setVisible(true); //1.设置窗口可见  2.尽快调用paint()
		/*对frame添加键盘监听对象*/
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				//super.keyPressed(e);
				int speed = 10;//飞机移动速度
				int x = hero.x;
				int y = hero.y;
				switch (e.getKeyCode()) {//获取键盘输入值，并判断如何移动
				case 37:
					hero.move(x-speed, y);
					break;
				case 38:
					hero.move(x, y-speed);
					break;
				case 39:
					hero.move(x+speed, y);
					break;
				case 40:
					hero.move(x, y+speed);
					break;
				default:
					break;
				}
				
			}
		});
	}
	
	public static void main(String[] args) {

		
		ShootGame game = new ShootGame(); //创建面板对象
		game.window(game);
		game.action();//调用action方法
		
	}

}

###零、写在开始之前
>新人，Java学习中，文章中遗漏错误之处，欢迎斧正
个人博客，完全原创
转载请注明出处。
项目全代码地址：[GitHub](https://github.com/Baishu/JavaProject/tree/master/tetris+%20+%22GitHub%22)
###一、从面向对象的开始，将对象抽象成类
   面对一个程序or实际项目，个人会选择从程序的表面入手，从入目可及的实例对象出发，一步步抽象成类。
对于如何使用面向对象的思想去分析一个项目时，我的理解是：
      
>首先去找出对象间的共性，以此来设计出最基本的类。然后找出对象间的差异性，来设计不同的接口或子类来实现这些差异性 <
	
   以俄罗斯方块程序而言，根据所有的实际对象按照他们共有属性和方法的不同分类所得的结果，我可以把该程序运行过程中的可见的对象可以分为以下三种类型的类型
	
叠块类：各种形态的方块组合对象
信息类：面板上可见且变动的信息对象
	
对于其中的信息类对象和背景图片类对象，因为我们只需要对它们进行展示和赋值，而不需要对它们进行更多的方法操作，赋予它们更多的属性，因此为了程序更加简单，我们不需要为它们创建新的类。之后我们会聊到如何使用swing.JFrame来绘制图片和提示信息。
	对于叠块类对象的分析我们可以得出它们共有的属性和方法，并聚合而成一个叠块类（Tetromino）大致如下
	
所有的叠块类对象都是由4个相同大小的小格子组成的
所有的叠块类对象都有同样的左移、右移和下落方法
    
   所有的叠块类对象都可以进行旋转且暂时假设所有对象遵从相同的旋转法则（事实确实如此）
	由第一条共性中可以提炼出一个不是基本类型也不是任何java自带类的对象：格子（Cell），为此我们需要设计一个格子类（Cell），它的属性和方法大致包括：
     1.用于定位的row, col值
     2.展示的颜色/图像
     3.下落，左右移动的最小单元
	知道所有的叠块类对象的共性，但我们也不能因此而忽视它们最直观的差异所有的叠块类对象一共拥有七类不同的形状和颜色，相同形状的叠块类对象的格子颜色都相同，所以我们根据差异性创建七个类
	综合以上，我们所需要的基本类和她们所属的属性和方法分别为
	
####Cell类
|属性名|类型|修饰词|
|-|::|:|
| row| int |Privaet|
| col| int|Private|
| image| BufferImage|Private |

|方法名|返回类型|传参 | 修饰词  |
|-|::|::|:|
|moveDown|void|-|Private|
|moveLeft|void|-|Private|
|moveRight|void|-|Private|
####Tetromino类
|属性名|类型|修饰词|
|-|::|:|
|cells|Cell[]|Private|

|方法名|返回类型|传参 | 修饰词  |
|-|::|::|:|
|moveDown|void|-|Public|
|moveLeft|void|-|Public|
|moveRight|void|-|Public|
|spin|void|-|Public|

   而对于七种叠块类的子类而言，它们类中没有特有的属性，只需利用每一个类的构造方法实现它们的特征即可。以O类为例，我们创建O类的对象时，我们只需把cells中4个格子对象进行排列就能满足实现特有形状，而在创建每一个cells中的Cell对象时传入的bufferedImage对象则可以实现不同颜色（图像）。

###二、类的实现

当经过第一步的使用面向对象将所有对象抽象成类后，我们需要将设计好的类使用代码实现。具体的实现代码如下
####Cell类
![Cell类的构造器](http://img.blog.csdn.net/20161022235544065)
![Cell类的一般方法](http://img.blog.csdn.net/20161022235209811)
####Tetromino类
![Tetromino类的方法](http://img.blog.csdn.net/20161022235351412)
![Tetromino类中重写toString方法](http://img.blog.csdn.net/20161022235621913)
####特征类以Z类为例
![特征类以Z类为例](http://img.blog.csdn.net/20161022235715507)

   当然我们在类的抽象向还说道Tetromino类中应该拥有旋转叠块类对象的方法，因为该方法对每个特征类都有不同的实现，而且该方法的编写和调试离不开图形实现，因此我们考虑在实现叠块类的下落和移动后在回过头实现旋转方法。
     
   我们已经知道Tetromino类已经不是叠块类的最表层实现类，与之相反，叠块类对象的具体实现类因为类对象的不同特征已经有了七类不同的实现类。这与我们类的抽象的目的——更加精简的类的实现 相违背。为了精简优雅的实现叠块类的初始化，我们可以在Tetromino类中创建一个方法，该方法的具体作用是随机生成一个数字，然后根据该数字来确定返回的对象类型，具体实现如下所示:
![随机返回叠块类对象类型方法](http://img.blog.csdn.net/20161023000013508)


###三、如何才能画好一个俄罗斯方块
接下来设计俄罗斯方块程序的具体实现类——包括程序的逻辑，算法，图形绘制。分以下四个步骤进行静态程序的绘制
####①　静态资源的加载
程序中所谓的静态资源包括图片，视频，音频等，在俄罗斯方块中必须的是所有的图片资源。静态资源的加载一般都在静态块中即static块中加载资源，在本例中加载资源所需要使用的IO类是ImageIO类。所用的方法为该类中的静态方法ImageIO.read();方法

```
Public static BufferedImage read(File/InputStream/URL/ ImageInputStream input)Throw IOException{}
```
假如程序需要读取Tetris类所在包中的”Z.png”资源
```
Tetris.class.getResource("Z.png");
```
又因为ImageIO.read()默认可能会抛出IOException异常，因此在加载图片资源时需要使用异常机制尝试捕获和处理异常。使用try~catch语句将静态资源加载语句封装在一起

```
Static {
	Try {
			BufferedImage Z = ImageIO.read
				(Tetris.class.getResource("Z.png"));
	}catch (Exception e){
		e.printStackTrace();
	}
}

```

####②　画背景图
画图需要使用的javax.swing包中的JFrame类和JPanel类。只说明具体的使用方法:

`Public Component add(Component comp){} `
继承自Componet的方法，确定窗口的对象

`Public void setSize(int width, int height){}`
继承自Window类的方法，约束初始窗口的大小

`Public void setUndecorated(true){}`
设置窗口边框不可见，仅显示图片

`Public void setVisible(true);`
设置窗口的可见性，同时调用paint()方法

`Public void paint(Graphics g){}`
调用画笔画图或贴图

`g.drawImage(BufferedImage, x, y, null);`
贴图方法的一般用法

综合使用以上方法我们就可以将运行窗口显示，并且显示背景图
![backageImage](http://img.blog.csdn.net/20161023000943389)
![main方法中设计窗口](http://img.blog.csdn.net/20161023001007623)

####③　画运动叠块类对象（tetromino）和等待进入叠块类对象（nextone）。
画tetromino和nextone对象相对于画背景图而言，稍稍复杂一些。
首先我们需要先初始化两个对象
```
// 下一个下落对象
	private Tetromino nextone;
// 当前下落对象
	private Tetromino tetromino;
	etromino = Tetromino.ranShape();
	nextone = Tetromino.ranShape();
```
当两个对象初始化完成后，再分别创建两个对应的画对象方法，以paintNextone()为例
![paintNextone()](http://img.blog.csdn.net/20161023101702516)
在paint方法中对paintNextone() 和 paintTetromino() 方法进行调00用即可绘制出程序初运行时第一个tetromino对象和nextone对象的图像位置，示例图该部分讨论完列出。

####④　画提示信息
在俄罗斯方块程序的运行过程中，需要在右侧三个小框中，写上提示信息：SCORE，LINES，LEVEL，对用户进行提示。
在绘制提示信息时，我们需要注意的几个方法和类分别为：
Color类，颜色类；Font类，字体类；
`public void setColor(Color color);`
设置绘画笔的的颜色
`Public void setFont(Font font);`
设置绘画字的字体
`Public void drawString(String str, int x, int y);`
绘制提示信息的方法
最后方法的具体实现如下所示：
![paintMsg](http://img.blog.csdn.net/20161023102119158)

当以上所有俄罗斯方块程序静态部分的代码都完成后，运行程序我们可以得到下面所示的画面：
![静态画面](http://img.blog.csdn.net/20161023102244174)

###四、让它们动起来
经过第三步静态程序的实现，我们可以得到俄罗斯方块程序运行时第一帧的静态图像。这当然不是结束，接下来我们需要让俄罗斯方块程序动起来。
大家想一想在玩俄罗斯方块的过程中程序有哪几个部分或是哪儿几个对象是会动的。
1. 下落的叠块类：tetromino
2. 触底堆叠的叠块类集合：继续堆叠&消除行
3. 提示信息：score，lines，level
它们之间的联系为，tetromino的运动导致触底判定是否置入堆叠的叠块类集合wall，wall中判定是否继续堆叠or消除行的判定导致了score等信息的更新。因此在对象进行运动Action的绑定时我们可以根据它们间的关系来一步步的实现程序的运动。

####①　Action方法与定时器和监听器的绑定
在开始运动之前我们需要将运动方法与定时器进行绑定，定时器的相关类为Timer和TimerTask。

`Public void schedule(TimerTask task, long delay, long period);`
设置定时器内方法（new TimerTask.run）执行的时间间隔
`Public abstract void run();`
TimerTask抽象类的抽象方法，因此当程序想用定时器实现定时执行代码，需要对run()方法进行实现，而一般的实现方式为匿名内部类。
```
Timer timer = newTimer();
TimerTask task = new TimerTask() {
@override
Public void run() {
//定时器定时执行部分
}
}
//定时器的定时执行装置
Timer.schedcule(task, 10, 10);
```
而除了绑定Action和定时器是对象运动的关键外，最终要的时调用repaint()方法，该方法的最用时调用paint()方法对窗口所有对象进行重画，也就是利用这个方法在一秒内对窗口进行几十次的重画，才能实现对象的运动。当然时相对运动，对象运动的本质是一组静态对象的集合图形在短时间内交替改变。
就像时小时候的连环画，当我们让连环画快速的翻动时就会产生连环画中人物活过来动的感觉。同理其实视频也是由一秒内几十上百帧的画面的切换实现动态的效果。

我们知道俄罗斯方块叠块类的运动是与键盘的键位相绑定的，我们可以需要使用不同的键位控制对象运动，并且为了实现运动效果我们同时也需要将按键动作与repaint()绑定。在对对象的运动控制中我们需要对以下键位进行监听。
|动作|键位|
|-|:|
|下落|KEY_DOWN|
|左移|KEY_LEFT|
|右移|KEY_RIGHT|
|旋转|KEY_UP|

键盘监听类的具体用法一般为匿名内部类来实现KeyListener接口和接口内的`KeyPressed(KeyEvent e)`抽象方法，使用向上造型的方式将KeyAdapter类对象指向KeyListener接口的引用。在`keyPressed(KeyEvent e)`内我们可以使用KeyEvent类内的`getkeyChar()`,`getKeyCode()`等方法获取当前触发按键动作所发出的键位信息。根据所获取的键位信息的不同我们可以调用不同的动作（Action）方法来实现对象的不同运动方式。
为了代码可维护性和可读性，我们将根据键位信息来执行不同运动的判断方法进行封装。
```
KeyListener k = new KeyAdapter() {
	@override
	Pulic void KeyPressed(KeyEvent e) {
		Int key = e.getCode();
		KeyMoveAction(key);
	}
}
Public void KeyMoveAction(int key) {
	switch (key) {
		//System.out.println(“这个是键位信息的调试输出” + key);
		case KeyEvent.VK_RIGHT:moveRightAction();break;
		case KeyEvent.VK_LEFT:moveLeftAction();break;
		case KeyEvent.VK_DOWN:moveDownAction();break;
		case KeyEvent.VK_UP:spinCellAction();break;
	}
}
```

####②　Tetromino的运动实现

#####下落实现
创建一个moveDownAction()方法，将其调用放入上述代码的定时器定时执行部分。而其具体实现十分简单，只要在方法体中让tetromino对象调用其moveDown()方法即可，如下
```
Public void moveDownAction() {
tetromino.moveDown();
}
```
但是大家可以发现，叠块类对象在下落的过程中不会停止，而会一直不停的下落。这时候我们需要设置一个判定条件or判定方法来确定对象下落到何处停止。当然，一个完整的功能最好将其封装成一个方法。因此我们需要在创建一个`isBottom()`方法来判断当前对象是否可以停止下落。
分析对象停止下落的条件：
1. 叠块类对象触底
2. 叠块类对象底部接触到其他叠块类对象集合

在对下落条件进行具体分析前，我们需要声明一个二维数组规定下tetromino的运动范围，同时也可以作为存储停止运动后的格子容器。
```
Private static int ROWS = 20；
Private static int COLS = 10；
Private Cell[][] wall = new Cell[20][10];//格子容器
```
在得到一个空wall数组后，我们分析下落停止的条件。
第一个条件，叠块类对象触底即意味着，当前对象中cells内的4个格子有一个或几个到达wall数组的最底端也就是某一Cell对象的row值等于最大值（ROWS-1）。
第二个条件，叠块类对象中某一Cell对象接触到了其他Cell对象集合（即wall数组的顶部）。对于叠块类对象的cells内的一个或多个格子接触到了wall数组中的已存在的格子对象，即cells内的某个格子下方存在格子对象。可以根据当前格子下方即`wall[row+1][col]`是否存在格子类判定接触。
综合以上两个判定条件我们需要对叠块类对象中cells数组的格子对象进行遍历，然后根据不同情况进行判定。具体代码如下
![isBottom()](http://img.blog.csdn.net/20161023104431200)
最后综合isBottom()方法对下落方法moveDownAction()进行修改可有
```
Public void moveDownAction() {
	If(!isBottom) {
		tetromino.moveDown();
	}
}
```
#####左右移动实现
创建两个方法`moveLeftAction()`&`moveRightAction()`。与`moveDownAction()`类似，我们在方法体中使用`tetromino.moveLeft() `/ `tetromino.moveRight()`方法。同样的我们也需要面对左右移动的边界问题。下落实现时分析的判定条件的基础上我们可以知道，左右移动实现同样面对着这两个问题。
1. 左右移动到达边界问题；
2. 左右移动接触到已有格子对象问题。
而且因为左移与右移的判定条件相似但是完全相反，因此我们需要同时创建两个对应的判定方法`canLeftMove()` & `canRightMove()`。结合分析问题得出的条件和下落时的判定方法`isBottom()`的设计。以右移和其判定方法为例子
![canrightMove](http://img.blog.csdn.net/20161023104753639)
![moveRight](http://img.blog.csdn.net/20161023104849545)

#####tetromino旋转方法的实现
旋转方法时俄罗斯方块中最重要的方法之一，它是实现了俄罗斯方块如此精彩耐玩的基础。在对旋转方法进行设计之前，需要对俄罗斯方块中各种叠块类的旋转特点进行分析和比较,经过分析，所有叠块类都可以分成两类，适用与两种不同的算法：
1. O型对象，因为O型对象属于圆心对称，因此无论O型对象如何旋转都不会改变形态
2. 其他型对象，是的其他类型对象的旋转形式各种各样，但是有句话说得好，万变不离其宗，当我们在对象的四个格子中选择一个作为圆心即旋转中心点时，各个旋转格子对象与旋转中心点的关系如下所有

A = iRow - iCol + b;
B = iRow + iCol - a;

旋转中心点O(iRow, iCol)，格子旋转前M(a, b)，格子旋转后N(A, B);

找到旋转方法所需要的算法后，就可以开始进行旋转方法的设计和实现了，但是在实现方法之前我们需要明确以下问题

当前对象是否可以进行旋转？or 对象何时可以进行旋转?

我们在对对象进行下落，左右移动的操作时都有关注对象是否可以继续左右移动或下落的判定，那么同样的我们在对对象进行旋转操作时也不可以忽略对对象进行相关问题的研究和讨论。结合下落判定和左右移动判定的经验，俄罗斯方法叠块类对象旋转方法有以下判定条件

1. 当对象旋转时，旋转结果对象的cells内是否有格子越界，即超出wall数组的边界
2. 当对象旋转时，旋转结果对象的cells内是否有格子与wall内已存在元素重叠

以上两个判定条件都需要或是依赖于对旋转结果与wall数组的比较，而这就产生了一个问题，我们的`tetromino.spin()`方法定义于Tetromino类中，而判定条件需要的wall数组存在于Tetris类中且属于类的私有属性。Tetromino的spin()方法中无法调用Tetris类中的私有属性wall数组，无法做到在spin()方法中对结果进行判定。因此在此处我们需要将tetromino.spain()的旋转结果以Cell数组的形式返回到Tetris类中，然后根据判定条件对返回结果进行判定，若判定结果为true，则将tetromino.cells指向返回对象的引用，否则return结束当前方法的执行。

以上分析我们可以知道需要至少两个方法来进行合理的旋转运动
`public Cell[] spin(){}`
`public void spinCellAction(){}`
其他注意点：
在定义spin()方法，我们需要注意的是：

1. 在初始化iCells数组时不可以将this.cells直接指向iCells数组的引用
2. 必须对iCells数组元素进行初始化，不然返回结果中的Cell元素会产生NullPointException异常。
![旋转方法](http://img.blog.csdn.net/20161023105747818)
而在设计实现spinCellAction()方法时，我们需要注意：
	1. 判定条件的先后问题
	2. Wall数组的边界问题
![判定方法](http://img.blog.csdn.net/20161023105902632)

####③	Wall数组中元素存储与删除

其实这个步骤的一部分应该在左右移动和下落运动时就需要实现了，那就是把停止下落的叠块类对象中cells的格子对象存入wall数组中对应的位置。
Wall数组元素的存入，当某叠块类对象停止下落时，我们可以遍历该对象cells内的格子元素，并将它们存入到wall数组中对应的位置。这部分代码在之前的isBottom()方法内已经实现。伪代码示意如下
```
//当确定当前对象运动到底部即停止时，将该对象的cells内的格子元素存入wall内
for(int j = 0; j < cells.length; j++) {
	Cell cell = cells[j];
	int col1 = cell.getCol();
	int row1 = cell.getRow();
	wall[row1][col1] = cell;
}
```
当前tetromino对象停止运动并存入wall数组内时，我们需要将nextone的对象赋给tetromino对象，同时还需要将nextone对象重新初始化，即nextone = Tetromino.ranShape()，实现更新tetromino和nextone对象的作用。以上是wall数组的存储原理和方法。

Wall数组消除行方法。先创建一个名为`removeLine()`的方法，每此当wall数组内元素有变化即wall内新存入元素时调用`removeLine()`方法。
`removeLine()`方法的设计是简单的双循环判定方法。设置一个特征值比如
`Boolean flag = true；`
外循环为row[0~20)，内循环为col[0~10)，每当内循环开始时，若存在`wall[row][col] == null`则设置`flag = false`，跳出当前内循环，更新`flag = true`，`row++`，重新开始内循环。若完成一个内循环后`flag == true`，则设置当前row下的所有元素`wall[row][0~9] = null`。同时将小于当前row的所有行下降即row-1。最后为了实现信息的更新将`Scone+=10`、`lines+=1`。
![removeLine](http://img.blog.csdn.net/20161023110711976)

####④	提示信息的更新
在“wall数组中的元素存储与消除”中已经包括了对提示信息的更新。SCORE和LINES只要要自增就可以了。
```
score += 10;
lines += 1;
```
但是对于LEVEL游戏难度而言，它的改变还是需要进行一番规定的。首先LEVEL数据改变的条件是`lines/10 == 0`,也就是说每当消除10行格子后就可以改变LEVEL值。但是在此程序中我们的采用LEVEL来控制格子速度`speed = 5*LEVEL`,所以LEVEL值时从高到低，格子移速越来越快，而LEVEL最低不能为0，否则speed会变为0.所以综合起来LEVEL的算法如下
`level = lines%10 == 0?level == 1?level:level-1:level;`
当然此时更新的是后台数据，要想实现可见的信息更新仍旧需要repaint()和paintTabs()两个方法的组合配合。

###五、控制它们，不受控制的程序是不可靠的

对于一组程序，与我而言，最重要的工作不是让它动起来，而是如何控制它的运动。不受控制的程序是不稳定的。当然现在写的只是简单的俄罗斯方块小程序而已，233。
如何控制一组俄罗斯方块程序，除了以上控制运动章节所提及的对象地运动控制之外，还有程序整体的状态控制，比如暂停，初始化，退出等等状态或功能。将它们的触发与退出与键盘的键位进行绑定
|状态or功能|键位|
|:|:|
|暂停（Pause）|P|
|继续（Continue）|C|
|初始化（initialize）|I|
|游戏结束（gameover)|null|
|退出（Exit）|E|

在Tetris类中声明暂停or正常运行的特征值STATE，默认值为true。根据键位监听器方法的设置和控制状态的要求，我们在keyMoveAction()方法中加入如下代码：
```
case KeyEvent.VK_I:moveInitAction();break;
case KeyEvent.VK_P:STATE = false;break;
case KeyEvent.VK_C:STATE = true;break;
case KeyEvent.VK_E:System.exit(0);break;
```
因为在初始化时需要重置的对象与信息较多，因此将这部分代码封装成一个方法，方便后期的调试和调用。
![初始化](http://img.blog.csdn.net/20161023111418370)

而对于游戏结束状态而言时不需要使用键盘进行控制的，它需要的对游戏是否可以继续进行的判定，以及最重要的是对游戏结束时游戏程序界面的重画以及提示信息GameOver的展示。同理我们也需要对暂停状态的游戏程序界面进行重画。为此我们需要在paint()方法中增加两个重画方法`paintGameOver()` & `paintGamePause()`。对它们的调用依据为新增方法`isGameOver()`的返回结果和STATE的值。

如何实现暂停，实现暂停方法的逻辑很简单就是当程序进入暂停模式时，将对象运动相关的方法全部挂起即可，此程序中进入暂停状态的标识是 `STATE = false`。也就可以在定时器中添加一个条件判断语句，当程序处于正常状态条件成立即可运行程序，正常执行action相关方法，若STATE为false则将程序中的运动方法挂起，等待下一次状态改变。
```
if (STATE) {
	if (moveIndex % speed == 0) {
		moveDownAction();
		moveIndex = 0;
	}
}
```

`isGameOver()`方法的作用为判断当前游戏进程是否可以继续进行，该判断的依据于wall数组内当row = 0的行内是否存在格子。若存在返回true示意游戏结束，否则返回false。
```
public boolean isGameOver() {
	for(int col = 0; col < COLS; col++) {
		if (wall[0][col] != null)return true;
	}
	return false;
}
```
而对于`paintGameOver()` & `paintGamePause()`而言，两者是否需要绘制都依据于对STATE值和`isGameOver`的值的判定
![paintGameOver](http://img.blog.csdn.net/20161023111835184)
![paintGamePause](http://img.blog.csdn.net/20161023111905551)

同时因为状态和功能之间有相斥或依赖的关系，比如继续”continue”功能必须时程序处于暂停”pause”时才有效，游戏结束”gameOver时，则需要进行初始化设置，而无法对程序进行暂停处理或”continue”继续处理。这些程序间的逻辑关系和细节处理需要进一步的分析。还可以对程序的更多可能性状态进行添加。

###六、让程序看起来更有趣
在很多时候看来，编程从来都是一项枯燥无聊的活动，屏幕的幽幽蓝光映镜片，一天要坐着至少9h，多可怕。如果我们不能从程序中找到更多的乐趣（恶趣），那坚持说起来简单做起来真的时太难了。就算无法完成，但至少想起可乐就行。
①　外观大改造，PS，DIY更有趣的程序界面和背景图片
当图片轮换条件满足时，改变程序的背景图片
背景图片的静态资源加载 
背景图片的轮换的条件——轮换条件可以与`isBottom(){}`，`removeLine(){}`，
`keyPressed(Event e){}`等方法进行绑定，当方法中的条件成立时图片改变。为了简化程序的代码量，我们借助与变量BGI来完成图片轮换。
```
  //控制背景图片
 BGI = (BGI == 3)?0:BGI+1;
 g.drawImage(bgi[BGI], 0, 0, null);
```
 当然还可将程序配上个性音效辣，手动设置游戏难度等等。
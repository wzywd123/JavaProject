package baishu957.ShootGame;

/**
 * Award奖励接口继承自Knocks撞击接口
 * 设置默认奖励类型常量DOUBLE_LIFE，ADD_LIFE，ALL_IN
 * @author baishu
 * @version 1.02 2016-05-19
 */
public interface Award extends Knocks{
	int DOUBLE_FIRE = 0;//双倍火力
	int ADD_LIFE    = 1;//增加生命
	int ALL_IN      = 2;//两者都有
}

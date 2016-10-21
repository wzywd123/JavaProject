package baishu957.ShootGame;

/**
 * 撞击（Knocks）接口
 * @author baishu
 * @version	1.02	2016-05-19
 */
public interface Knocks {
	/**	判断是否被撞	*/
	public boolean beKnocked();
	/**	若被撞，获取返回值	*/
	public void getType();
}

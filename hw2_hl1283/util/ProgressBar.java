package hw2_hl1283.util;


/**
 * 显示进度条(参数：当前点、结束点、进度条长度)
 * example:
 * 		System.out.println(ProgressBar.showBarByPoint(30, 100, 20));
 * result:
 * 		[----->              ] 30.00%
 * @author gondi
 *
 */
public class ProgressBar {
	
	/**
	 * 显示进度条
	 * @param currentPoint 当前点
	 * @param finishPoint 结束点
	 * @param barLength 进度条长度(字符)
	 * @return 进度条结果
	 */
	public static String showBarByPoint(double currentPoint, double finishPoint, int barLength) {
		// 根据进度参数计算进度比率
		double rate = currentPoint / finishPoint;
		// 根据进度条长度计算当前记号
		int barSign = (int) (rate * barLength);
		// 生成进度条
		return makeBarBySignAndLength(barSign, barLength) + String.format(" %.2f%%", rate * 100);
	}
	
	/**
	 * 构造进度条
	 * @param barSign 进度条标记(当前点)
	 * @param barLength 进度条长度
	 * @return 字符型进度条
	 */
	private static String makeBarBySignAndLength(int barSign, int barLength) {
		StringBuilder bar = new StringBuilder();
		bar.append("[");
		for (int i=1; i<=barLength; i++) {
			if (i < barSign) {
				bar.append("-");
			} else if (i == barSign) {
				bar.append(">");
			} else {
				bar.append(" ");
			}
		}
		bar.append("]");
		return bar.toString();
	}
	
}


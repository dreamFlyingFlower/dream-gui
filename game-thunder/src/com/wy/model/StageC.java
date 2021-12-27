package com.wy.model;

import com.wy.view.GamePanel;

public class StageC {
	private static Battle _battle;

	static EnemyTable stageC[] = { new EnemyTable(0, 0, 50F, -50F, 3),
			new EnemyTable(0, 0, 100F, -50F, 3), new EnemyTable(0, 1, 150F, -50F, 3),
			new EnemyTable(0, 1, 200F, -50F, 3), new EnemyTable(0, 2, 280F, -50F, 4),
			new EnemyTable(0, 2, 360F, -50F, 4), new EnemyTable(0, 3, -20F, 450F, 5),
			new EnemyTable(0, 4, 500F, 450F, 6), new EnemyTable(0, 4, 550F, 450F, 6),
			new EnemyTable(3, 6, 80F, -50F, 0), new EnemyTable(3, 6, 320F, -50F, 1),
			new EnemyTable(0, 7, 100F, -50F, 2), new EnemyTable(0, 7, 160F, -50F, 2),
			new EnemyTable(0, 7, 200F, -50F, 2), new EnemyTable(1, 9, 100F, -50F, 0),
			new EnemyTable(1, 9, 200F, -50F, 0), new EnemyTable(1, 9, 350F, -50F, 0),
			new EnemyTable(0, 10, 130F, -50F, 0), new EnemyTable(0, 10, 250F, -50F, 1),
			new EnemyTable(2, 13, 80F, -50F, 0), new EnemyTable(2, 13, 240F, -50F, 1),
			new EnemyTable(0, 14, 210F, -50F, 2), new EnemyTable(0, 14, 260F, -50F, 2),
			new EnemyTable(0, 14, 310F, -50F, 2), new EnemyTable(3, 17, 100F, -50F, 0),
			new EnemyTable(3, 17, 150F, -50F, 0), new EnemyTable(0, 18, 320F, -50F, 2),
			new EnemyTable(0, 18, 260F, -50F, 2), new EnemyTable(0, 18, 210F, -50F, 2),
			new EnemyTable(0, 20, -50F, 400F, 3), new EnemyTable(0, 20, 500F, 400F, 4),
			new EnemyTable(2, 21, 150F, -50F, 0), new EnemyTable(2, 21, 300F, -50F, 1),
			new EnemyTable(1, 24, 100F, -50F, 0), new EnemyTable(1, 24, 300F, -50F, 0),
			new EnemyTable(2, 25, 200F, -50F, 0), new EnemyTable(0, 27, 60F, -50F, 2),
			new EnemyTable(0, 27, 120F, -50F, 2), new EnemyTable(0, 27, 280F, -50F, 2),
			new EnemyTable(3, 28, 270F, -50F, 1), new EnemyTable(3, 28, 320F, -50F, 1),
			new EnemyTable(0, 30, 90F, -50F, 3), new EnemyTable(0, 30, 160F, -50F, 3),
			new EnemyTable(0, 30, 170F, -50F, 3), new EnemyTable(0, 31, 300F, -50F, 4),
			new EnemyTable(0, 31, 400F, -50F, 4), new EnemyTable(1, 34, 100F, -50F, 0),
			new EnemyTable(1, 34, 200F, -50F, 0), new EnemyTable(1, 34, 400F, -50F, 0),
			new EnemyTable(3, 35, 340F, -50F, 1), new EnemyTable(0, 37, 150F, -50F, 3),
			new EnemyTable(0, 37, 400F, -50F, 3), new EnemyTable(0, 38, 250F, -50F, 3),
			new EnemyTable(0, 38, -40F, 450F, 5), new EnemyTable(0, 38, 500F, 450F, 6),
			new EnemyTable(0, 38, 550F, 450F, 6), new EnemyTable(2, 39, 120F, -50F, 0),
			new EnemyTable(2, 39, 400F, -50F, 0), new EnemyTable(3, 42, 120F, -50F, 0),
			new EnemyTable(3, 42, 320F, -50F, 1), new EnemyTable(0, 43, 250F, -50F, 3),
			new EnemyTable(0, 43, -20F, 450F, 5), new EnemyTable(0, 44, 500F, 450F, 6),
			new EnemyTable(0, 44, 550F, 450F, 6), new EnemyTable(2, 47, 120F, -50F, 0),
			new EnemyTable(3, 48, 200F, -50F, 0), new EnemyTable(1, 51, 220F, -50F, 0),
			new EnemyTable(1, 50, 340F, -50F, 0), new EnemyTable(0, 51, 90F, -50F, 2),
			new EnemyTable(0, 51, 120F, -50F, 2), new EnemyTable(0, 51, 160F, -50F, 2),
			new EnemyTable(0, 52, 280F, -50F, 2), new EnemyTable(5, 56, 50F, -250F, 0) };

	public StageC(Battle battle) {
		StageC._battle = battle;
	}

	public static void start() {
		for (int i = 0; i < stageC.length; i++)
			if ((double) stageC[i].time == (double) GamePanel.time / 50D)
				if (stageC[i].enemyKind == 0)
					GamePanel.addList(
							new EnemyA(stageC[i].x, stageC[i].y, _battle, stageC[i].pattern));
				else if (stageC[i].enemyKind == 1)
					GamePanel.addList(
							new EnemyB(stageC[i].x, stageC[i].y, _battle, stageC[i].pattern));
				else if (stageC[i].enemyKind == 2)
					GamePanel.addList(
							new EnemyC(stageC[i].x, stageC[i].y, _battle, stageC[i].pattern));
				else if (stageC[i].enemyKind == 5) {
					GamePanel.addList(new BossC(_battle));
				}
	}
}
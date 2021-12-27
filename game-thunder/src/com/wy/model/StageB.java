package com.wy.model;

import com.wy.view.GamePanel;

public class StageB {

	private static Battle _battle;

	static EnemyTable stageB[] = { new EnemyTable(2, 0, 50F, -50F, 0),
			new EnemyTable(2, 1, 340F, -50F, 1), new EnemyTable(0, 2, 80F, -50F, 2),
			new EnemyTable(0, 2, 100F, -50F, 2), new EnemyTable(0, 2, 200F, -50F, 2),
			new EnemyTable(0, 2, 300F, -50F, 2), new EnemyTable(1, 4, 100F, -50F, 0),
			new EnemyTable(1, 4, 300F, -50F, 0), new EnemyTable(0, 5, 80F, -50F, 0),
			new EnemyTable(0, 5, 340F, -50F, 1), new EnemyTable(0, 7, 320F, -50F, 2),
			new EnemyTable(0, 7, 260F, -50F, 2), new EnemyTable(0, 8, 230F, -50F, 2),
			new EnemyTable(0, 8, 180F, -50F, 2), new EnemyTable(3, 9, 100F, -50F, 0),
			new EnemyTable(0, 12, 90F, -50F, 2), new EnemyTable(0, 12, 70F, -50F, 2),
			new EnemyTable(0, 13, 100F, -50F, 2), new EnemyTable(0, 13, 140F, -50F, 2),
			new EnemyTable(0, 13, 200F, -50F, 2), new EnemyTable(2, 14, 320F, -50F, 1),
			new EnemyTable(1, 16, 340F, -50F, 0), new EnemyTable(2, 17, 120F, -50F, 0),
			new EnemyTable(0, 19, 90F, -50F, 3), new EnemyTable(0, 19, 120F, -50F, 3),
			new EnemyTable(0, 20, 160F, -50F, 3), new EnemyTable(0, 20, 200F, -50F, 3),
			new EnemyTable(0, 21, 280F, -50F, 4), new EnemyTable(0, 21, 300F, -50F, 4),
			new EnemyTable(0, 21, 330F, -50F, 4), new EnemyTable(0, 23, -20F, 450F, 5),
			new EnemyTable(0, 23, -20F, 450F, 6), new EnemyTable(0, 24, 70F, -50F, 0),
			new EnemyTable(0, 24, 100F, -50F, 0), new EnemyTable(3, 26, 80F, -50F, 0),
			new EnemyTable(3, 26, 320F, -50F, 1), new EnemyTable(0, 27, 260F, -50F, 2),
			new EnemyTable(1, 29, 100F, -50F, 0), new EnemyTable(1, 29, 300F, -50F, 0),
			new EnemyTable(0, 30, 70F, -50F, 0), new EnemyTable(2, 32, 100F, -50F, 0),
			new EnemyTable(2, 32, 340F, -50F, 0), new EnemyTable(0, 33, 280F, -50F, 2),
			new EnemyTable(0, 35, 60F, -50F, 3), new EnemyTable(0, 35, 100F, -50F, 3),
			new EnemyTable(0, 36, 150F, -50F, 3), new EnemyTable(0, 36, 200F, -50F, 3),
			new EnemyTable(0, 37, 280F, -50F, 4), new EnemyTable(0, 37, 300F, -50F, 4),
			new EnemyTable(0, 37, 320F, -50F, 4), new EnemyTable(0, 38, -20F, 450F, 5),
			new EnemyTable(0, 38, -20F, 450F, 6), new EnemyTable(0, 39, 70F, -50F, 0),
			new EnemyTable(0, 39, 90F, -50F, 0), new EnemyTable(2, 41, 40F, -50F, 0),
			new EnemyTable(3, 41, 320F, -50F, 1), new EnemyTable(1, 43, 150F, -50F, 0),
			new EnemyTable(1, 43, 320F, -50F, 0), new EnemyTable(2, 45, -50F, 0.0F, 0),
			new EnemyTable(2, 45, 500F, 0.0F, 1), new EnemyTable(5, 49, 50F, -250F, 0) };

	public StageB(Battle battle) {
		StageB._battle = battle;
	}

	public static void start() {
		for (int i = 0; i < stageB.length; i++)
			if ((double) stageB[i].time == (double) GamePanel.time / 50D)
				if (stageB[i].enemyKind == 0)
					GamePanel.addList(
							new EnemyA(stageB[i].x, stageB[i].y, _battle, stageB[i].pattern));
				else if (stageB[i].enemyKind == 1)
					GamePanel.addList(
							new EnemyB(stageB[i].x, stageB[i].y, _battle, stageB[i].pattern));
				else if (stageB[i].enemyKind == 2)
					GamePanel.addList(
							new EnemyC(stageB[i].x, stageB[i].y, _battle, stageB[i].pattern));
				else if (stageB[i].enemyKind == 5) {
					GamePanel.addList(new BossB(_battle));
				}
	}
}
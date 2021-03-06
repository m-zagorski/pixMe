package com.example.combat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import com.example.entities.Characters;
import com.example.entities.MainCharacter;
import com.example.entities.Monsters;
import com.example.entities.NormalMonster;
import com.example.gamedata.GameDatabase;
import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Map;
import com.example.pixme.Tawern;

public class OneCharacterMaps extends Activity implements OnTouchListener {
	OurView v;
	Context context;

	// COMBAT VARIABLES
	public enum State {
		PLAYERR, MONSTER
	}

	public enum Monster {
		ONE, TWO, THREE, FOUR
	}

	State state = State.PLAYERR;
	Monster whichMonster = Monster.ONE;
	boolean monsterOne = true, monsterTwo = false, monsterThree = false,
			MonsterFour = false;
	boolean playerAttack = true;
	boolean playerFirstSkill = false, playerSecondSkill = false,
			playerThirdSkill = false;
	boolean monsterAttack = true;
	long damage = 0;
	long dotDamage = 0;

	int stun = 0;
	boolean dot = false;
	int dotTick = 0;
	boolean dotTickShowDamage = false;
	boolean thirdSpecialAttack = false;

	int monstersAlive = 0;
	boolean firstM = true, secondM = true, thirdM = true, fourthM = true;

	int yPlayer, yMonster, yMonsterSecond, yMonsterThird, yMonsterFourth;
	int screenWidth, screenHeight;
	int playerLevel;

	MediaPlayer combatmusic;

	GameDatabase maps = null;
	GameSharedPreferences appPrefs;
	Cursor mapsCursor;
	Cursor monstersCursor;
	ArrayList<Map> allMaps = new ArrayList<Map>();
	ArrayList<com.example.gamedata.Monster> allMonsters = new ArrayList<com.example.gamedata.Monster>();
	Paint cooldowns, cdFade, health, damagePaint, damageDotPaint, healthBar,
			healthBarS;
	Map currentMap = null;

	Bitmap backgroundBitmap, playerBitmap, firstMonsterBitmap,
			secondMonsterBitmap, thirdMonsterBitmap, fourthMonsterBitmap;
	Bitmap firstSkill, secondSkill, thirdSkill;
	Bitmap firstSkillBitmapCd, secondSkillBitmapCd, thirdSkillBitmapCd;
	int firstSkillCd = 0, secondSkillCd = 0, thirdSkillCd = 0;
	Characters player = null;
	Monsters firstMonster = null, secondMonster = null, thirdMonster = null,
			fourthMonster = null;
	Monsters attackedMonster = null;

	Timer timer = new Timer();

	// --

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = getApplicationContext();
		v = new OurView(this);
		v.setOnTouchListener(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		appPrefs = new GameSharedPreferences(context);
		playerLevel = Integer.parseInt(appPrefs.characterLevel());
		maps = new GameDatabase(this);
		maps.open();
		fillMapsList();
		fillMonstersList();
		maps.close();
		// BITMAPS LOAD
		// backgroundBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.haunted_forest2);
		initializeBitmaps();
		createPaints();

		// ---

		// -- MUSIC
		combatmusic = MediaPlayer.create(this, (getResources().getIdentifier(
				"combat" + new Random().nextInt(2), "raw", getPackageName())));
		if (appPrefs.getMusicStatus().equals("yes")) {
			combatmusic.start();
		}

		// --

		setContentView(v);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		combatmusic.release();
		v.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}

	private void fillMapsList() {
		mapsCursor = maps.getAllMaps();
		startManagingCursor(mapsCursor);
		updateMapsList();
	}

	private void fillMonstersList() {
		monstersCursor = maps.getAllMonsters();
		startManagingCursor(monstersCursor);
		updateMonstersList();
	}

	private void updateMapsList() {
		mapsCursor.requery();

		allMaps.clear();

		if (mapsCursor.moveToFirst()) {
			do {
				String name = mapsCursor.getString(maps.NAME_COLUMN);
				String monstersCount = mapsCursor
						.getString(maps.MONSTERSCOUNT_COLUMN);
				String gold = mapsCursor.getString(maps.BASEGOLD_COLUMN);
				String exp = mapsCursor.getString(maps.BASEEXPERIENCE_COLUMN);
				Map newMap = new Map(name, monstersCount, gold, exp);
				allMaps.add(newMap);
			} while (mapsCursor.moveToNext());
		}
	}

	private void updateMonstersList() {
		monstersCursor.requery();

		allMonsters.clear();

		if (monstersCursor.moveToFirst()) {
			do {
				String icon = monstersCursor.getString(maps.MONSTERICON_COLUMN);
				String type = monstersCursor.getString(maps.MONSTERTYPE_COLUMN);
				String health = monstersCursor
						.getString(maps.MONSTERHEALTH_COLUMN);
				String damage = monstersCursor
						.getString(maps.MONSTERDAMAGE_COLUMN);
				String armor = monstersCursor
						.getString(maps.MONSTERARMOR_COLUMN);
				String coords = monstersCursor
						.getString(maps.MONSTERCOORDS_COLUMN);
				com.example.gamedata.Monster newMonster = new com.example.gamedata.Monster(
						icon, type, health, damage, armor, coords);
				allMonsters.add(newMonster);
			} while (monstersCursor.moveToNext());
		}
	}

	private void initializeBitmaps() {
		Random r = new Random();
		currentMap = allMaps.get(r.nextInt(allMaps.size()));
		String mapName = currentMap.getName();

		backgroundBitmap = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(mapName, "drawable",
						getPackageName()));
		// playerBitmap=BitmapFactory.decodeResource(getResources(),
		// R.drawable.barbarian);
		playerBitmap = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(appPrefs.getCharacterClass(),
						"drawable", getPackageName()));

		firstSkill = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillfirst",
						"drawable", getPackageName()));
		secondSkill = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillsecond",
						"drawable", getPackageName()));
		thirdSkill = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillthird",
						"drawable", getPackageName()));

		firstSkillBitmapCd = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillfirstcd",
						"drawable", getPackageName()));
		secondSkillBitmapCd = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillsecondcd",
						"drawable", getPackageName()));
		thirdSkillBitmapCd = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						appPrefs.getCharacterClass() + "skillthirdcd",
						"drawable", getPackageName()));
	}

	private void createPaints() {
		Typeface dotDamageFont = Typeface.createFromAsset(getAssets(),
				"kree.ttf");
		Typeface cdFont = Typeface.createFromAsset(getAssets(), "cooldown.ttf");

		cooldowns = new Paint();
		cooldowns.setStyle(Paint.Style.FILL);
		cooldowns.setAntiAlias(true);
		cooldowns.setTextSize(60);
		cooldowns.setTypeface(cdFont);
		cooldowns.setColor(Color.rgb(242, 240, 216));

		damageDotPaint = new Paint();
		damageDotPaint.setTypeface(dotDamageFont);
		damageDotPaint.setStyle(Paint.Style.FILL);
		damageDotPaint.setAntiAlias(true);
		damageDotPaint.setTextSize(80);
		damageDotPaint.setColor(Color.rgb(89, 49, 87));

		damagePaint = new Paint();
		damagePaint.setTypeface(dotDamageFont);
		damagePaint.setStyle(Paint.Style.FILL);
		damagePaint.setAntiAlias(true);
		damagePaint.setTextSize(80);
		// damagePaint.setColor(Color.argb(0, 166, 36, 36));
		damagePaint.setColor(Color.rgb(166, 36, 36));

		cdFade = new Paint();
		cdFade.setARGB(90, 0, 0, 0);

		healthBar = new Paint();
		healthBar.setColor(Color.rgb(166, 36, 36));

		healthBarS = new Paint();
		healthBarS.setColor(Color.BLACK);
		healthBarS.setStyle(Paint.Style.STROKE);

	}

	public class OurView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;
		boolean isOk = false;

		public OurView(Context context) {
			super(context);
			holder = getHolder();
		}

		@Override
		public void run() {
			// CREATE ChARS AND MONSTERS

			DisplayMetrics deviceDisplayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(
					deviceDisplayMetrics);
			screenWidth = deviceDisplayMetrics.widthPixels;
			screenHeight = deviceDisplayMetrics.heightPixels;

			player = new MainCharacter(this, playerBitmap, 100, 150,
					playerLevel, appPrefs.getCharacterClass(),
					appPrefs.getCharacterSkills());
			initializeMonsters(this);

			// firstMonster= new NormalMonster(this, firstMonsterBitmap,
			// 600,200, 1000, 50, 25, 5, 1,"9 0 5 3 8 6", "Normal");
			// secondMonster= new NormalMonster(this, secondMonsterBitmap,
			// 600,400, 1000, 50, 60, 5, 1,"9 1 0 0 8 6", "Normal");
			// thirdMonster=new NormalMonster(this, secondMonsterBitmap,
			// 500,300, 100, 50, 600, 5, 1,"9 1 0 0 8 6", "Normal");

			// --
			// ALL THE MAGICs
			while (isOk) {
				if (!holder.getSurface().isValid()) {
					continue;
				}
				Canvas c = holder.lockCanvas();
				onDraw(c);
				holder.unlockCanvasAndPost(c);
			}
		}

		private void initializeMonsters(OurView ourView) {

			int monstersCount = Integer.parseInt(currentMap.getMonstersCount());

			if (monstersCount > 0) {
				monstersAlive++;
				com.example.gamedata.Monster monsterFirst = allMonsters
						.get(new Random().nextInt(allMonsters.size()));
				firstMonsterBitmap = BitmapFactory.decodeResource(
						getResources(),
						getResources().getIdentifier(monsterFirst.getIcon(),
								"drawable", getPackageName()));
				firstMonster = new NormalMonster(this, firstMonsterBitmap, 600,
						200, monsterFirst.getType(),
						Integer.parseInt(monsterFirst.getHealth()),
						Integer.parseInt(monsterFirst.getArmor()),
						Integer.parseInt(monsterFirst.getDamage()),
						playerLevel, monsterFirst.getCoords());
			}
			if (monstersCount > 1) {
				monstersAlive++;
				com.example.gamedata.Monster monsterSecond = allMonsters
						.get(new Random().nextInt(allMonsters.size()));
				secondMonsterBitmap = BitmapFactory.decodeResource(
						getResources(),
						getResources().getIdentifier(monsterSecond.getIcon(),
								"drawable", getPackageName()));
				secondMonster = new NormalMonster(this, secondMonsterBitmap,
						600, 400, monsterSecond.getType(),
						Integer.parseInt(monsterSecond.getHealth()),
						Integer.parseInt(monsterSecond.getArmor()),
						Integer.parseInt(monsterSecond.getDamage()),
						playerLevel, monsterSecond.getCoords());
			}
			if (monstersCount > 2) {
				monstersAlive++;
				com.example.gamedata.Monster monsterThird = allMonsters
						.get(new Random().nextInt(allMonsters.size()));
				thirdMonsterBitmap = BitmapFactory.decodeResource(
						getResources(),
						getResources().getIdentifier(monsterThird.getIcon(),
								"drawable", getPackageName()));
				thirdMonster = new NormalMonster(this, thirdMonsterBitmap, 500,
						300, monsterThird.getType(),
						Integer.parseInt(monsterThird.getHealth()),
						Integer.parseInt(monsterThird.getArmor()),
						Integer.parseInt(monsterThird.getDamage()),
						playerLevel, monsterThird.getCoords());
			}
			if (monstersCount > 3) {
				monstersAlive++;
				com.example.gamedata.Monster monsterFour = allMonsters
						.get(new Random().nextInt(allMonsters.size()));
				fourthMonsterBitmap = BitmapFactory.decodeResource(
						getResources(),
						getResources().getIdentifier(monsterFour.getIcon(),
								"drawable", getPackageName()));
				fourthMonster = new NormalMonster(this, fourthMonsterBitmap,
						500, 400, monsterFour.getType(),
						Integer.parseInt(monsterFour.getHealth()),
						Integer.parseInt(monsterFour.getArmor()),
						Integer.parseInt(monsterFour.getDamage()), playerLevel,
						monsterFour.getCoords());
			}

		}

		protected void onDraw(final Canvas canvas) {
			// BACKGROUND//
			Paint background = new Paint();
			background.setFilterBitmap(true);
			Rect dest = new Rect(0, 0, getWidth(), getHeight());
			canvas.drawBitmap(backgroundBitmap, null, dest, background);
			//

			// SKILLS
			/*
			 * canvas.drawBitmap(firstSkill, (screenWidth/2)-152,
			 * screenHeight-90, null); canvas.drawBitmap(secondSkill,
			 * (screenWidth/2)-54, screenHeight-90, null);
			 * canvas.drawBitmap(thirdSkill, (screenWidth/2)+44,
			 * screenHeight-90, null);
			 */

			if (firstSkillCd == 0) {
				canvas.drawBitmap(firstSkill, 0, (screenHeight / 2) - 152, null);
			} else {
				canvas.drawBitmap(firstSkillBitmapCd, 0,
						(screenHeight / 2) - 152, null);
				canvas.drawText("" + firstSkillCd, 27, screenHeight / 2 - 90,
						cooldowns);
			}
			if (secondSkillCd == 0) {
				canvas.drawBitmap(secondSkill, 0, (screenHeight / 2) - 54, null);
			} else {
				canvas.drawBitmap(secondSkillBitmapCd, 0,
						(screenHeight / 2) - 54, null);
				canvas.drawText("" + secondSkillCd, 27, screenHeight / 2 + 20,
						cooldowns);
			}
			if (thirdSkillCd == 0) {
				canvas.drawBitmap(thirdSkill, 0, (screenHeight / 2) + 44, null);
			} else {
				canvas.drawBitmap(thirdSkillBitmapCd, 0,
						(screenHeight / 2) + 44, null);
				canvas.drawText("" + thirdSkillCd, 27, screenHeight / 2 + 110,
						cooldowns);
			}
			if (thirdSpecialAttack) {
				canvas.drawBitmap(firstSkillBitmapCd, 0,
						(screenHeight / 2) - 152, null);
				canvas.drawBitmap(secondSkillBitmapCd, 0,
						(screenHeight / 2) - 54, null);
			}
			// --

			// HEALTH
			Typeface font = Typeface.createFromAsset(getAssets(), "8.TTF");
			Paint healthNumbers = new Paint();
			healthNumbers.setStyle(Paint.Style.FILL);
			healthNumbers.setAntiAlias(true);
			healthNumbers.setTextSize(25);
			healthNumbers.setTypeface(font);
			healthNumbers.setColor(Color.BLUE);

			/*
			 * canvas.drawText(""+player.getHealth(), player.getX()+30,
			 * player.getY(), healthNumbers);
			 * canvas.drawText(""+firstMonster.getHealth(),
			 * firstMonster.getX()+30, firstMonster.getY(), healthNumbers);
			 * if(checkIfMonsterExists(2)){
			 * canvas.drawText(""+secondMonster.getHealth(),
			 * secondMonster.getX()+30, secondMonster.getY(), healthNumbers);}
			 * if(checkIfMonsterExists(3)){
			 * canvas.drawText(""+thirdMonster.getHealth(),
			 * thirdMonster.getX()+30, thirdMonster.getY(), healthNumbers); }
			 * if(checkIfMonsterExists(4)){
			 * canvas.drawText(""+fourthMonster.getHealth(),
			 * fourthMonster.getX()+30, fourthMonster.getY(), healthNumbers); }
			 */

			canvas.drawRect(
					player.getX(),
					player.getY() - 20,
					player.getX()
							+ getCurrentHealth(player.getHealth(),
									player.getMaxHealth()), player.getY() - 10,
					healthBar);
			canvas.drawRect(player.getX(), player.getY() - 20,
					player.getX() + 100, player.getY() - 10, healthBarS);

			if (firstMonster.getHealth() > 0) {
				healthBar(canvas, firstMonster);
			}
			if (checkIfMonsterExists(2)) {
				if (secondMonster.getHealth() > 0) {
					healthBar(canvas, secondMonster);
				}
			}
			if (checkIfMonsterExists(3)) {
				if (thirdMonster.getHealth() > 0) {
					healthBar(canvas, thirdMonster);
				}
			}
			if (checkIfMonsterExists(4)) {
				if (fourthMonster.getHealth() > 0) {
					healthBar(canvas, fourthMonster);
				}
			}

			// canvas.drawText(""+getCurrentHealth(player.getHealth(),
			// player.getMaxHealth())+" : "+player.getHealth()+
			// " : "+player.getMaxHealth(), 300, 300, cooldowns);
			// DRAW COOLDOWNS
			/*
			 * if(secondSkillCd>0){ canvas.drawText(""+secondSkillCd, 27,
			 * screenHeight/2, cooldowns); canvas.drawRect(0, screenHeight/2-84,
			 * 80, screenHeight/2+40, cdFade); } if(firstSkillCd>0){
			 * canvas.drawText(""+firstSkillCd, 27, screenHeight/2-90,
			 * cooldowns); canvas.drawRect(0, screenHeight/2-152, 80,
			 * screenHeight/2-40, cdFade); } if(thirdSkillCd>0){
			 * canvas.drawText(""+thirdSkillCd, 27, screenHeight/2+110,
			 * cooldowns); canvas.drawRect(0, screenHeight/2+70, 80,
			 * screenHeight/2+40, cdFade); } if(thirdSpecialAttack){
			 * canvas.drawRect(0, screenHeight/2-152, 80, screenHeight/2-40,
			 * cdFade); canvas.drawRect(0, screenHeight/2-84, 80,
			 * screenHeight/2+40, cdFade); }
			 */
			// --
			// --
			checkAliveMonsters();
			checkGameStatus();

			player.onDraw(canvas);
			firstMonster.onDraw(canvas);
			if (checkIfMonsterExists(2)) {
				secondMonster.onDraw(canvas);
			}
			if (checkIfMonsterExists(3)) {
				thirdMonster.onDraw(canvas);
			}
			if (checkIfMonsterExists(4)) {
				fourthMonster.onDraw(canvas);
			}

			if (stun > 0) {
				if (dot) {
					dotDamage = player.doDotDamage();
					if (firstMonster.getHealth() > 0) {
						firstMonster.getDamage(dotDamage);
					}

					if (checkIfMonsterExists(2)) {
						if (secondMonster.getHealth() > 0) {
							secondMonster.getDamage(dotDamage);
						}
					}
					if (checkIfMonsterExists(3)) {
						if (thirdMonster.getHealth() > 0) {
							thirdMonster.getDamage(dotDamage);
						}
					}
					if (checkIfMonsterExists(4)) {
						if (fourthMonster.getHealth() > 0) {
							fourthMonster.getDamage(dotDamage);
						}
					}

					dot = false;

				}
			} else {
				player.secondSkillDone();
			}
			if (dotTickShowDamage) {
				dotTickDamage();
				if (firstMonster.getHealth() > 0) {
					canvas.drawText("-" + dotDamage, firstMonster.getX() + 60,
							firstMonster.getY() + 30, damageDotPaint);
				}

				if (checkIfMonsterExists(2)) {
					if (secondMonster.getHealth() > 0) {
						canvas.drawText("-" + dotDamage,
								secondMonster.getX() + 60,
								secondMonster.getY() + 50, damageDotPaint);
					}
				}
				if (checkIfMonsterExists(3)) {
					if (thirdMonster.getHealth() > 0) {
						canvas.drawText("-" + dotDamage,
								thirdMonster.getX() + 60,
								thirdMonster.getY() + 50, damageDotPaint);
					}
				}
				if (checkIfMonsterExists(4)) {
					if (fourthMonster.getHealth() > 0) {
						canvas.drawText("-" + dotDamage,
								fourthMonster.getX() + 60,
								fourthMonster.getY() + 50, damageDotPaint);
					}
				}

			}

			if (state == State.PLAYERR
					&& (playerFirstSkill || playerSecondSkill || playerThirdSkill)) {

				if (playerFirstSkill) {
					if (appPrefs.getCharacterClass().equals("barbarian")) {
						if (player.isAttacking()) {
							if (playerAttack) {
								yPlayer = player.getY();
								damage = player.doDamage();
								player.getDamage(damage);
								playerAttack = false;
							}
							canvas.drawText("+" + damage, player.getX() + 32,
									yPlayer--, damagePaint);
						} else {
							if (!playerAttack && !player.done()) {
								state = State.MONSTER;
								whichMonster = Monster.ONE;
								monsterAttack = true;
								firstMonster.setMonsterTurn(true);
								firstMonster.setPlayerDest(player.getX(),
										player.getY());
								playerFirstSkill = false;
								playerSecondSkill = false;
								playerThirdSkill = false;
							}
						}
					} else if (appPrefs.getCharacterClass().equals("wizard")
							|| appPrefs.getCharacterClass().equals("hunter")) {
						if (player.isAttacking()) {

							if (playerAttack) {
								damage = player.doDamage();
								if (firstMonster.getHealth() > 0) {
									firstMonster.getDamage(damage);
								}
								if (checkIfMonsterExists(2)) {
									if (secondMonster.getHealth() > 0) {
										secondMonster.getDamage(damage);
									}
								}
								if (checkIfMonsterExists(3)) {
									if (thirdMonster.getHealth() > 0) {
										thirdMonster.getDamage(damage);
									}
								}
								if (checkIfMonsterExists(4)) {
									if (fourthMonster.getHealth() > 0) {
										fourthMonster.getDamage(damage);
									}
								}

								playerAttack = false;
							}

							if (firstMonster.getHealth() > 0) {
								canvas.drawText("-" + damage,
										firstMonster.getX() + 32, yMonster--,
										damagePaint);
							}
							if (checkIfMonsterExists(2)) {
								if (secondMonster.getHealth() > 0) {
									canvas.drawText("-" + damage,
											secondMonster.getX() + 32,
											yMonsterSecond--, damagePaint);
								}
							}
							if (checkIfMonsterExists(3)) {
								if (thirdMonster.getHealth() > 0) {
									canvas.drawText("-" + damage,
											thirdMonster.getX() + 32,
											yMonsterThird--, damagePaint);
								}
							}
							if (checkIfMonsterExists(4)) {
								if (fourthMonster.getHealth() > 0) {
									canvas.drawText("-" + damage,
											fourthMonster.getX() + 32,
											yMonsterFourth--, damagePaint);
								}
							}

						} else {
							if (!playerAttack && !player.done()) {
								state = State.MONSTER;
								whichMonster = Monster.ONE;
								monsterAttack = true;
								firstMonster.setMonsterTurn(true);
								firstMonster.setPlayerDest(player.getX(),
										player.getY());

							}
						}
					}

				}

			} else if (state == State.PLAYERR) {
				if (player.isAttacking()) {
					if (playerAttack) {
						damage = player.doDamage();
						attackedMonster.getDamage(damage);
						playerAttack = false;
					}
					canvas.drawText("-" + damage, attackedMonster.getX() + 32,
							yMonster--, damagePaint);
				} else {
					if (!playerAttack && !player.done()) {

						if (stun == 0) {
							state = State.MONSTER;
							whichMonster = Monster.ONE;
							monsterAttack = true;
							firstMonster.setMonsterTurn(true);
							firstMonster.setPlayerDest(player.getX(),
									player.getY());
						} else {
							playerAttack = true;
						}
						playerFirstSkill = false;
						playerSecondSkill = false;
						playerThirdSkill = false;

					}
				}
			} else {
				if (whichMonster == Monster.ONE) {

					if (firstMonster.isAttacking()) {
						if (monsterAttack) {
							yPlayer = player.getY();
							damage = firstMonster.doDamage();
							player.getDamage(damage);
							monsterAttack = false;
						}
						canvas.drawText("-" + damage, player.getX() + 32,
								yPlayer--, damagePaint);
					} else {
						if (!firstMonster.getMonsterTurn()
								&& (!monsterAttack || !firstMonster
										.isMonsterAlive())) {
							whichMonster = Monster.TWO;
							playerAttack = true;
							monsterAttack = true;
							if (checkIfMonsterExists(2)) {
								secondMonster.setMonsterTurn(true);
								secondMonster.setPlayerDest(player.getX(),
										player.getY());
							}
						}
					}

				} else if (whichMonster == Monster.TWO) {
					if (secondMonster == null) {
						state = state.PLAYERR;
					} else {

						if (secondMonster.isAttacking()) {
							if (monsterAttack) {
								yPlayer = player.getY();
								damage = secondMonster.doDamage();
								player.getDamage(damage);
								monsterAttack = false;
							}
							canvas.drawText("-" + damage, player.getX() + 32,
									yPlayer--, damagePaint);
						} else {
							if (!secondMonster.getMonsterTurn()
									&& (!monsterAttack || !secondMonster
											.isMonsterAlive())) {
								whichMonster = Monster.THREE;
								monsterAttack = true;
								if (checkIfMonsterExists(3)) {
									thirdMonster.setMonsterTurn(true);
									thirdMonster.setPlayerDest(player.getX(),
											player.getY());
								}
							}
						}

					}
				} else if (whichMonster == Monster.THREE) {
					if (thirdMonster == null) {
						state = state.PLAYERR;
						whichMonster = Monster.ONE;
					} else {

						if (thirdMonster.isAttacking()) {
							if (monsterAttack) {
								yPlayer = player.getY();
								damage = thirdMonster.doDamage();
								player.getDamage(damage);
								monsterAttack = false;
							}
							canvas.drawText("-" + damage, player.getX() + 32,
									yPlayer--, damagePaint);
						} else {
							if (!thirdMonster.getMonsterTurn()
									&& (!monsterAttack || !thirdMonster
											.isMonsterAlive())) {
								whichMonster = Monster.FOUR;
								monsterAttack = true;
								if (checkIfMonsterExists(4)) {
									fourthMonster.setMonsterTurn(true);
									fourthMonster.setPlayerDest(player.getX(),
											player.getY());
								}
							}
						}

					}
				} else if (whichMonster == Monster.FOUR) {
					if (fourthMonster == null) {
						state = state.PLAYERR;
						whichMonster = Monster.ONE;
					} else {

						if (fourthMonster.isAttacking()) {
							if (monsterAttack) {
								yPlayer = player.getY();
								damage = fourthMonster.doDamage();
								player.getDamage(damage);
								monsterAttack = false;
							}
							canvas.drawText("-" + damage, player.getX() + 32,
									yPlayer--, damagePaint);
						} else {
							if (!fourthMonster.getMonsterTurn()
									&& (!monsterAttack || !fourthMonster
											.isMonsterAlive())) {
								whichMonster = Monster.ONE;
								state = state.PLAYERR;
								monsterAttack = true;

							}
						}

					}
				}
			}

		}

		private int getCurrentHealth(long hp, long maxhp) {
			return (int) ((hp * 100) / maxhp);
		}

		public void healthBar(Canvas canvas, Monsters monx) {
			canvas.drawRect(monx.getX(), monx.getY() - 20, monx.getX()
					+ getCurrentHealth(monx.getHealth(), monx.getMaxHealth()),
					monx.getY() - 10, healthBar);
			canvas.drawRect(monx.getX(), monx.getY() - 20, monx.getX() + 100,
					monx.getY() - 10, healthBarS);
		}

		public void pause() {
			isOk = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
			isOk = true;
			t = new Thread(this);
			t.start();
		}

		private void checkAliveMonsters() {
			if (firstMonster.getHealth() == 0 && firstM) {
				monstersAlive--;
				firstM = false;
			}
			if (checkIfMonsterExists(2)) {
				if (secondMonster.getHealth() == 0 && secondM) {
					monstersAlive--;
					secondM = false;
				}
			}
			if (checkIfMonsterExists(3)) {
				if (thirdMonster.getHealth() == 0 && thirdM) {
					monstersAlive--;
					thirdM = false;
				}
			}
			if (checkIfMonsterExists(4)) {
				if (fourthMonster.getHealth() == 0 && fourthM) {
					monstersAlive--;
					fourthM = false;
				}
			}
		}

		private void checkGameStatus() {
			if (monstersAlive == 0) {
				int mapGold = Integer.parseInt(currentMap.getBasegold());
				int mapExp = Integer.parseInt(currentMap.getBaseexperience());
				mapGold += mapGold * (0.6 * playerLevel);
				mapExp += mapExp * (0.5 * playerLevel);
				appPrefs.setWinLose("win", mapGold, mapExp);
				startActivity(new Intent(context, Tawern.class));

			} else if (player.getHealth() <= 0) {
				appPrefs.setWinLose("lose", 0, 0);
				startActivity(new Intent(context, Tawern.class));

			}
		}

	}

	private void dotTickDamage() {
		dotTick++;
		if (dotTick == 50) {
			dotTick = 0;
			dotTickShowDamage = false;
		}
	}

	private void manageSkills() {
		if (firstSkillCd > 0) {
			--firstSkillCd;
		}
		if (secondSkillCd > 0) {
			--secondSkillCd;
		}
		if (thirdSkillCd > 0) {
			--thirdSkillCd;
		}
		if (stun > 0) {
			--stun;
			if (appPrefs.getCharacterClass().equals("barbarian")
					|| appPrefs.getCharacterClass().equals("wizard")) {
				dot = true;
				dotTickShowDamage = true;
			}
		}
	}

	private boolean checkIfMonsterExists(int monsterID) {
		if (monsterID == 2) {
			if (secondMonster == null) {
				return false;
			} else {
				return true;
			}
		} else if (monsterID == 3) {
			if (thirdMonster == null) {
				return false;
			} else {
				return true;
			}
		} else {
			if (fourthMonster == null) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (me.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// PLAYER
			if (state == State.PLAYERR) {

				// MONSTERONE
				if ((me.getX() >= 0 && me.getX() <= 108)
						&& (me.getY() >= (screenHeight / 2) - 152 && me.getY() <= (screenHeight / 2) - 48)) {

					if (firstSkillCd == 0 && !thirdSpecialAttack) {
						player.setMonsterDest(player.getX(), player.getY());
						player.setAttack(true);
						attackedMonster = firstMonster;
						yMonster = attackedMonster.getY();
						if (checkIfMonsterExists(2)) {
							yMonsterSecond = secondMonster.getY();
						}
						if (checkIfMonsterExists(3)) {
							yMonsterThird = thirdMonster.getY();
						}
						if (checkIfMonsterExists(4)) {
							yMonsterFourth = fourthMonster.getY();
						}
						firstSkillCd = 3;
						playerFirstSkill = true;
						player.specialAttack("first");
						manageSkills();
					}

				}

				if ((me.getX() >= 0 && me.getX() <= 108)
						&& (me.getY() >= (screenHeight / 2) - 48 && me.getY() <= (screenHeight / 2) + 48)) {
					if (secondSkillCd == 0 && !thirdSpecialAttack) {
						if (appPrefs.getCharacterClass().equals("barbarian")) {
							stun = 3;
							dot = true;
							dotTickShowDamage = true;
						}
						if (appPrefs.getCharacterClass().equals("wizard")) {
							stun = 2;
							dot = true;
							dotTickShowDamage = true;
						}
						if (appPrefs.getCharacterClass().equals("hunter")) {
							stun = 3;
						}
						player.startAnimateSkill();
						player.specialAttack("second");
						secondSkillCd = 5;

					}

				}

				if ((me.getX() >= 0 && me.getX() <= 108)
						&& (me.getY() >= (screenHeight / 2) + 48 && me.getY() <= (screenHeight / 2) + 156)) {
					if (thirdSkillCd == 0) {
						player.specialAttack("third");
						thirdSkillCd = 8;
						thirdSpecialAttack = true;
					}
				}

				if ((me.getX() >= firstMonster.getX() && me.getX() <= firstMonster
						.getX() + 64)
						&& (me.getY() >= firstMonster.getY() && me.getY() <= firstMonster
								.getY() + 128)) {
					if (firstMonster.getHealth() > 0) {
						player.setMonsterDest(firstMonster.getX(),
								firstMonster.getY());
						player.setAttack(true);
						attackedMonster = firstMonster;
						yMonster = attackedMonster.getY();
						if (thirdSpecialAttack) {
							thirdSpecialAttack = false;
						}
						manageSkills();
					}
				}

				if (checkIfMonsterExists(2)) {
					if ((me.getX() >= secondMonster.getX() && me.getX() <= secondMonster
							.getX() + 64)
							&& (me.getY() >= secondMonster.getY() && me.getY() <= secondMonster
									.getY() + 128)) {
						if (secondMonster.getHealth() > 0) {
							player.setMonsterDest(secondMonster.getX(),
									secondMonster.getY());
							player.setAttack(true);
							attackedMonster = secondMonster;
							yMonster = attackedMonster.getY();
							if (thirdSpecialAttack) {
								thirdSpecialAttack = false;
							}
							manageSkills();
						}
					}
				}

				if (checkIfMonsterExists(3)) {
					if ((me.getX() >= thirdMonster.getX() && me.getX() <= thirdMonster
							.getX() + 64)
							&& (me.getY() >= thirdMonster.getY() && me.getY() <= thirdMonster
									.getY() + 128)) {
						if (thirdMonster.getHealth() > 0) {
							player.setMonsterDest(thirdMonster.getX(),
									thirdMonster.getY());
							player.setAttack(true);
							attackedMonster = thirdMonster;
							yMonster = attackedMonster.getY();
							if (thirdSpecialAttack) {
								thirdSpecialAttack = false;
							}
							manageSkills();
						}
					}
				}

				if (checkIfMonsterExists(4)) {
					if ((me.getX() >= fourthMonster.getX() && me.getX() <= fourthMonster
							.getX() + 64)
							&& (me.getY() >= fourthMonster.getY() && me.getY() <= fourthMonster
									.getY() + 128)) {
						if (fourthMonster.getHealth() > 0) {
							player.setMonsterDest(fourthMonster.getX(),
									fourthMonster.getY());
							player.setAttack(true);
							attackedMonster = fourthMonster;
							yMonster = attackedMonster.getY();
							if (thirdSpecialAttack) {
								thirdSpecialAttack = false;
							}
							manageSkills();
						}
					}
				}
			}

			//

			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		}
		return true;
	}

}

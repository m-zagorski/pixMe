package com.example.pixme;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamedata.GameSharedPreferences;

public class Sanctuary extends Activity {
	GameSharedPreferences appPrefs;
	ImageButton firstSkill = null;
	ImageButton secondSkill = null;
	ImageButton thirdSkill = null;
	ImageButton backButton = null;
	ImageButton chamberGuardian = null;

	MediaPlayer sanctuaryMusic;
	MediaPlayer chamberGuardianSound;
	MediaPlayer doorsClosed;

	final Context context = this;

	Typeface buttonFont = null;
	Typeface textFont = null;

	final int upgradeCosts[] = { 0, 200, 300, 400, 500, 1000, 3000, 5000, 7000,
			10000 };
	final String skillNames[][] = { { "Regenerate", "Stomp", "Crush" },
			{ "Molten rain", "Fire Nova", "Pyroblast" },
			{ "Multishot", "Poison Arrow", "Accurate Shot" } };

	final String skillDescription[][] = {
			{
					"Regenerates your health \ndepending on level.\nCooldown: 3 turns.",
					"Stuns all monsters \nfor 3 turns\nand deals damage.\nCooldown: 5 turns.",
					"Powerfull crushing attack.\nCooldown: 8 turns." },
			{
					"Deals damage to all enemies \non the field.\nCooldown: 3 turns.",
					"Stuns all enemies \nfor 2 turns\nand deals damage.\nCooldown: 5 turns.",
					"Powerfull fire attack \nthat crushes enemies.\nCooldown: 8 turns." },
			{
					"Shots arrows that \ndeal damage to\nall enemies.\nCooldown: 3 turns.",
					"Stuns all enemies for 2 turns.\nCooldown: 5 turns.",
					"Accurate and powerfull shot.\nCooldown: 8 turns." } };

	final int skillBaseDamage[][] = { { 25, 15, 122 }, { 50, 30, 155 },
			{ 40, 20, 133 } };

	final int skillNextDamage[][] = { { 2, 2, 3 }, { 5, 3, 4 }, { 4, 2, 3 } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sanctuary);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		appPrefs = new GameSharedPreferences(context);

		sanctuaryMusic = MediaPlayer.create(this, R.raw.haunted_west);
		chamberGuardianSound = MediaPlayer.create(this, (getResources()
				.getIdentifier("chamberguy" + new Random().nextInt(1), "raw",
						getPackageName())));
		doorsClosed = MediaPlayer.create(
				this,
				(getResources().getIdentifier(appPrefs.getCharacterClass()
						+ "closeddoors", "raw", getPackageName())));

		firstSkill = (ImageButton) findViewById(R.id.firstSkill);
		secondSkill = (ImageButton) findViewById(R.id.secondSkill);
		thirdSkill = (ImageButton) findViewById(R.id.thirdSkill);
		backButton = (ImageButton) findViewById(R.id.backButton);
		chamberGuardian = (ImageButton) findViewById(R.id.chamberGuardian);

		firstSkill.setBackgroundResource(R.drawable.empty_shop);
		secondSkill.setBackgroundResource(R.drawable.empty_shop);
		thirdSkill.setBackgroundResource(R.drawable.empty_shop);
		backButton.setBackgroundResource(R.drawable.empty_shop);
		chamberGuardian.setBackgroundResource(R.drawable.empty_shop);

		buttonFont = Typeface.createFromAsset(getAssets(), "bloodthirsty.ttf");
		textFont = Typeface.createFromAsset(getAssets(), "kree.ttf");

		if (Integer.parseInt(appPrefs.characterLevel()) == 6) {
			firstSkill.setImageResource(R.drawable.chamber_closed);
			secondSkill.setImageResource(R.drawable.chamber_closed);
			thirdSkill.setImageResource(R.drawable.chamber_closed);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) == 7) {
			firstSkill.setImageResource(R.drawable.chamber_third);
			secondSkill.setImageResource(R.drawable.chamber_closed);
			thirdSkill.setImageResource(R.drawable.chamber_closed);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) == 8) {
			firstSkill.setImageResource(R.drawable.chamber_middle);
			secondSkill.setImageResource(R.drawable.chamber_third);
			thirdSkill.setImageResource(R.drawable.chamber_closed);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) == 9) {
			firstSkill.setImageResource(R.drawable.chamber_open);
			secondSkill.setImageResource(R.drawable.chamber_third);
			thirdSkill.setImageResource(R.drawable.chamber_third);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) > 9
				&& Integer.parseInt(appPrefs.characterLevel()) < 12) {
			secondSkill.setImageResource(R.drawable.chamber_middle);
			thirdSkill.setImageResource(R.drawable.chamber_third);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) > 12
				&& Integer.parseInt(appPrefs.characterLevel()) < 15) {
			thirdSkill.setImageResource(R.drawable.chamber_middle);
		}

		if (Integer.parseInt(appPrefs.characterLevel()) > 9) {
			firstSkill.setImageResource(R.drawable.chamber_open);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) >= 12) {
			secondSkill.setImageResource(R.drawable.chamber_open);
		}
		if (Integer.parseInt(appPrefs.characterLevel()) >= 15) {
			thirdSkill.setImageResource(R.drawable.chamber_open);
		}

		if (appPrefs.getMusicStatus().equals("yes")) {
			sanctuaryMusic.start();
			chamberGuardianSound.start();
			sanctuaryMusic.setLooping(true);
		}
		// firstSkill.setImageResource(getResources().getIdentifier("empty_shop",
		// "drawable", getPackageName()));
		// secondSkill.setImageResource(getResources().getIdentifier("empty_shop",
		// "drawable", getPackageName()));
		// thirdSkill.setImageResource(getResources().getIdentifier("empty_shop",
		// "drawable", getPackageName()));

		firstSkill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Integer.parseInt(appPrefs.characterLevel()) < 9) {
					doorsClosed.start();
					Toast.makeText(Sanctuary.this,
							"Level 9 required to enter this chamber.",
							Toast.LENGTH_LONG).show();
				} else {
					createDialog(0);
				}
			}
		});
		secondSkill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Integer.parseInt(appPrefs.characterLevel()) < 12) {
					doorsClosed.start();
					Toast.makeText(Sanctuary.this,
							"Level 12 required to enter this chamber.",
							Toast.LENGTH_LONG).show();
				} else {
					createDialog(1);
				}
			}
		});
		thirdSkill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Integer.parseInt(appPrefs.characterLevel()) < 15) {
					doorsClosed.start();
					Toast.makeText(Sanctuary.this,
							"Level 15 required to enter this chamber.",
							Toast.LENGTH_LONG).show();
				} else {
					createDialog(2);
				}
			}
		});
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(Sanctuary.this, Tawern.class));
			}
		});
		chamberGuardian.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chamberGuardianSound.start();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sanctuary, menu);
		return true;
	}

	private void createDialog(final int skillID) {

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.sanctuary_dialog);
		int row = 0;
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
		if (appPrefs.getCharacterClass().equals("barbarian")) {
			row = 0;
		} else if (appPrefs.getCharacterClass().equals("wizard")) {
			row = 1;
		} else {
			row = 2;
		}

		TextView skillDescription = (TextView) dialog
				.findViewById(R.id.skillDescription);
		TextView title = (TextView) dialog.findViewById(R.id.skillName);
		TextView skillNextLevel = (TextView) dialog
				.findViewById(R.id.skillNextLevel);
		ImageView skillIcon = (ImageView) dialog.findViewById(R.id.skillIcon);
		title.setText(skillNames[row][skillID]);
		skillDescription.setText("Current skill level: "
				+ appPrefs.skillLevel(skillID) + "\n"
				+ this.skillDescription[row][skillID]);
		skillNextLevel.setText(createDesc(row, skillID) + "\nUpgrade cost: "
				+ upgradeCosts[appPrefs.skillLevel(skillID)]);
		// skillNextLevel.setText(""+upgradeCosts[appPrefs.skillLevel(skillID)]);

		// ImageView image = (ImageView) dialog.findViewById(R.id.image);
		// image.setImageResource(R.drawable.ic_launcher);

		Button dialogButtonBuy = (Button) dialog.findViewById(R.id.upgrade);
		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.cancel);

		dialogButtonBuy.setBackgroundResource(R.drawable.empty_shop);
		dialogButtonCancel.setBackgroundResource(R.drawable.empty_shop);
		dialogButtonBuy.setTypeface(buttonFont);
		dialogButtonCancel.setTypeface(buttonFont);
		title.setTypeface(textFont);
		skillDescription.setTypeface(textFont);
		skillNextLevel.setTypeface(textFont);

		if (skillID == 0) {
			skillIcon.setImageResource(getResources().getIdentifier(
					appPrefs.getCharacterClass() + "skillfirst", "drawable",
					getPackageName()));
		} else if (skillID == 1) {
			skillIcon.setImageResource(getResources().getIdentifier(
					appPrefs.getCharacterClass() + "skillsecond", "drawable",
					getPackageName()));
		} else {
			skillIcon.setImageResource(getResources().getIdentifier(
					appPrefs.getCharacterClass() + "skillthird", "drawable",
					getPackageName()));
		}

		dialogButtonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialogButtonBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (appPrefs.skillLevel(skillID) == 10) {
					Toast.makeText(Sanctuary.this, "Max skill level.",
							Toast.LENGTH_LONG).show();
				} else if (upgradeCosts[appPrefs.skillLevel(skillID)] > Integer
						.parseInt(appPrefs.characterMoney())) {
					Toast.makeText(Sanctuary.this, "Not enough money.",
							Toast.LENGTH_LONG).show();
				} else {
					appPrefs.updateCharacterMoney(upgradeCosts[appPrefs
							.skillLevel(skillID)]);
					if (ifUpgraded(appPrefs.skillLevel(skillID))) {
						appPrefs.updateSkill(skillID);
						Toast.makeText(Sanctuary.this, "Upgrade succedded.",
								Toast.LENGTH_LONG).show();
					} else {
						appPrefs.decreaseSkill(skillID);
						Toast.makeText(Sanctuary.this, "Upgrade failed.",
								Toast.LENGTH_LONG).show();
					}

				}
				dialog.dismiss();

			}
		});

		dialog.show();
	}

	private String createDesc(int row, int skillID) {
		return "Base damage: " + skillBaseDamage[row][skillID]
				+ "%\nCurrent bonus: " + skillNextDamage[row][skillID]
				* appPrefs.skillLevel(skillID) + "%\nNext level gives: "
				+ skillNextDamage[row][skillID] + "%";
	}

	private boolean ifUpgraded(int skillID) {
		if (skillID == 1) {
			if (new Random().nextInt(9) < 8) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 2) {
			if (new Random().nextInt(9) < 7) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 3) {
			if (new Random().nextInt(9) < 6) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 4) {
			if (new Random().nextInt(9) < 5) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 5) {
			if (new Random().nextInt(9) < 5) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 6) {
			if (new Random().nextInt(9) < 4) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 7) {
			if (new Random().nextInt(9) < 2) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 8) {
			if (new Random().nextInt(9) < 2) {
				return true;
			} else {
				return false;
			}
		} else if (skillID == 9) {
			if (new Random().nextInt(9) < 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		sanctuaryMusic.release();
		chamberGuardianSound.release();
		doorsClosed.release();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

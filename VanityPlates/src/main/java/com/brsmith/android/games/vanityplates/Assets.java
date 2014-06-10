package com.brsmith.android.games.vanityplates;

import android.graphics.Typeface;


import com.brsmith.android.games.framework.gl.Font;
import com.brsmith.android.games.framework.gl.Texture;
import com.brsmith.android.games.framework.gl.TextureRegion;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.interfaces.IMusic;
import com.brsmith.android.games.framework.interfaces.ISound;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture characters;
    public static Font font;

    public static Texture bubbleCar;
    public static TextureRegion bubbleCarFrontRegion;
    public static TextureRegion bubbleCarBackRegion;

    public static Texture plates;
    public static TextureRegion plateRegion;

    public static Typeface licenseFont;

    public static IMusic music;
    public static ISound honkSound;

    public static void load(GLGame game)
    {
        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

        characters = new Texture(game, "LicensePlateFont.png");
        font = new Font(characters, 0, 0, 16, 16, 32);

        bubbleCar = new Texture(game, "BubbleCar2.png");
        bubbleCarFrontRegion = new TextureRegion(bubbleCar, 0, 0, 221, 272);
        bubbleCarBackRegion = new TextureRegion(bubbleCar, 221, 0, 442, 272);

        plates = new Texture(game, "licenseplate.png");
        plateRegion = new TextureRegion(plates, 0, 0, 240, 123);

        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();

        honkSound = game.getAudio().newSound("honk.ogg");
        licenseFont = game.getGLGraphics().newTypeface("license_plate.ttf");

    }

    public static void reload()
    {
        background.reload();
        characters.reload();
        if(Settings.soundEnabled)
            music.play();
    }

    public static void playSound(ISound sound)
    {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}

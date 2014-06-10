package com.brsmith.android.games.vanityplates.Actions;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NewScreenAction <T extends Screen> implements IAction {
    IGame game;
    Class<T> clazz;

    public NewScreenAction(IGame game, Class<T> clazz)
    {
        this.game = game;
        this.clazz = clazz;
    }

    public void run()
    {
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor(IGame.class);
            ctor.setAccessible(true);
            Screen newScreen = (Screen) ctor.newInstance(game);
            game.setScreen(newScreen);
        } catch (IllegalArgumentException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }
    }
}

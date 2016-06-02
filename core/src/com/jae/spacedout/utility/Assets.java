package com.jae.spacedout.utility;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets
{
    private AssetManager manager;
    public AssetManager getManager()
    {
        return this.manager;
    }

    public Assets()
    {
        this.manager = new AssetManager();
        this.loadAssets();
    }

    private void loadAssets()
    {
        //region ship textures

        this.manager.load(Settings.DEBUG_SHIP, Texture.class);

        //endregion ship textures

        //region star textures

        this.manager.load(Settings.STAR_1, Texture.class);
        this.manager.load(Settings.STAR_2, Texture.class);
        this.manager.load(Settings.STAR_3, Texture.class);
        this.manager.load(Settings.STAR_4, Texture.class);
        this.manager.load(Settings.STAR_5, Texture.class);
        this.manager.load(Settings.STAR_6, Texture.class);

        //endregion star textures
    }

    public TextureRegion getRegion(String filepath)
    {
        return new TextureRegion(this.manager.get(filepath, Texture.class));
    }

    public void unloadAsset(String asset)
    {
        this.manager.unload(asset);
    }

    public boolean update()
    {
        return this.manager.update();
    }
}

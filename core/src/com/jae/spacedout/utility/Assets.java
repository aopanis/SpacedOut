package com.jae.spacedout.utility;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jae.spacedout.game.stats.ShipStatHolder;
import com.jae.spacedout.game.stats.ShipStats;
import com.jae.spacedout.game.stats.WeaponStatHolder;
import com.jae.spacedout.game.stats.WeaponStats;

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

        for(ShipStatHolder holder : ShipStats.ships)
        {
            this.manager.load(holder.texPath, Texture.class);
        }

        //endregion ship textures

        //region bullet textures

        for(WeaponStatHolder holder : WeaponStats.weapons)
        {
            this.manager.load(holder.bulletTexPath, Texture.class);
        }

        //endregion bullet textures

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

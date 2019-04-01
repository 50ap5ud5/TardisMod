package net.tardis.mod.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

/**
 * Created by Sub
 * on 20/09/2018.
 */
public class MovingSoundBase extends MovingSound {
	
	private final Object entity;
	private final Supplier<Boolean> stopCondition;
	private boolean donePlaying = false;
	
	public MovingSoundBase(Object object, SoundEvent soundIn, SoundCategory categoryIn, boolean repeat, Supplier<Boolean> stopCondition, float volumeSfx) {
		super(soundIn, categoryIn);
		this.entity = object;
		this.stopCondition = stopCondition;
		this.repeat = repeat;
		volume = volumeSfx;
	}
	
	@Override
	public void update() {
		if (entity instanceof Entity) {
			Entity entityObject = (Entity) entity;
			if (stopCondition.get() || entityObject.isDead) {
				setDonePlaying();
			}
			
			if (entityObject instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityObject;
				if (player.onGround) {
					volume = 0;
				} else {
					volume = 1;
				}
			}
			
			
			super.xPosF = (float) entityObject.posX;
			super.yPosF = (float) entityObject.posY;
			super.zPosF = (float) entityObject.posZ;
		}
		
		if (entity instanceof TileEntity) {
			TileEntity tileObject = (TileEntity) entity;
			BlockPos pos = tileObject.getPos();
			super.xPosF = (float) pos.getX();
			super.yPosF = (float) pos.getY();
			super.zPosF = (float) pos.getZ();
		}
		
	}
	
	
	public void setDonePlaying() {
		this.repeat = false;
		this.donePlaying = true;
		this.repeatDelay = 0;
	}
	
	@Override
	public boolean canRepeat() {
		return this.repeat;
	}
	
	@Override
	public float getVolume() {
		return this.volume;
	}
	
	@Override
	public float getPitch() {
		return this.pitch;
	}
	
	@Override
	public boolean isDonePlaying() {
		return donePlaying;
	}
	
	
	@Override
	public int getRepeatDelay() {
		return this.repeatDelay;
	}
	
	@Override
	public AttenuationType getAttenuationType() {
		return AttenuationType.LINEAR;
	}
}
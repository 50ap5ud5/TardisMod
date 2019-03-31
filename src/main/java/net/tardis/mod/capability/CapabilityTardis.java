package net.tardis.mod.capability;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.Tardis;
import net.tardis.mod.capability.TardisCapStorage.TardisCapProvider;
import net.tardis.mod.common.blocks.BlockTardisTop;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.sounds.TSounds;
import net.tardis.mod.common.systems.TardisSystems;
import net.tardis.mod.common.tileentity.TileEntityDoor;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.network.NetworkHandler;
import net.tardis.mod.network.packets.MessageSyncCap;
import net.tardis.mod.util.common.helpers.PlayerHelper;
import net.tardis.mod.util.common.helpers.TardisHelper;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class CapabilityTardis implements ITardisCap {
	
	private EntityPlayer player;
	private BlockPos pos = BlockPos.ORIGIN;
	private boolean isInFlight;
	private int exterior = Block.getStateId(TBlocks.tardis_top.getDefaultState());
	private boolean hasFuel = true;
	private int timeOnGround = 0;
	private boolean isOpen = false;
	
	public CapabilityTardis() {
	}
	
	public CapabilityTardis(EntityPlayer player) {
		this.player = player;
	}
	
	@Override
	public void setTardis(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public BlockPos getTardis() {
		return pos;
	}
	
	@Override
	public boolean isInFlight() {
		return isInFlight;
	}
	
	@Override
	public void setInFlight(boolean inFlight) {
		isInFlight = inFlight;
	}
	
	private static final Predicate<Entity> ENTITY = p_apply_1_ -> p_apply_1_ instanceof EntityLiving;
	
	@Override
	public void update() {
		
		//Set the players Tardis position when they are in the Tardis Dimension
		if (getTardis().equals(BlockPos.ORIGIN) && player.dimension == TDimensions.TARDIS_ID) {
			setTardis(TardisHelper.getTardisForPosition(player.getPosition()));
		} else {
			
			if (!getTardis().equals(BlockPos.ORIGIN) && player.dimension != TDimensions.TARDIS_ID && !isInFlight) {
				setTardis(BlockPos.ORIGIN);
			}
		}
		
		if (player.dimension != TDimensions.TARDIS_ID) {
			if (!getTardis().equals(BlockPos.ORIGIN)) {
				if (isInFlight()) {
					
					if (isOpen()) {
						//	for (Entity entity : player.world.getEntitiesInAABBexcluding(player, player.getCollisionBoundingBox().grow(4), ENTITY)) {
						//		if (entity != null && !entity.isDead) {
						//			ITardisCap cap = CapabilityTardis.get(player);
						//			TileEntityTardis console = TardisHelper.getConsole(cap.getTardis());
						//			if (console != null) {
						//				console.enterTARDIS(entity);
						//			}
						//		}
						//	}
					}
					
					
					if (player.collidedHorizontally) {
						for (TardisSystems.BaseSystem s : Objects.requireNonNull(TardisHelper.getConsole(getTardis())).systems) {
							s.damage();
						}
					}
					
					if (player.world.getBlockState(player.getPosition().down()).getBlock() != Blocks.AIR) {
						timeOnGround++;
					} else {
						timeOnGround = 0;
					}
					
					if (timeOnGround >= 50 && player.isSneaking()) {
						endFlight(player);
					}
					
					if (hasFuel) {
						player.capabilities.allowFlying = true;
						
						if(player.world.isRemote){
							setSpeeds(player, true);
						}
						
						player.velocityChanged = true;
						if (player.ticksExisted % 40 == 0) {
							if (!player.onGround) {
								player.world.playSound(null, player.getPosition(), TSounds.loop, SoundCategory.BLOCKS, 0.5F, 1F);
							}
						}
					} else {
						if (player.ticksExisted % 100 == 0) {
							player.world.playSound(null, player.getPosition(), TSounds.cloister_bell, SoundCategory.BLOCKS, 0.5F, 1F);
						}
						player.capabilities.isFlying = false;
						player.capabilities.allowFlying = false;
						player.velocityChanged = true;
					}
				} else {
					endFlight(player);
					setTardis(BlockPos.ORIGIN);
				}
			}
		} else {
			if (isInFlight()) {
				endFlight(player);
			}
		}
		
		
		//Return the player to the interior when they attempt to move away from it
		if (player.dimension == TDimensions.TARDIS_ID && !getTardis().equals(BlockPos.ORIGIN)) {
			if (player.getPosition().distanceSq(getTardis()) > 16384) {
				player.setPositionAndUpdate(getTardis().getX(), getTardis().getY(), getTardis().getZ());
			}
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void setSpeeds(EntityPlayer player, boolean reset){
		if(reset){
			player.capabilities.setFlySpeed(0.05F);
		} else {
			if (!player.isSprinting()) {
				player.capabilities.setFlySpeed(5);
			} else {
				player.capabilities.setFlySpeed(11);
			}
		}
	}
	
	@Override
	public void setExterior(IBlockState exterior) {
		this.exterior = Block.getStateId(exterior);
	}
	
	@Override
	public IBlockState getExterior() {
		return Block.getStateById(exterior);
	}
	
	@Override
	public void sync() {
		NetworkHandler.NETWORK.sendToAll(new MessageSyncCap(player, serializeNBT()));
	}
	
	@Override
	public void setHasFuel(boolean b) {
		hasFuel = b;
	}
	
	@Override
	public boolean hasFuel() {
		return hasFuel;
	}
	
	@Override
	public int timeOnGround() {
		return timeOnGround;
	}
	
	@Override
	public void setTimeOnGround(int time) {
		timeOnGround = time;
	}
	
	@Override
	public void setDoorsOpen(boolean open) {
		isOpen = open;
	}
	
	@Override
	public boolean isOpen() {
		return isOpen;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("exterior", exterior);
		nbt.setBoolean("inFlight", isInFlight);
		nbt.setBoolean("hasFuel", hasFuel());
		nbt.setInteger("groundTime", timeOnGround);
		nbt.setBoolean("open", isOpen);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		exterior = nbt.getInteger("exterior");
		isInFlight = nbt.getBoolean("inFlight");
		hasFuel = nbt.getBoolean("hasFuel");
		timeOnGround = nbt.getInteger("groundTime");
		isOpen = nbt.getBoolean("open");
	}
	
	
	//===== CAPABILITY EVENTS =====
	@Mod.EventBusSubscriber(modid = Tardis.MODID)
	public static class Events {
		
		@SubscribeEvent
		public static void attach(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof EntityPlayer)
				event.addCapability(new ResourceLocation(Tardis.MODID, "tardis_cap"), new TardisCapProvider((EntityPlayer) event.getObject()));
		}
		
		@SubscribeEvent
		public static void update(LivingUpdateEvent event) {
			CapabilityTardis cap = event.getEntityLiving().getCapability(TardisCapStorage.CAP, null);
			if (cap != null)
				cap.update();
		}
		
		@SubscribeEvent
		public static void onPlayerClone(PlayerEvent.Clone event) {
			Capability.IStorage storage = TardisCapStorage.CAP.getStorage();
			
			ITardisCap oldCap = get(event.getOriginal());
			ITardisCap newCap = get(event.getEntityPlayer());
			
			NBTTagCompound nbt = (NBTTagCompound) storage.writeNBT(TardisCapStorage.CAP, oldCap, null);
			storage.readNBT(TardisCapStorage.CAP, newCap, null, nbt);
			get(event.getEntityPlayer()).sync();
		}
		
		@SubscribeEvent
		public static void playerTracking(PlayerEvent.StartTracking event) {
			get(event.getEntityPlayer()).sync();
		}
		
	}
	
	//===== HELPERS =====
	
	@Nonnull
	public static ITardisCap get(EntityPlayer player) {
		if (player.hasCapability(TardisCapStorage.CAP, null)) {
			return player.getCapability(TardisCapStorage.CAP, null);
		}
		throw new IllegalStateException("Missing Tardis capability: " + player + ", please report this to the issue tracker");
	}
	
	private static final UUID SPEED_ID = UUID.fromString("a22a9515-90d7-479d-9153-07268f2a1714");
	private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_ID, "SANIC_FAST", 0.95, 1);
	
	public static void setupFlight(EntityPlayer player) {
		if (player.world.isRemote) return;
		ITardisCap cap = CapabilityTardis.get(player);
		cap.setTimeOnGround(0);
		TileEntityTardis console = TardisHelper.getConsole(cap.getTardis());
		if (console != null && !console.hasPilot() && console.fuel > 0) {
			console.setFlightPilot(player);
			console.transferPlayer(player, false);
			cap.setInFlight(true);
			cap.setExterior(console.getTopBlock());
			cap.setHasFuel(true);
			player.capabilities.allowFlying = true;
			player.capabilities.isFlying = true;
			player.capabilities.allowEdit = false;
			player.capabilities.disableDamage = true;
			player.motionY += 8;
			player.velocityChanged = true;
			player.eyeHeight = 2;
			if(player.world.isRemote){
				setSpeeds(player, true);
			}
			player.sendPlayerAbilities();
			player.setEntityInvulnerable(true);
			if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER)) {
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MODIFIER);
			}
			cap.sync();
			WorldServer world = DimensionManager.getWorld(console.dimension);
			world.setBlockState(console.getLocation(), Blocks.AIR.getDefaultState());
			world.setBlockState(console.getLocation().up(), Blocks.AIR.getDefaultState());
		} else {
			PlayerHelper.sendMessage(player, new TextComponentTranslation("tardis.message.has_pilot"), true);
		}
	}
	
	public static void endFlight(EntityPlayer player) {
		if (player.world.isRemote) return;
		ITardisCap cap = CapabilityTardis.get(player);
		TileEntityTardis console = TardisHelper.getConsole(cap.getTardis());
		BlockPos bPos = player.getPosition();
		if (console != null) {
			cap.setInFlight(false);
			player.capabilities.allowFlying = player.isCreative();
			player.capabilities.isFlying = player.isCreative();
			player.capabilities.allowEdit = true;
			player.capabilities.disableDamage = false;
			player.velocityChanged = true;
			player.setEntityInvulnerable(false);
			if(player.world.isRemote){
				setSpeeds(player, true);
			}
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER)) {
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER);
			}
			cap.sync();
			player.eyeHeight = player.getDefaultEyeHeight();
			player.sendPlayerAbilities();
			WorldServer exteriorWorld = DimensionManager.getWorld(console.dimension);
			exteriorWorld.setBlockState(bPos, TBlocks.tardis.getDefaultState());
			exteriorWorld.setBlockState(bPos.up(), console.getTopBlock().withProperty(BlockTardisTop.FACING, player.getHorizontalFacing()));
			
			if (exteriorWorld.getTileEntity(bPos.up()) instanceof TileEntityDoor) {
				TileEntityDoor door = (TileEntityDoor) exteriorWorld.getTileEntity(bPos.up());
				if (door != null) {
					door.setConsolePos(console.getLocation());
					door.setRemat();
				}
			}
			
			console.setLocation(bPos);
			console.enterTARDIS(player);
			console.setFlightPilot(null);
			cap.setTimeOnGround(0);
		}
	}
	
}

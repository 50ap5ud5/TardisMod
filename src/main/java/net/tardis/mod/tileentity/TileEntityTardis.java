package net.tardis.mod.tileentity;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.Constants;
import net.tardis.api.controls.SpaceTimeCoord;
import net.tardis.mod.Tardis;
import net.tardis.mod.blocks.TBlocks;
import net.tardis.mod.controls.ControlDimChange;
import net.tardis.mod.controls.ControlDoor;
import net.tardis.mod.controls.ControlFlight;
import net.tardis.mod.controls.ControlFuel;
import net.tardis.mod.controls.ControlLaunch;
import net.tardis.mod.controls.ControlRandom;
import net.tardis.mod.controls.ControlSTCButton;
import net.tardis.mod.controls.ControlSTCLoad;
import net.tardis.mod.controls.ControlScanner;
import net.tardis.mod.controls.ControlScreen;
import net.tardis.mod.controls.ControlX;
import net.tardis.mod.controls.ControlY;
import net.tardis.mod.controls.ControlZ;
import net.tardis.mod.controls.EntityControl;
import net.tardis.mod.dimensions.TDimensions;
import net.tardis.mod.helpers.Helper;
import net.tardis.mod.sounds.TSounds;

public class TileEntityTardis extends TileEntity implements ITickable{
	
	private int ticksToTravel=0;
	private int ticks=0;
	private BlockPos tardisLocation=BlockPos.ORIGIN;
	private BlockPos tardisDestination=BlockPos.ORIGIN;
	public int dimension=0;
	private int destDim=0;
	public int dimIndex=0;
	private boolean isLoading=false;
	private static IBlockState blockBase=TBlocks.tardis.getDefaultState();
	private static IBlockState blockTop=TBlocks.tardis_top.getDefaultState();
	/** Time To Travel in Blocks/Tick**/
	private static final int MAX_TARDIS_SPEED=1;
	public NonNullList<SpaceTimeCoord> saveCoords=NonNullList.create().withSize(15, SpaceTimeCoord.ORIGIN);
	public EntityControl[] controls;
	public float fuel=1F;
	private boolean isFueling=false;
	private boolean shouldDelayLoop=true;
	private Ticket tardisTicket;
	public Ticket tardisLocTicket;
	private boolean chunkLoadTick=true;
	
	public TileEntityTardis() {}

	@Override
	public void update() {
		if(this.ticksToTravel>0) {
			--ticksToTravel;
			this.setFuel(fuel-0.0001F);
			if(ticksToTravel<=0)
				this.travel();
			int mt=shouldDelayLoop?420:40;
			if(ticksToTravel%mt==0) {
				this.shouldDelayLoop=false;
				world.playSound(null, pos, TSounds.loop, SoundCategory.BLOCKS, 0.4F, 1F);
			}
			if(fuel<=0.0)
				crash();
		}
		else if(this.isFueling()) {
			this.setFuel(fuel+0.0001F);
		}
		++ticks;
		if(ticks>=20) {
			this.updateServer();
			ticks=0;
		}
		if(chunkLoadTick) {
			if(!world.isRemote) {
				chunkLoadTick=false;
				tardisTicket=ForgeChunkManager.requestTicket(Tardis.instance, world, ForgeChunkManager.Type.NORMAL);
				ForgeChunkManager.forceChunk(tardisTicket, world.getChunkFromBlockCoords(this.getPos()).getPos());
				WorldServer ws=((WorldServer)world).getMinecraftServer().getWorld(this.dimension);
				this.tardisLocTicket=ForgeChunkManager.requestTicket(Tardis.instance, ws, ForgeChunkManager.Type.NORMAL);
				ForgeChunkManager.forceChunk(tardisLocTicket, ws.getChunkFromBlockCoords(tardisLocation).getPos());
			}
		}
	}

	public void travel() {
		if(!world.isRemote) {
			this.ticksToTravel=0;
			System.out.println("Traveled");
			World dWorld=((WorldServer)world).getMinecraftServer().getWorld(destDim);
			World oWorld=((WorldServer)world).getMinecraftServer().getWorld(dimension);
			BlockPos nPos=isValid(dWorld,this.getDestination())?this.getDestination():dWorld.getTopSolidOrLiquidBlock(this.getDestination());
			if(nPos!=null) {
				boolean b=dWorld.setBlockState(nPos, blockBase);
				dWorld.setBlockState(nPos.up(), blockTop);
				((TileEntityDoor)dWorld.getTileEntity(nPos.up())).consolePos=pos.toImmutable();
				this.setLocation(nPos);
				this.dimension=this.destDim;
			}
			ForgeChunkManager.releaseTicket(tardisLocTicket);
			tardisLocTicket=ForgeChunkManager.requestTicket(Tardis.instance, dWorld, ForgeChunkManager.Type.NORMAL);
			ForgeChunkManager.forceChunk(tardisLocTicket, dWorld.getChunkFromBlockCoords(tardisLocation).getPos());
			world.playSound(null, pos, TSounds.takeoff, SoundCategory.BLOCKS, 1F, 1F);
			this.markDirty();
		}
		shouldDelayLoop=true;
	}

	public void updateServer() {
		if(!world.isRemote) {
			((WorldServer)world).getMinecraftServer().getPlayerList().sendToAllNearExcept(null, pos.getX(), pos.getY(), pos.getZ(), 8,TDimensions.id, this.getUpdatePacket());
		}
	}

	public boolean isValid(World world,BlockPos pos) {
		if(!world.getBlockState(pos).causesSuffocation()&&!world.getBlockState(pos.up()).causesSuffocation()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagCompound tardisTag=tag.getCompoundTag("tardis");
		{
			this.ticksToTravel=tardisTag.getInteger("timeLeft");
			this.tardisDestination=BlockPos.fromLong(tardisTag.getLong("tardisDest"));
			this.tardisLocation=BlockPos.fromLong(tardisTag.getLong("tardisLoc"));
			this.dimension=tardisTag.getInteger("dim");
			this.destDim=tardisTag.getInteger("destDim");
			this.fuel=tardisTag.getFloat("fuel");
			NBTTagList coordList=tardisTag.getTagList("coordList", Constants.NBT.TAG_COMPOUND);
			int i=0;
			for(NBTBase base:coordList) {
				saveCoords.set(i,SpaceTimeCoord.readFromNBT((NBTTagCompound)base));
				++i;
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		
		NBTTagCompound tardisTag=new NBTTagCompound();
		{
			tardisTag.setInteger("timeLeft",this.ticksToTravel);
			tardisTag.setLong("tardisDest", this.tardisDestination.toLong());
			tardisTag.setLong("tardisLoc", this.tardisLocation.toLong());
			tardisTag.setInteger("dim",this.dimension);
			tardisTag.setInteger("destDim", this.destDim);
			tardisTag.setFloat("fuel", fuel);
			NBTTagList cList=new NBTTagList();
			for(SpaceTimeCoord co:saveCoords) {
				cList.appendTag(co.writeToNBT(new NBTTagCompound()));
			}
			tardisTag.setTag("coordList", cList);
		}
		tag.setTag("tardis", tardisTag);
		return super.writeToNBT(tag);
	}

	public void setDesination(BlockPos pos,int dimension) {
		this.tardisDestination=pos.up().toImmutable();
		this.destDim=dimension;
		this.markDirty();
	}
	public int calcTimeToTravel() {
		double dist=this.tardisLocation.getDistance(this.tardisDestination.getX(), this.tardisDestination.getY(), this.tardisDestination.getZ());
		return (int)((dist/MAX_TARDIS_SPEED)+440);
	}
	
	public BlockPos getDestination() {
		return this.tardisDestination;
	}
	
	public BlockPos getLocation() {
		return this.tardisLocation;
	}
	
	public void setLocation(BlockPos pos) {
		this.tardisLocation=pos.toImmutable();
		this.markDirty();
	}

	public int getTargetDim() {
		return destDim;
	}
	
	public int getTicks() {
		return this.ticksToTravel;
	}
	
	public boolean isInFlight() {
		return this.ticksToTravel>0;
	}
	
	public void setLoading(boolean b) {
		this.isLoading=b;
		this.markDirty();
	}
	
	public boolean getLoading() {
		return this.isLoading;
	}
	
	public boolean isFueling() {
		return isFueling;
	}
	
	public void setFueling(boolean b) {
		isFueling=b;
		this.markDirty();
	}
	
	private void setFuel(float f) {
		fuel=f>1.0F?1.0F:f;
		this.markDirty();
	}

	public void setSpaceTimeCoordnate(SpaceTimeCoord co) {
		this.setDesination(co.getPos(), co.getDimension());
	}
	
	public boolean startFlight() {
		if(this.getDestination().equals(BlockPos.ORIGIN))return false;
		if(fuel<=0.0F)return false;
		this.ticksToTravel=this.calcTimeToTravel();
		world.playSound(null, this.pos, TSounds.takeoff, SoundCategory.BLOCKS, 1F, 1F);
		if(!world.isRemote) {
			WorldServer oWorld=((WorldServer)world).getMinecraftServer().getWorld(dimension);
			oWorld.setBlockToAir(this.tardisLocation);
			oWorld.setBlockToAir(this.tardisLocation.up());
			ForgeChunkManager.unforceChunk(tardisLocTicket, oWorld.getChunkFromBlockCoords(getLocation()).getPos());
			ForgeChunkManager.releaseTicket(tardisLocTicket);
		}
		this.markDirty();
		return true;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos,-1,this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag=new NBTTagCompound();
		tag.setInteger("dim", dimension);
		tag.setInteger("destDim", this.destDim);
		tag.setLong("loc", this.getLocation().toLong());
		tag.setLong("dest", this.getDestination().toLong());
		tag.setFloat("fuel", fuel);
		tag.setInteger("timeLeft", ticksToTravel);
		return tag;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(world.isRemote) {
			NBTTagCompound tag=pkt.getNbtCompound();
			this.destDim=tag.getInteger("destDim");
			this.dimension=tag.getInteger("dim");
			this.tardisDestination=BlockPos.fromLong(tag.getLong("dest"));
			this.tardisLocation=BlockPos.fromLong(tag.getLong("loc"));
			this.fuel=tag.getFloat("fuel");
			this.ticksToTravel=tag.getInteger("timeLeft");
		}
	}
	
	public void setTargetDimension(int id) {
		this.setDesination(this.getLocation(), id);
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		if(createControls()) {
			for(EntityControl con:controls) {
				con.setPosition(pos.getX()+con.getOffset().x, pos.getY()+con.getOffset().y, pos.getZ()+con.getOffset().z);
				if(!world.isRemote)world.spawnEntity(con);
			}
		}
		updateServer();
	}

	public boolean createControls() {
		if(controls==null) {
			controls=new EntityControl[] {
					new ControlLaunch(this),
					new ControlX(this),
					new ControlY(this),
					new ControlZ(this),
					new ControlDimChange(this),
					new ControlScreen(this),
					new ControlRandom(this),
					new ControlDoor(this),
					new ControlSTCLoad(this),
					new ControlSTCButton(this,0,Helper.normalizeVec3d(0,0,0)),
					new ControlSTCButton(this,1,Helper.normalizeVec3d(1.5,0,0)),
					new ControlSTCButton(this,2,Helper.normalizeVec3d(3,0,0)),
					new ControlSTCButton(this,3,Helper.normalizeVec3d(4.5,0,0)),
					new ControlSTCButton(this,4,Helper.normalizeVec3d(6,0,0)),
					new ControlSTCButton(this,5,Helper.normalizeVec3d(0,-2,2.5)),
					new ControlSTCButton(this,6,Helper.normalizeVec3d(1.5,-2,2.5)),
					new ControlSTCButton(this,7,Helper.normalizeVec3d(3,-2,2.5)),
					new ControlSTCButton(this,8,Helper.normalizeVec3d(4.5,-2,2.5)),
					new ControlSTCButton(this,9,Helper.normalizeVec3d(6,-2,2.5)),
					new ControlScanner(this),
					new ControlFlight(this),
					new ControlFuel(this)
			};
			return true;
		}
		return false;
	}
	
	@Override
	public void invalidate() {
		for(EntityControl e:controls) {
			e.setDead();
		}
		ForgeChunkManager.releaseTicket(tardisTicket);
		ForgeChunkManager.releaseTicket(tardisLocTicket);
		super.invalidate();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos.getX(),pos.getY(),pos.getZ(),pos.getX()+1,pos.getY()+1,pos.getZ()+1).grow(3,3,3);
	}
	
	public EntityControl getControl(Class clazz) {
		for(EntityControl e:controls) {
			if(e.getClass()==clazz)
				return e;
		}
		return null;
	}

	@Override
	public void onChunkUnload() {
		for(EntityControl c:controls) {
			c.setDead();
		}
		super.onChunkUnload();
	}
	
	public void crash() {
		if(!world.isRemote) {
			ChunkPos cPos=world.getChunkFromBlockCoords(this.getLocation()).getPos();
			WorldServer ws=((WorldServer)world).getMinecraftServer().getWorld(dimension);
			ws.getChunkProvider().loadChunk(cPos.x, cPos.z);
			this.setDesination(getLocation(), dimension);
			BlockPos pos=getLocation();
			ws.createExplosion(null, pos.getX(),pos.getY(),pos.getZ(), 3F,true);
			this.travel();
			world.playSound(null, this.getPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.BLOCKS, 1F,1F);
		}
		else {
			Random rand=new Random();
			for(int i=0;i<300;++i) {
				world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, getPos().getX()+(rand.nextInt(3)-1),getPos().getY()+(rand.nextInt(3)-1),getPos().getZ()+(rand.nextInt(3)-1), 0,1,0,0);
			}
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}
}
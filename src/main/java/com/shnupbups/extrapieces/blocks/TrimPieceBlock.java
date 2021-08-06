package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.register.ModProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TrimPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final EnumProperty<Direction.Axis> AXIS;
	public static final EnumProperty<ModProperties.TrimType> TYPE;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape Y_SHAPE_Q0;
	protected static final VoxelShape Y_SHAPE_Q1;
	protected static final VoxelShape Y_SHAPE_Q2;
	protected static final VoxelShape Y_SHAPE_Q3;
	protected static final VoxelShape X_SHAPE_Q0;
	protected static final VoxelShape X_SHAPE_Q1;
	protected static final VoxelShape X_SHAPE_Q2;
	protected static final VoxelShape X_SHAPE_Q3;
	protected static final VoxelShape Z_SHAPE_Q0;
	protected static final VoxelShape Z_SHAPE_Q1;
	protected static final VoxelShape Z_SHAPE_Q2;
	protected static final VoxelShape Z_SHAPE_Q3;

	static {
		TYPE = ModProperties.TRIM_TYPE;
		AXIS = Properties.AXIS;
		WATERLOGGED = Properties.WATERLOGGED;
		Y_SHAPE_Q0 = Block.createCuboidShape(0f, 0f, 0f, 8f, 16f, 8f);
		Y_SHAPE_Q1 = Block.createCuboidShape(8f, 0f, 0f, 16f, 16f, 8f);
		Y_SHAPE_Q2 = Block.createCuboidShape(8f, 0f, 8f, 16f, 16f, 16f);
		Y_SHAPE_Q3 = Block.createCuboidShape(0f, 0f, 8f, 8f, 16f, 16f);
		X_SHAPE_Q0 = Block.createCuboidShape(0f, 0f, 0f, 16f, 8f, 8f);
		X_SHAPE_Q1 = Block.createCuboidShape(0f, 8f, 0f, 16f, 16f, 8f);
		X_SHAPE_Q2 = Block.createCuboidShape(0f, 8f, 8f, 16f, 16f, 16f);
		X_SHAPE_Q3 = Block.createCuboidShape(0f, 0f, 8f, 16f, 8f, 16f);
		Z_SHAPE_Q0 = Block.createCuboidShape(0f, 0f, 0f, 8f, 8f, 16f);
		Z_SHAPE_Q1 = Block.createCuboidShape(0f, 8f, 0f, 8f, 16f, 16f);
		Z_SHAPE_Q2 = Block.createCuboidShape(8f, 8f, 0f, 16f, 16f, 16f);
		Z_SHAPE_Q3 = Block.createCuboidShape(8f, 0f, 0f, 16f, 8f, 16f);
	}

	private final PieceSet set;

	public TrimPieceBlock(PieceSet set) {
		super(FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getDefaultMapColor()).breakByTool(set.getHarvestTool(), set.getHarvestLevel()));
		this.set = set;
		this.setDefaultState(this.getDefaultState().with(TYPE, ModProperties.TrimType.Q0).with(AXIS, Direction.Axis.Y).with(WATERLOGGED, false));
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceTypes.TRIM;
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, ShapeContext shapeContext_1) {
		ModProperties.TrimType trimType_1 = blockState_1.get(TYPE);
		Direction.Axis axis = blockState_1.get(AXIS);
		switch(trimType_1) {
			case Q1:
				switch (axis) {
					case X:
						return X_SHAPE_Q1;
					case Z:
						return Z_SHAPE_Q1;
					default:
						return Y_SHAPE_Q1;
				}
			case Q2:
				switch (axis) {
					case X:
						return X_SHAPE_Q2;
					case Z:
						return Z_SHAPE_Q2;
					default:
						return Y_SHAPE_Q2;
				}
			case Q3:
				switch (axis) {
					case X:
						return X_SHAPE_Q3;
					case Z:
						return Z_SHAPE_Q3;
					default:
						return Y_SHAPE_Q3;
				}
		default:
			switch (axis) {
				case X:
					return X_SHAPE_Q0;
				case Z:
					return Z_SHAPE_Q0;
				default:
					return Y_SHAPE_Q0;
			}
		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
		FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
		Axis sideAxis_1 = itemPlacementContext_1.getSide().getAxis();
		double xPos = itemPlacementContext_1.getHitPos().getX() - blockPos_1.getX();
		double yPos = itemPlacementContext_1.getHitPos().getY() - blockPos_1.getY();
		double zPos = itemPlacementContext_1.getHitPos().getZ() - blockPos_1.getZ();
		
		ModProperties.TrimType trimType_1 = ModProperties.TrimType.Q0;
		switch (sideAxis_1) {
			case X:
				if (yPos > 0.5)
					if (zPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q2;
					else trimType_1 = ModProperties.TrimType.Q1;
				else if (zPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q3;
				break;
			case Z:
				if (yPos > 0.5)
					if (xPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q2;
					else trimType_1 = ModProperties.TrimType.Q1;
				else if (xPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q3;
				break;
			default:
				if (xPos > 0.5)
					if (zPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q2;
					else trimType_1 = ModProperties.TrimType.Q1;
				else if (zPos > 0.5 ) trimType_1 = ModProperties.TrimType.Q3;
				break;
		}
		return this.getDefaultState().with(TYPE, trimType_1).with(AXIS, sideAxis_1).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, WorldAccess worldAccess_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(WATERLOGGED)) {
			worldAccess_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(worldAccess_1));
		}
		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, worldAccess_1, blockPos_1, blockPos_2);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> StateManager$Builder_1) {
		StateManager$Builder_1.add(TYPE, AXIS, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, NavigationType navigationType_1) {
		return false;
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		super.randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
		this.getBase().randomDisplayTick(this.getBaseState(), world_1, blockPos_1, random_1);
	}
	
	@Override
	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		super.onBlockBreakStart(blockState_1, world_1, blockPos_1, playerEntity_1);
		this.getBaseState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
	}
	
	@Override
	public void onBroken(WorldAccess worldAccess_1, BlockPos blockPos_1, BlockState blockState_1) {
		super.onBroken(worldAccess_1, blockPos_1, blockState_1);
		this.getBase().onBroken(worldAccess_1, blockPos_1, blockState_1);
	}
	
	@Override
	public float getBlastResistance() {
		return this.getBase().getBlastResistance();
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onBlockAdded(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			this.getBase().getDefaultState().neighborUpdate(world_1, blockPos_1, Blocks.AIR, blockPos_1, false);
			this.getBase().getDefaultState().onBlockAdded(world_1, blockPos_1, blockState_2, false);
		}
	}
	
	@Override
	public void onStateReplaced(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onStateReplaced(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			this.getBaseState().onStateReplaced(world_1, blockPos_1, blockState_2, boolean_1);
		}
	}
	
	@Override
	public void onSteppedOn(World world_1, BlockPos blockPos_1, BlockState blockState_1, Entity entity_1) {
		super.onSteppedOn(world_1, blockPos_1, blockState_1, entity_1);
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, blockState_1, entity_1);
		} catch (IllegalArgumentException ignored) {
			ExtraPieces.debugLog("Caught an exception in onSteppedOn for "+this.getPieceString());
		}
	}
	
	@Override
	public void scheduledTick(BlockState blockState_1, ServerWorld world_1, BlockPos blockPos_1, Random random_1) {
		super.scheduledTick(blockState_1, world_1, blockPos_1, random_1);
		this.getBase().scheduledTick(this.getBaseState(), world_1, blockPos_1, random_1);
	}
	
	@Override
	public ActionResult onUse(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		ActionResult a = super.onUse(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
		if(a.isAccepted() || this.getBaseState().onUse(world_1, playerEntity_1, hand_1, blockHitResult_1).isAccepted()) {
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}
	
	@Override
	public void onDestroyedByExplosion(World world_1, BlockPos blockPos_1, Explosion explosion_1) {
		super.onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
		this.getBase().onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this || super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return super.emitsRedstonePower(blockState_1) || this.getBaseState().emitsRedstonePower();
	}
	
	@Override
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return this.getBaseState().getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
	}

}

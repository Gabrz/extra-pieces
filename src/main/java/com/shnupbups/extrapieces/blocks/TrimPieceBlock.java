package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.register.ModProperties;
import com.shnupbups.extrapieces.register.ModProperties.TrimType;

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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TrimPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final EnumProperty<ModProperties.TrimType> TYPE;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape SHAPE_Z0;
	protected static final VoxelShape SHAPE_Z1;
	protected static final VoxelShape SHAPE_Z2;
	protected static final VoxelShape SHAPE_Z3;
	protected static final VoxelShape SHAPE_X0;
	protected static final VoxelShape SHAPE_X1;
	protected static final VoxelShape SHAPE_X2;
	protected static final VoxelShape SHAPE_X3;
	protected static final VoxelShape SHAPE_Y0;
	protected static final VoxelShape SHAPE_Y1;
	protected static final VoxelShape SHAPE_Y2;
	protected static final VoxelShape SHAPE_Y3;

	protected static final VoxelShape SHAPE_Y0Z0;
	protected static final VoxelShape SHAPE_Y0X0;
	protected static final VoxelShape SHAPE_Y0Z0X0;
	protected static final VoxelShape SHAPE_Z0X0;
	protected static final VoxelShape SHAPE_Y0Z1;
	protected static final VoxelShape SHAPE_Y0X1;
	protected static final VoxelShape SHAPE_Y0Z1X1;
	protected static final VoxelShape SHAPE_Z1X1;

	protected static final VoxelShape SHAPE_Y1Z3;
	protected static final VoxelShape SHAPE_Y1X0;
	protected static final VoxelShape SHAPE_Z3X0;
	protected static final VoxelShape SHAPE_Y1Z3X0;
	protected static final VoxelShape SHAPE_Y1Z2;
	protected static final VoxelShape SHAPE_Y1X1;
	protected static final VoxelShape SHAPE_Z2X1;
	protected static final VoxelShape SHAPE_Y1Z2X1;

	protected static final VoxelShape SHAPE_Y2Z3;
	protected static final VoxelShape SHAPE_Y2X3;
	protected static final VoxelShape SHAPE_Z3X3;
	protected static final VoxelShape SHAPE_Y2Z3X3;
	protected static final VoxelShape SHAPE_Y2Z2;
	protected static final VoxelShape SHAPE_Y2X2;
	protected static final VoxelShape SHAPE_Z2X2;
	protected static final VoxelShape SHAPE_Y2Z2X2;

	protected static final VoxelShape SHAPE_Y3Z0;
	protected static final VoxelShape SHAPE_Y3X3;
	protected static final VoxelShape SHAPE_Z0X3;
	protected static final VoxelShape SHAPE_Y3Z0X3;
	protected static final VoxelShape SHAPE_Y3Z1;
	protected static final VoxelShape SHAPE_Y3X2;
	protected static final VoxelShape SHAPE_Z1X2;
	protected static final VoxelShape SHAPE_Y3Z1X2;

	static {
		TYPE = ModProperties.TRIM_TYPE;
		WATERLOGGED = Properties.WATERLOGGED;
		SHAPE_Z0 = Block.createCuboidShape(0f, 0f, 0f, 8f, 8f, 16f);
		SHAPE_Z1 = Block.createCuboidShape(0f, 8f, 0f, 8f, 16f, 16f);
		SHAPE_Z2 = Block.createCuboidShape(8f, 8f, 0f, 16f, 16f, 16f);
		SHAPE_Z3 = Block.createCuboidShape(8f, 0f, 0f, 16f, 8f, 16f);
		SHAPE_X0 = Block.createCuboidShape(0f, 0f, 0f, 16f, 8f, 8f);
		SHAPE_X1 = Block.createCuboidShape(0f, 8f, 0f, 16f, 16f, 8f);
		SHAPE_X2 = Block.createCuboidShape(0f, 8f, 8f, 16f, 16f, 16f);
		SHAPE_X3 = Block.createCuboidShape(0f, 0f, 8f, 16f, 8f, 16f);
		SHAPE_Y0 = Block.createCuboidShape(0f, 0f, 0f, 8f, 16f, 8f);
		SHAPE_Y1 = Block.createCuboidShape(8f, 0f, 0f, 16f, 16f, 8f);
		SHAPE_Y2 = Block.createCuboidShape(8f, 0f, 8f, 16f, 16f, 16f);
		SHAPE_Y3 = Block.createCuboidShape(0f, 0f, 8f, 8f, 16f, 16f);

		SHAPE_Y0Z0 = VoxelShapes.union(SHAPE_Y0, SHAPE_Z0);
		SHAPE_Y0X0 = VoxelShapes.union(SHAPE_Y0, SHAPE_X0);
		SHAPE_Z0X0 = VoxelShapes.union(SHAPE_Z0, SHAPE_X0);
		SHAPE_Y0Z0X0 = VoxelShapes.union(SHAPE_Y0, SHAPE_Z0, SHAPE_X0);
		SHAPE_Y0Z1 = VoxelShapes.union(SHAPE_Y0, SHAPE_Z1);
		SHAPE_Y0X1 = VoxelShapes.union(SHAPE_Y0, SHAPE_X1);
		SHAPE_Z1X1 = VoxelShapes.union(SHAPE_Z1, SHAPE_X1);
		SHAPE_Y0Z1X1 = VoxelShapes.union(SHAPE_Y0, SHAPE_Z1, SHAPE_X1);

		SHAPE_Y1Z3 = VoxelShapes.union(SHAPE_Y1, SHAPE_Z3);
		SHAPE_Y1X0 = VoxelShapes.union(SHAPE_Y1, SHAPE_X0);
		SHAPE_Z3X0 = VoxelShapes.union(SHAPE_Z3, SHAPE_X0);
		SHAPE_Y1Z3X0 = VoxelShapes.union(SHAPE_Y1, SHAPE_Z3, SHAPE_X0);
		SHAPE_Y1Z2 = VoxelShapes.union(SHAPE_Y1, SHAPE_Z2);
		SHAPE_Y1X1 = VoxelShapes.union(SHAPE_Y1, SHAPE_X1);
		SHAPE_Z2X1 = VoxelShapes.union(SHAPE_Z2, SHAPE_X1);
		SHAPE_Y1Z2X1 = VoxelShapes.union(SHAPE_Y1, SHAPE_Z2, SHAPE_X1);

		SHAPE_Y2Z3 = VoxelShapes.union(SHAPE_Y2, SHAPE_Z3);
		SHAPE_Y2X3 = VoxelShapes.union(SHAPE_Y2, SHAPE_X3);
		SHAPE_Z3X3 = VoxelShapes.union(SHAPE_Z3, SHAPE_X3);
		SHAPE_Y2Z3X3 = VoxelShapes.union(SHAPE_Y2, SHAPE_Z3, SHAPE_X3);
		SHAPE_Y2Z2 = VoxelShapes.union(SHAPE_Y2, SHAPE_Z2);
		SHAPE_Y2X2 = VoxelShapes.union(SHAPE_Y2, SHAPE_X2);
		SHAPE_Z2X2 = VoxelShapes.union(SHAPE_Z2, SHAPE_X2);
		SHAPE_Y2Z2X2 = VoxelShapes.union(SHAPE_Y2, SHAPE_Z2, SHAPE_X2);

		SHAPE_Y3Z0 = VoxelShapes.union(SHAPE_Y3, SHAPE_Z0);
		SHAPE_Y3X3 = VoxelShapes.union(SHAPE_Y3, SHAPE_X3);
		SHAPE_Z0X3 = VoxelShapes.union(SHAPE_Z0, SHAPE_X3);
		SHAPE_Y3Z0X3 = VoxelShapes.union(SHAPE_Y3, SHAPE_Z0, SHAPE_X3);
		SHAPE_Y3Z1 = VoxelShapes.union(SHAPE_Y3, SHAPE_Z1);
		SHAPE_Y3X2 = VoxelShapes.union(SHAPE_Y3, SHAPE_X2);
		SHAPE_Z1X2 = VoxelShapes.union(SHAPE_Z1, SHAPE_X2);
		SHAPE_Y3Z1X2 = VoxelShapes.union(SHAPE_Y3, SHAPE_Z1, SHAPE_X2);

	}

	private final PieceSet set;

	public TrimPieceBlock(PieceSet set) {
		super(FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getDefaultMapColor()).breakByTool(set.getHarvestTool(), set.getHarvestLevel()));
		this.set = set;
		this.setDefaultState(this.getDefaultState().with(TYPE, ModProperties.TrimType.Z0).with(WATERLOGGED, false));
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

		switch (blockState_1.get(TYPE)) {
			case X0:
				return SHAPE_X0;
			case X1:
				return SHAPE_X1;
			case X2:
				return SHAPE_X2;
			case X3:
				return SHAPE_X3;
			case Y0:
				return SHAPE_Y0;
			case Y1:
				return SHAPE_Y1;
			case Y2:
				return SHAPE_Y2;
			case Y3:
				return SHAPE_Y3;
			case Z0:
				return SHAPE_Z0;
			case Z1:
				return SHAPE_Z1;
			case Z2:
				return SHAPE_Z2;
			case Z3:
				return SHAPE_Z3;

			case Y0Z0:
				return SHAPE_Y0Z0;
			case Y0X0:
				return SHAPE_Y0X0;
			case Y0Z0X0:
				return SHAPE_Y0Z0X0;
			case Z0X0:
				return SHAPE_Z0X0;
			case Y0Z1:
				return SHAPE_Y0Z1;
			case Y0X1:
				return SHAPE_Y0X1;
			case Y0Z1X1:
				return SHAPE_Y0Z1X1;
			case Z1X1:
				return SHAPE_Z1X1;

			case Y1Z3:
				return SHAPE_Y1Z3;
			case Y1X0:
				return SHAPE_Y1X0;
			case Z3X0:
				return SHAPE_Z3X0;
			case Y1Z3X0:
				return SHAPE_Y1Z3X0;
			case Y1Z2:
				return SHAPE_Y1Z2;
			case Y1X1:
				return SHAPE_Y1X1;
			case Z2X1:
				return SHAPE_Z2X1;
			case Y1Z2X1:
				return SHAPE_Y1Z2X1;

			case Y2Z3:
				return SHAPE_Y2Z3;
			case Y2X3:
				return SHAPE_Y2X3;
			case Z3X3:
				return SHAPE_Z3X3;
			case Y2Z3X3:
				return SHAPE_Y2Z3X3;
			case Y2Z2:
				return SHAPE_Y2Z2;
			case Y2X2:
				return SHAPE_Y2X2;
			case Z2X2:
				return SHAPE_Z2X2;
			case Y2Z2X2:
				return SHAPE_Y2Z2X2;

			case Y3Z0:
				return SHAPE_Y3Z0;
			case Y3X3:
				return SHAPE_Y3X3;
			case Z0X3:
				return SHAPE_Z0X3;
			case Y3Z0X3:
				return SHAPE_Y3Z0X3;
			case Y3Z1:
				return SHAPE_Y3Z1;
			case Y3X2:
				return SHAPE_Y3X2;
			case Z1X2:
				return SHAPE_Z1X2;
			case Y3Z1X2:
				return SHAPE_Y3Z1X2;

			default:
				return SHAPE_Z0;
		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(itemPlacementContext_1.getBlockPos());
		ModProperties.TrimType trimType_1 = getPlacementTrimType(itemPlacementContext_1);
		return this.getDefaultState().with(TYPE, trimType_1).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, WorldAccess worldAccess_1, BlockPos blockPos_1, BlockPos blockPos_2) {

		TrimType trimType_1 = blockState_1.get(TYPE);

		switch (trimType_1) {
			case Z0:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x3");
				break;
			case Z1:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x2");
				break;
			case Z2:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x2");
				break;
			case Z3:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x3");
				break;

			case X0:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z3");
				break;
			case X1:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z2");
				break;
			case X2:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z2");
				break;
			case X3:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z3");
				break;

			case Y0:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x1");
				break;
			case Y0Z0:
			case Y0X0:
			case Z0X0:
			case Y0Z0X0:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y0");
				break;
			case Y0Z1:
			case Y0X1:
			case Z1X1:
			case Y0Z1X1:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y0");
				break;

			case Y1:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x1");
				break;
			case Y1Z3:
			case Y1X0:
			case Z3X0:
			case Y1Z3X0:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x0");
				break;
			case Y1Z2:
			case Y1X1:
			case Z2X1:
			case Y1Z2X1:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.SOUTH, "z2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x1");
				break;

			case Y2:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x3");
				break;
			case Y2Z3:
			case Y2X3:
			case Z3X3:
			case Y2Z3X3:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x3");
				break;
			case Y2Z2:
			case Y2X2:
			case Z2X2:
			case Y2Z2X2:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.WEST, "x2");
				break;

			case Y3:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x2");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x3");
				break;
			case Y3Z0:
			case Y3X3:
			case Z0X3:
			case Y3Z0X3:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.UP, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z0");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x3");
				break;
			case Y3Z1:
			case Y3X2:
			case Z1X2:
			case Y3Z1X2:
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.DOWN, "y3");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.NORTH, "z1");
				trimType_1 = getNbUpdTrimType(trimType_1, direction_1, blockState_2, Direction.EAST, "x2");
				break;

			default:
				break;
		}

		if (blockState_1.get(WATERLOGGED)) {
			worldAccess_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(worldAccess_1));
		}
		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, worldAccess_1, blockPos_1, blockPos_2).with(TYPE, (trimType_1 == null) ? blockState_1.get(TYPE) : trimType_1);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> StateManager$Builder_1) {
		StateManager$Builder_1.add(TYPE, WATERLOGGED);
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

	private static TrimType getPlacementTrimType(ItemPlacementContext context) {
		BlockPos blockPos_1 = context.getBlockPos();
		Direction direction_1 = context.getSide();
		double xPos = context.getHitPos().getX() - blockPos_1.getX();
		double yPos = context.getHitPos().getY() - blockPos_1.getY();
		double zPos = context.getHitPos().getZ() - blockPos_1.getZ();

		TrimType trimType_1;
		switch (direction_1) {
			case EAST:
			case WEST:
				if (yPos > 0.5) {
					if (zPos > 0.5) {
						trimType_1 = ModProperties.TrimType.X2;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y2");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y3");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z2");
					} else {
						trimType_1 = ModProperties.TrimType.X1;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y0");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z2");
					}
				} else if (zPos > 0.5) {
					trimType_1 = ModProperties.TrimType.X3;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y2");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y3");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z3");
				} else {
					trimType_1 = ModProperties.TrimType.X0;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y1");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z3");
				}
				break;
			case NORTH:
			case SOUTH:
				if (yPos > 0.5) {
					if (xPos > 0.5) {
						trimType_1 = ModProperties.TrimType.Z2;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y2");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x2");
					} else {
						trimType_1 = ModProperties.TrimType.Z1;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y0");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.DOWN, "y3");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x1");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x2");
					}
				} else if (xPos > 0.5) {
					trimType_1 = ModProperties.TrimType.Z3;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y1");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y2");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x3");
				} else {
					trimType_1 = ModProperties.TrimType.Z0;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.UP, "y3");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x3");
				}
				break;
			default:
				if (xPos > 0.5) {
					if (zPos > 0.5) {
						trimType_1 = ModProperties.TrimType.Y2;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z2");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z3");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x2");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x3");
					} else {
						trimType_1 = ModProperties.TrimType.Y1;
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z2");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z3");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x0");
						trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.WEST, "x1");
					}
				} else if (zPos > 0.5) {
					trimType_1 = ModProperties.TrimType.Y3;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.NORTH, "z1");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x2");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x3");
				} else {
					trimType_1 = ModProperties.TrimType.Y0;
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.SOUTH, "z1");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x0");
					trimType_1 = getPlacementNbTrimType(trimType_1, context, Direction.EAST, "x1");
				}
				break;
		}
		return trimType_1;
	}

	// Deterimine TrimType of Placed Block, based on neighboring block.
	private static TrimType getPlacementNbTrimType(TrimType curTrimType, ItemPlacementContext context, Direction nbDirection, String nbType) {
		BlockState nbBlock = context.getWorld().getBlockState(context.getBlockPos().offset(nbDirection));
		TrimType result = curTrimType;

		if (nbBlock.getBlock() instanceof TrimPieceBlock && nbType.equals(nbBlock.get(TYPE).asString()))
			// Block is a TrimPiece with expected value, combine neighbor- with current type
			result = ModProperties.getTrimTypeByValue(curTrimType.asString() + nbType);
		return result;
	}

	// Determine TrimType of Neigbor block during neighborUpdate
	private TrimType getNbUpdTrimType(TrimType curTrimType, Direction nbDirection, BlockState blockState_2, Direction chkDirection, String chkType) {
		TrimType result = curTrimType;

		// Check at which direction a neighbor block was updated.
		if (nbDirection == chkDirection) {
			if (blockState_2.getBlock() instanceof TrimPieceBlock) {
				if (chkType.equals(blockState_2.get(TYPE).asString())) {
					// Block is a TrimPiece with expected value, combine neighbor- with current type
					result = ModProperties.getTrimTypeByValue(curTrimType.asString() + chkType);
				}
			} else {
				// Not a TrimPiecBlock, subtract/replace the neighbor- from the current type
				result = ModProperties.getTrimTypeByValue(curTrimType.asString().replace(chkType, ""));
			}
		}
		return result;
	}

}

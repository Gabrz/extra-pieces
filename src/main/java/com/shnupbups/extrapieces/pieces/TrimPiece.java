package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.TrimPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.register.ModProperties;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class TrimPiece extends PieceType {
	public TrimPiece() {
		super("trim");
	}

	public TrimPieceBlock getNew(PieceSet set) {
		return new TrimPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 6, "bbb").addToKey('b', PieceTypes.SIDING));
		return recipes;
	}

	public int getStonecuttingCount() {
		return 4;
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (ModProperties.TrimType t: ModProperties.TrimType.values()) {
				for (Direction.Axis a : Direction.Axis.values()) {
					state.variant("type=" + t.asString() + ",axis=" + a.asString(), var -> {
						var.uvlock(true);
						var.model(getModelPath(pb));
						switch (a) {
							case X:
								switch (t) {
									case Q1:
										var.rotationX(180);
										var.rotationY(90);
										break;
									case Q2:
										var.rotationX(180);
										var.rotationY(270);
										break;
									case Q3:
										var.rotationY(270);
										break;
									default:
										var.rotationY(90);
										break;
								}
								break;
							case Y:
								switch (t) {
									case Q1:
										var.rotationX(90);
										var.rotationY(180);
										break;
									case Q2:
										var.rotationX(90);
										var.rotationY(270);
										break;
									case Q3:
										var.rotationX(90);
										break;
									default:
										var.rotationX(90);
										var.rotationY(90);
										break;
								}
								break;
							default:
								switch (t) {
									case Q1:
										var.rotationX(180);
										break;
									case Q2:
										var.rotationX(180);
										var.rotationY(180);
										break;
									case Q3:
										var.rotationY(180);
										break;
									default:
										break;
								}
								break;
						}
					});
				}
			}
		});
	}
}

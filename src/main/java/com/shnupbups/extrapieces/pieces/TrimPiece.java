package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.TrimPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.register.ModProperties;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
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

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "double_x");
		addBlockModel(pack, pb, "double_y");
		addBlockModel(pack, pb, "tripple");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (ModProperties.TrimType t : ModProperties.TrimType.values()) {
				state.variant("type=" + t.asString(), var -> {
					var.uvlock(true);
					switch (t.asString().length()) {
						case 4:
							var.model(getModelPath(pb, "double_x"));
							break;
						case 6:
							var.model(getModelPath(pb, "tripple"));
							break;
						default:
							var.model(getModelPath(pb));
					}
					switch (t) {
						case Z0:
							break;
						case Z1:
							var.rotationX(180);
							break;
						case Z2:
							var.rotationX(180);
							var.rotationY(180);
							break;
						case Z3:
							var.rotationY(180);
							break;

						case X0:
							var.rotationY(90);
							break;
						case X1:
							var.rotationX(180);
							var.rotationY(90);
							break;
						case X2:
							var.rotationX(180);
							var.rotationY(270);
							break;
						case X3:
							var.rotationY(270);
							break;

						case Y0:
							var.rotationX(90);
							var.rotationY(90);
							break;
						case Y1:
							var.rotationX(90);
							var.rotationY(180);
							break;
						case Y2:
							var.rotationX(90);
							var.rotationY(270);
							break;
						case Y3:
							var.rotationX(90);
							break;

						case Y0Z0:
							var.rotationX(90);
							var.rotationY(90);
							break;
						case Y0X0:
							var.model(getModelPath(pb, "double_y"));
							var.rotationX(90);
							var.rotationY(90);
							break;
						case Z0X0:
						case Y0Z0X0:
							break;

						case Y0Z1:
							var.model(getModelPath(pb, "double_y"));
							var.rotationX(270);
							break;
						case Y0X1:
							var.rotationX(270);
							break;
						case Z1X1:
						case Y0Z1X1:
							var.rotationX(180);
							var.rotationY(90);
							break;

						case Y1Z3:
							var.model(getModelPath(pb, "double_y"));
							var.rotationX(90);
							var.rotationY(180);
							break;
						case Y1X0:
							var.rotationX(90);
							var.rotationY(180);
							break;
						case Z3X0:
						case Y1Z3X0:
							var.rotationY(90);
							break;

						case Y1Z2:
							var.rotationY(90);
							var.rotationX(270);
							break;
						case Y1X1:
							var.model(getModelPath(pb, "double_y"));
							var.rotationY(90);
							var.rotationX(270);
							break;
						case Z2X1:
						case Y1Z2X1:
							var.rotationX(180);
							var.rotationY(180);
							break;

						case Y2Z3:
							var.rotationX(90);
							var.rotationY(270);
							break;
						case Y2X3:
							var.model(getModelPath(pb, "double_y"));
							var.rotationX(90);
							var.rotationY(270);
							break;
						case Z3X3:
						case Y2Z3X3:
							var.rotationY(180);
							break;

						case Y2Z2:
							var.model(getModelPath(pb, "double_y"));
							var.rotationY(180);
							var.rotationX(270);
							break;
						case Y2X2:
							var.rotationY(180);
							var.rotationX(270);
							break;
						case Z2X2:
						case Y2Z2X2:
							var.rotationX(180);
							var.rotationY(270);
							break;

						case Y3Z0:
							var.model(getModelPath(pb, "double_y"));
							var.rotationX(90);
							break;
						case Y3X3:
							var.rotationX(90);
							break;
						case Z0X3:
						case Y3Z0X3:
							var.rotationY(270);
							break;

						case Y3Z1:
							var.rotationY(270);
							var.rotationX(270);
							break;
						case Y3X2:
							var.model(getModelPath(pb, "double_y"));
							var.rotationY(270);
							var.rotationX(270);
							break;
						case Z1X2:
						case Y3Z1X2:
							var.rotationX(180);
							break;

						default:
							break;
					}
				});
			}
		});
	}
}

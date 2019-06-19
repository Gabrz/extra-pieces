package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public interface PieceBlock {
	PieceType getType();
	Block getBlock();
	PieceSet getSet();
}
package protocolsupport.protocol.typeremapper.tile;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyBlockFace;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper.TileEntityWithBlockDataNBTRemapper;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTFloat;
import protocolsupport.utils.CollectionsUtils.ArrayMap.Entry;

public class TileEntitySkullRemapper extends TileEntityWithBlockDataNBTRemapper {
	protected boolean isPE;
	public TileEntitySkullRemapper(boolean isPE)
	{
		this.isPE = isPE;
	}
	protected static byte getLegacyData(BlockData skull) {
		if (skull instanceof Rotatable) {
			return LegacyBlockFace.getLegacyRotatableId(((Rotatable) skull).getRotation());
		}
		return 0;
	}

	protected void register(List<Entry<Consumer<NBTCompound>>> list, Material skull, int skulltype, boolean isPE) {
		for (BlockData blockdata : MaterialAPI.getBlockDataList(skull)) {
			byte rotation = getLegacyData(blockdata);
			list.add(new Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
				nbt.setTag("SkullType", new NBTByte((byte) skulltype));
				if(isPE)
					nbt.setTag("Rotation", new NBTFloat(rotation * 22.5F));
				else
					nbt.setTag("Rot", new NBTByte(rotation));
			}));
		}
	}

	protected void register(List<Entry<Consumer<NBTCompound>>> list, Material skull, int skulltype) {
		boolean isPE;
		for (BlockData blockdata : MaterialAPI.getBlockDataList(skull)) {
			byte rotation = getLegacyData(blockdata);
			list.add(new Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
				nbt.setTag("SkullType", new NBTByte((byte) skulltype));
				nbt.setTag("Rotation", new NBTFloat(rotation * 22.5F)); //For PE Clients
				nbt.setTag("Rot", new NBTByte(rotation)); //For PC Clients
			}));
		}
	}

	@Override
	protected void init(List<Entry<Consumer<NBTCompound>>> list) {
		register(list, Material.SKELETON_SKULL, 0);
		register(list, Material.WITHER_SKELETON_SKULL, 1);
		register(list, Material.ZOMBIE_HEAD, 2);
		register(list, Material.PLAYER_HEAD, 3);
		register(list, Material.CREEPER_HEAD, 4);
		register(list, Material.DRAGON_HEAD, 5);
		register(list, Material.SKELETON_WALL_SKULL, 0);
		register(list, Material.WITHER_SKELETON_WALL_SKULL, 1);
		register(list, Material.ZOMBIE_WALL_HEAD, 2);
		register(list, Material.PLAYER_WALL_HEAD, 3);
		register(list, Material.CREEPER_WALL_HEAD, 4);
		register(list, Material.DRAGON_WALL_HEAD, 5);
	}

}

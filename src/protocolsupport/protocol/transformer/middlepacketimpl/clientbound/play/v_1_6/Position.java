package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePosition;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Position extends MiddlePosition<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		y += + 1.6200000047683716D;
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(y + 1.6200000047683716D);
		serializer.writeDouble(z);
		serializer.writeFloat(yaw);
		serializer.writeFloat(pitch);
		serializer.writeBoolean(false);
		return RecyclableSingletonList.<PacketData>create(PacketData.create(ClientBoundPacket.PLAY_POSITION_ID, serializer));
	}

}
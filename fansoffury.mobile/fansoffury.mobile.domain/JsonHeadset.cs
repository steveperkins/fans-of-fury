using System;

namespace fansoffury.mobile.domain
{
	public class JsonHeadset
	{
		public JsonHeadset (Player player)
		{
			this.headsetId = player.HeadsetId;
			this.fanId = player.Fan;
			this.playerId = player.PlayerId;
			this.measurementType = player.MeasurementType;
		}

		public string headsetId { get; set; }

		public string fanId { get; set; }

		public string playerId { get; set; }

		public string measurementType { get; set; }
	}
}


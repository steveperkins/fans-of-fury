using System;
using fansoffury.mobile.domain;
using System.Collections.Generic;

namespace fansoffury.mobile.services
{
	public class HeadsetService : IHeadsetService
	{
		public List<Headset> GetHeadsets()
		{
			return new List<Headset> () {
				new Headset () { HeadsetId = "74E543D575B0", HeadsetName = "Headset 1 (74E543D575B0)" },
				new Headset () { HeadsetId = "20689D88BC4A", HeadsetName = "Headset 2 (20689D88BC4A)" },
				new Headset () { HeadsetId = "20689D4C0A08", HeadsetName = "Headset 3 (20689D4C0A08)" },
				new Headset () { HeadsetId = "20689D79DE8A", HeadsetName = "Headset 4 (20689D79DE8A)" },
			};
		}
	}
}


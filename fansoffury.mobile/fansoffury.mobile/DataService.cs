using System;
using System.Collections.Generic;

namespace fansoffury.mobile
{
	public interface IDataService
	{
		List<Headset> GetHeadsets ();
	}
	public class DataService : IDataService
	{
		#region IDataService implementation

		public List<Headset> GetHeadsets ()
		{
			return new List<Headset> () {
				new Headset() { HeadsetId = 1, HeadsetName = "Test 1" },
				new Headset() { HeadsetId = 2, HeadsetName = "Test 2" },
				new Headset() { HeadsetId = 3, HeadsetName = "Test 3" },
				new Headset() { HeadsetId = 4, HeadsetName = "Test 4" },
				new Headset() { HeadsetId = 5, HeadsetName = "Test 5" }
			};
		}

		#endregion

	}
}


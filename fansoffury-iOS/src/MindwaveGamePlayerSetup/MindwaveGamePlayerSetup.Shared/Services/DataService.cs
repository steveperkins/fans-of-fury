using System;
using System.Collections.Generic;

namespace MindwaveGamePlayerSetup
{
	public interface IDataService
	{
		List<int> GetHeadsets ();
	}
	public class DataService : IDataService
	{
		#region IDataService implementation

		public List<int> GetHeadsets ()
		{
			return new List<int> () {
				1, 2, 3, 4, 5
			};
		}

		#endregion

	}
}


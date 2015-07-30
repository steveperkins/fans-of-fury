using System;
using fansoffury.mobile.domain;
using System.Threading.Tasks;

namespace fansoffury.mobile.services
{
	public interface IPlayerService
	{
		Player GetPlayer(string id);
		Player AssignPlayer (JsonHeadset headset);
	}
}


using System;
using fansoffury.mobile.domain;
using System.Threading.Tasks;

namespace fansoffury.mobile.services
{
	public interface IPlayerService
	{
		Task<Player> GetPlayer(string id);
		void AssignPlayer (Player player);
	}
}


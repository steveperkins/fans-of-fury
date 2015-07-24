using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Linq;
using System.Collections;

namespace fansoffury.mobile
{
	public class Player
	{
		private string _playerId;

		public Player(string id)
		{
			_playerId = id;
		}

		public string Name { get; set; }

		public int HeadsetId  { get; set; }

		public string PlayerId 
		{ 
			get { return _playerId.Contains('-') ? _playerId.Substring(_playerId.LastIndexOf('-') + 1, _playerId.Length - _playerId.LastIndexOf('-') - 1) : _playerId; }
		}
	}
}


using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Linq;
using System.Collections;

namespace fansoffury.mobile.domain
{
	public class Player
	{
		private string _playerId;

		public Player (string id)
		{
			_playerId = id;
		}

		public string Id { get; set; }

		public double AttentionLevel { get; set; }

		public double MeditationLevel { get; set; }

		public string MeasurementType { get; set; }

		public MeasurementTypeEnum MeasurementTypeValue {
			get {
				if (MeasurementType == MeasurementTypeEnum.Meditation.ToString ().ToUpper ())
					return MeasurementTypeEnum.Meditation;

				return MeasurementTypeEnum.Attention;
			}
			set {
				MeasurementType = value.ToString ().ToUpper ();
			}
		}

		public bool InGame { get; set; }

		public FanEnum FanValue {
			get {
				if (Fan == "0")
					return FanEnum.Blue;
				if (Fan == "1")
					return FanEnum.Red;
				return FanEnum.Unknown;
			}
			set {
				switch (value) {
				case FanEnum.Blue:
					Fan = "0";
					break;
				case FanEnum.Red:
					Fan = "1";
					break;
				case FanEnum.Unknown:
					Fan = null;
					break;
				}
			}
		}

		public string Fan { get; set; }

		public MeasurementTypeEnum MeasurementTypeEnum {
			get { 
				MeasurementTypeEnum type = MeasurementTypeEnum.Unknown;
				if (Enum.TryParse (MeasurementType, true, out type)) {
					return type;
				}
				return type;
			}
		}

		public string Display
		{
			get {
				if (InGame && FanValue != FanEnum.Unknown) {
					return string.Format ("{0} (Team:{1})", Name, FanValue.ToString());
				}

				return Name;
			}
		}

		public int Score { get; set; }

		public string Name { get; set; }

		public string HeadsetId  { get; set; }

		public string PlayerId { 
			get { return _playerId.Contains ('-') ? _playerId.Substring (_playerId.LastIndexOf ('-') + 1, _playerId.Length - _playerId.LastIndexOf ('-') - 1) : _playerId; }
		}
	}

}


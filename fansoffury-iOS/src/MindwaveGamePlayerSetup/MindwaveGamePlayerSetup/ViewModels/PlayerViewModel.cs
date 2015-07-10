using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace MindwaveGamePlayerSetup
{
	public class PlayerViewModel : ViewModelBase
	{
		private string _name;

		public string Name {
			get { return _name; }
			set {
				_name = value;
				OnPropertyChanged ();
				Console.WriteLine ("Test");
			}
		}
	}
}


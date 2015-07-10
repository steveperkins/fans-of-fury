using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Collections.ObjectModel;
using System.Windows.Input;
using Xamarin.Forms;

namespace MindwaveGamePlayerSetup
{
	public class PlayerListViewModel : ViewModelBase
	{
		private ObservableCollection<PlayerViewModel> _players;

		public PlayerListViewModel()
		{
			SelectPlayerCommand = new Command<PlayerViewModel> ((player) => {
				
			});
		}

		public ICommand SelectPlayerCommand { protected set; get; }

		public ObservableCollection<PlayerViewModel> Players {
			get { return _players; }
			set {
				_players = value;
				OnPropertyChanged ();
			}
		}

		public PlayerListViewModel()
		{
			Players = new ObservableCollection<PlayerViewModel> (new System.Collections.Generic.List<PlayerViewModel> () {
				new PlayerViewModel () { Name = "Test 1" },
				new PlayerViewModel () { Name = "Test 2" },
				new PlayerViewModel () { Name = "Test 3" }
			});
		}
	}
}


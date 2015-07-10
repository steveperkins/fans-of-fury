using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Collections.ObjectModel;
using XLabs.Forms.Mvvm;
using XLabs;
using System.Windows.Input;
using Xamarin.Forms;
using XLabs.Ioc;

namespace MindwaveGamePlayerSetup
{
	public class PlayerListViewModel : ViewModel
	{
		private ObservableCollection<PlayerViewModel> _players;
		private ICommand _newPlayerCommand;
		private IDataService _dataService;

		public PlayerListViewModel(IDataService dataService)
		{
			_dataService = dataService;
			Players = new ObservableCollection<PlayerViewModel> ();
			Players.Add (new PlayerViewModel (_dataService) { Name = "Test 1", Parent = Players });
			Players.Add (new PlayerViewModel (_dataService) { Name = "Test 2", Parent = Players });
			Players.Add (new PlayerViewModel (_dataService) { Name = "Test 3", Parent = Players });
		}

		public ObservableCollection<PlayerViewModel> Players {
			get { return _players; }
			set { SetProperty (ref _players, value); }
		}

		public ICommand NewPlayerCommand 
		{ 
			get 
			{
				return _newPlayerCommand ?? (_newPlayerCommand = new Command ((obj) => {
					var player = new PlayerViewModel(_dataService) { Name = "New Player" };
					Players.Add(player);
					NavigationService.NavigateTo<PlayerViewModel> (player);
				}));
			}
		}
	}
}


using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using XLabs.Forms.Mvvm;
using System.Windows.Input;
using Xamarin.Forms;
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Linq;
using System.Collections;

namespace MindwaveGamePlayerSetup
{
	public class PlayerViewModel : ViewModel
	{
		private string _name;
		private string _headsetId;

		private ICommand _selectPlayerCommand;
		private ICommand _removePlayerCommand;
		private ICommand _savePlayerCommand;

		public PlayerViewModel(IDataService dataService)
		{
			Headsets = dataService.GetHeadsets().Select(x => x.ToString()).ToList();
			GetText = (o) => {
				return o.ToString ();
			};
		}

		public string Name 
		{
			get { return _name; }
			set { SetProperty (ref _name, value); }
		}

		public string HeadsetId 
		{
			get { return _headsetId; }
			set { SetProperty (ref _headsetId, value); }
		}

		public Func<object,string> GetText { get; set; }

		public ObservableCollection<PlayerViewModel> Parent { get; set; }

		public IList Headsets { get; set; }

		public ICommand SelectPlayerCommand 
		{ 
			get 
			{
				return _selectPlayerCommand ?? (_selectPlayerCommand = new Command<PlayerViewModel> ((player) => {
					//Navigation.PushAsync<PlayerViewModel>((v,p) => p.BindingContext = v);
					NavigationService.NavigateTo<PlayerViewModel> (player);
				}));
			}
		}

		public ICommand RemovePlayerCommand 
		{ 
			get 
			{
				return _removePlayerCommand ?? (_removePlayerCommand = new Command<PlayerViewModel> ((player) => {
					Parent.Remove(player);
				}));
			}
		}

		public ICommand SavePlayerCommand 
		{ 
			get 
			{
				return _savePlayerCommand ?? (_savePlayerCommand = new Command (() => {
					NavigationService.GoBack();
				}));
			}
		}
	}
}


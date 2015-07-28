
using System;

using Foundation;
using UIKit;
using System.Collections.Generic;
using System.Linq;
using fansoffury.mobile.domain;
using fansoffury.mobile.services;
using System.Net.Http;
using System.Net.Http.Headers;

namespace fansoffury.mobile
{
	public partial class PlayerViewController : UITableViewController
	{
		private Player _player;
		private PlayerListViewController _playerListViewController;
		private bool _newPlayer;
		private IPlayerService _playerService;
		private IHeadsetService _headsetService;

		public PlayerViewController (IntPtr handle) : base (handle)
		{
			View.LayoutMargins = new UIEdgeInsets (20, 0, 0, 0);
			_playerService = new PlayerService ();
			_headsetService = new HeadsetService ();
			Headsets = _headsetService.GetHeadsets ();
		}

		public List<Headset> Headsets { get; set; }

		public void SetPlayer (PlayerListViewController playerListViewController, Player player)
		{
			_playerListViewController = playerListViewController;
			_player = player;
			if (_player == null) {
				PlayerIdCell.AddGestureRecognizer (new UITapGestureRecognizer (AddPlayerGestureHandler));

			} else {
				AddHeadsetGesture ();
			}
		}

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			AddToGameButton.Hidden = true;
			RemoveFromGameButton.Hidden = true;
			KeyboardUtil.RegisterKeyboardDismissalHandler (this.View);
			KeyboardUtil.RegisterKeyboardDoneHandler (this.PlayerName);
			AddToGameButton.TouchUpInside+= (sender, e) => {
				ChangeGameState(true);
			};
			RemoveFromGameButton.TouchUpInside+= (sender, e) =>  {
				ChangeGameState(false);
			};
			AddSaveButton ();
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			BindLabels ();
		}

		public void SelectHeadset (string headsetId)
		{
			if (_player != null) {
				_player.HeadsetId = headsetId;
				HeadsetId.Text = Headsets.Single (x => x.HeadsetId == _player.HeadsetId).HeadsetName;
			}
		}

		#region Private Methods

		private void SetPlayerInfo()
		{
			if (_player != null){
				_player.Name = PlayerName.Text;
				_player.MeasurementTypeValue = HeadsetType.SelectedSegment == 0 ? MeasurementTypeEnum.Attention : MeasurementTypeEnum.Meditation;

				switch (FanTeam.SelectedSegment) {
				case 0:
					_player.FanValue = FanEnum.Blue;
					break;
				case 1:
					_player.FanValue = FanEnum.Red;
					break;
				default:
					_player.FanValue = FanEnum.Unknown;
					break;
				}

				_player.InGame = InGameToggle.On;
			}
		}

		private void AddHeadsetGesture ()
		{
			HeadsetIdTableCell.Accessory = UITableViewCellAccessory.DisclosureIndicator;
			HeadsetIdTableCell.AddGestureRecognizer (new UITapGestureRecognizer (() => {
				SetPlayerInfo();
				var vc = Storyboard.InstantiateViewController ("HeadsetTableSelectionController") as HeadsetTableSelectionController;
				vc.SetDataSource (new HeaderTableViewSource (Headsets, _player.HeadsetId, this));
				NavigationController.PushViewController (vc, true);
			}));

		}
		private void AddSaveButton()
		{
			NavigationItem.SetRightBarButtonItem (new UIBarButtonItem ("Save", UIBarButtonItemStyle.Plain, (o, e) => {
				SavePlayer();
			}), true);
		}

		private async void AddPlayerGestureHandler ()
		{
			Console.WriteLine ("PlayerID Pushed");
			string id = "";
			if (UIImagePickerController.IsSourceTypeAvailable (UIImagePickerControllerSourceType.Camera)) {
				var scanner = new ZXing.Mobile.MobileBarcodeScanner (this);
				var result = await scanner.Scan ();
				if (result != null) {
					id = result.Text.Substring (result.Text.LastIndexOf ('/') + 1, result.Text.Length - result.Text.LastIndexOf ('/') - 1);
				}
			} else {
				id = "TEST";
			}
			Console.WriteLine ("PlayerID: " + id);
			_newPlayer = true;
			try {
				_player = _playerService.GetPlayer (id);
			} catch (Exception e) {
				var alert = new UIAlertView ("Error", "There was an error retrieving the player\n" + e.Message, null, "Ok", null);
				alert.Show ();
			}
			BindLabels ();
			AddHeadsetGesture ();
		}

		private void BindLabels ()
		{
			if (_player != null) {
				PlayerId.Text = _player.PlayerId;
				PlayerName.Text = _player.Name;
				if (Headsets.Any (x => x.HeadsetId == _player.HeadsetId)) {
					HeadsetId.Text = Headsets.Single (x => x.HeadsetId == _player.HeadsetId).HeadsetName;
				}
				HeadsetType.SelectedSegment = _player.MeasurementTypeEnum == MeasurementTypeEnum.Meditation ? 1 : 0;
				FanTeam.SelectedSegment = (int)_player.FanValue;
				InGameToggle.On = _player.InGame;
				ChangeGameState (_player.InGame);
			}
		}

		private void SavePlayer()
		{
			string error = null;
			if (_player == null) {
				error = "Invalid Player";
			} 

			if (string.IsNullOrEmpty (_player.PlayerId)) {
				error = "Invalid PlayerID";
			} else if (string.IsNullOrEmpty (_player.Name)) {
				error = "Player Name is required";
			}
			if (!string.IsNullOrEmpty (error)) {
				var alert = new UIAlertView ("Error", "Invalid Player", null, "Ok", null);
				alert.Show ();
			} else {
				SetPlayerInfo ();
				if (_newPlayer && _player != null) {
					_playerListViewController.AddPlayer (_player);
				}
				if (_player != null && _player.InGame && _player.FanValue != FanEnum.Unknown && !string.IsNullOrEmpty(_player.HeadsetId)) {
					var response = _playerService.AssignPlayer (new JsonHeadset(_player));
					Console.WriteLine (response);
				}
				Console.WriteLine ("Save Player");
				NavigationController.PopViewController (true);
			}
		}

		private void ChangeGameState(bool inGame)
		{
			AddToGameButton.Hidden = inGame;
			RemoveFromGameButton.Hidden = !inGame;
			_player.InGame = inGame;
		}

		private void ChangeFanTeam(FanEnum fan)
		{
			Console.WriteLine(fan);
		}

		#endregion

	}
}



using System;

using Foundation;
using UIKit;
using System.Collections.Generic;
using System.Linq;

namespace fansoffury.mobile
{
	public partial class PlayerViewController : UITableViewController
	{
		private Player _player;
		private PlayerListViewController _playerListViewController;
		private bool _newPlayer;

		public PlayerViewController (IntPtr handle) : base (handle)
		{
			View.LayoutMargins = new UIEdgeInsets (20, 0, 0, 0);
			Headsets = new List<Headset> () {
				new Headset () { HeadsetId = 1, HeadsetName = "Headset 1" },
				new Headset () { HeadsetId = 2, HeadsetName = "Headset 2" }
			};
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
			KeyboardUtil.RegisterKeyboardDismissalHandler (this.View);
			KeyboardUtil.RegisterKeyboardDoneHandler (this.PlayerName);
			AddSaveButton ();
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			BindLabels ();
		}

		public void SelectHeadset (int headsetId)
		{
			if (_player != null) {
				_player.HeadsetId = headsetId;
				HeadsetId.Text = Headsets.Single (x => x.HeadsetId == _player.HeadsetId).HeadsetName;
			}
		}

		#region Private Methods

		private void SetPlayerName()
		{
			if (_player != null){
				_player.Name = PlayerName.Text;
			}
		}

		private void AddHeadsetGesture ()
		{
			HeadsetIdTableCell.Accessory = UITableViewCellAccessory.DisclosureIndicator;
			HeadsetIdTableCell.AddGestureRecognizer (new UITapGestureRecognizer (() => {
				SetPlayerName();
				var vc = Storyboard.InstantiateViewController ("HeadsetTableSelectionController") as HeadsetTableSelectionController;
				vc.SetDataSource (new HeaderTableViewSource (Headsets, _player.HeadsetId, this), 1);
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
			_player = new Player (id);
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
			} else {

				if (_newPlayer && _player != null) {
					_playerListViewController.AddPlayer (_player);
				}
				Console.WriteLine ("Save Player");
				NavigationController.PopViewController (true);
			}
		}


		#endregion
	}
}


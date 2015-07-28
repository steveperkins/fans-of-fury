using System;
using System.Collections.Generic;
using fansoffury.mobile.domain;
using fansoffury.mobile.services;
using UIKit;

namespace fansoffury.mobile
{
	public partial class PlayerListViewController : UIViewController
	{
		UITableView _playerTableView;
		List<Player> _players;

		public PlayerListViewController (IntPtr handle) : base (handle)
		{
			View.LayoutMargins = new UIEdgeInsets (20, 0, 0, 0);
		}

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();

			NavigationItem.SetRightBarButtonItem (new UIBarButtonItem ("New Player", UIBarButtonItemStyle.Plain, (o, e) => {
				var p = Storyboard.InstantiateViewController ("PlayerViewController") as PlayerViewController;
				p.SetPlayer(this, null);
				NavigationController.PushViewController (p, true);
			}), true);

			_playerTableView = new UITableView (View.Bounds);

			if (_players == null) {
				_players = new List<Player> ();
			}
			Add (_playerTableView);
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			_playerTableView.Source = new PlayerTableViewSource (_players, this);
		}

		public void AddPlayer(Player player)
		{
			_players.Add (player);
		}

		public void DeletePlayer(Player player)
		{
			_players.Remove (player);
		}
	}
}


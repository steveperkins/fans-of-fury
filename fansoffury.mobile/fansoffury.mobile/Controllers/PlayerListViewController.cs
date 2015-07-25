using System;
using UIKit;
using System.Collections.Generic;
using fansoffury.mobile.domain;
using fansoffury.mobile.services;

namespace fansoffury.mobile
{
	public partial class PlayerListViewController : UIViewController
	{
		UITableView playerTableView;
		List<Player> _players;

		[Ninject.Inject()]
		public IPlayerService PlayerService {get;set;}

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

			playerTableView = new UITableView (View.Bounds);

			if (_players == null) {
				_players = new List<Player> ();
//				_players.Add (new Player ("1") { Name = "Sample 1", HeadsetId = 1 });
//				_players.Add (new Player ("2") { Name = "Sample 2", HeadsetId = 2 });
			}
			Add (playerTableView);
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			playerTableView.Source = new PlayerTableViewSource (_players, this);
		}

		public override void DidReceiveMemoryWarning ()
		{
			base.DidReceiveMemoryWarning ();
			// Release any cached data, images, etc that aren't in use.
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


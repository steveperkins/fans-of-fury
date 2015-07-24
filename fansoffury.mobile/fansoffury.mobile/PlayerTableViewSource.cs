using System;
using UIKit;
using System.Collections.Generic;
using System.Linq;

namespace fansoffury.mobile
{
	public class PlayerTableViewSource : UITableViewSource 
	{
		const string CellIdentifier = "PlayerTableCell";

    	PlayerListViewController _controller;

		public PlayerTableViewSource (IEnumerable<Player> items, PlayerListViewController controller)
		{
			Players = items.ToList();
			_controller = controller;
		}

		public List<Player> Players { get; set; }

		public override nint RowsInSection (UITableView tableview, nint section)
		{
			return Players.Count;
		}

		public override UITableViewCell GetCell (UITableView tableView, Foundation.NSIndexPath indexPath)
		{
			UITableViewCell cell = tableView.DequeueReusableCell (CellIdentifier);
			Player player = Players [indexPath.Row];

			if (cell == null) {
				cell = new UITableViewCell (UITableViewCellStyle.Value1, CellIdentifier);
			}

			cell.TextLabel.Text = player.Name;
			cell.DetailTextLabel.Text = "(Headset: " + player.HeadsetId + ")";

			return cell;
		}

		public override void CommitEditingStyle (UITableView tableView, UITableViewCellEditingStyle editingStyle, Foundation.NSIndexPath indexPath)
		{
			switch (editingStyle) {
			case UITableViewCellEditingStyle.Delete:
				var player = Players [indexPath.Row];
				_controller.DeletePlayer (player);
				Players.RemoveAt (indexPath.Row);
				tableView.DeleteRows (new Foundation.NSIndexPath[] { indexPath }, UITableViewRowAnimation.Left);
				break;
			}
		}

		public override void RowSelected (UITableView tableView, Foundation.NSIndexPath indexPath)
		{
			var p = _controller.Storyboard.InstantiateViewController ("PlayerViewController") as PlayerViewController;
			p.SetPlayer (_controller, Players [indexPath.Row]);
			_controller.NavigationController.PushViewController (p, true);
		}
	}
}


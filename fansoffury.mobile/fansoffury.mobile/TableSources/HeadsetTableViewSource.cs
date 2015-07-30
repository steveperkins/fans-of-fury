using System;
using UIKit;
using System.Collections.Generic;
using System.Linq;
using fansoffury.mobile.domain;

namespace fansoffury.mobile
{
	public class HeaderTableViewSource : UITableViewSource 
	{
		const string CellIdentifier = "HeadsetTableCell";

		PlayerViewController _controller;
		private string _selectedHeadsetId;

		public HeaderTableViewSource (IEnumerable<Headset> items, string selectedHeadset, PlayerViewController controller)
		{
			Headsets = items.ToList();
			_selectedHeadsetId = selectedHeadset;
			_controller = controller;
		}

		public IList<Headset> Headsets { get; set; }

		public override nint RowsInSection (UITableView tableview, nint section)
		{
			return Headsets.Count;
		}

		public override nint NumberOfSections (UITableView tableView)
		{
			return 1;
		}

		public override UITableViewCell GetCell (UITableView tableView, Foundation.NSIndexPath indexPath)
		{
			UITableViewCell cell = tableView.DequeueReusableCell (CellIdentifier);
			Headset headset = Headsets [indexPath.Row];

			if (cell == null) {
				cell = new UITableViewCell (UITableViewCellStyle.Default, CellIdentifier);
			}

			cell.TextLabel.Text = headset.HeadsetName;
			if (headset.HeadsetId == _selectedHeadsetId) {
				cell.Accessory = UITableViewCellAccessory.Checkmark;
			}
			return cell;
		}

		public override void RowSelected (UITableView tableView, Foundation.NSIndexPath indexPath)
		{
			_controller.SelectHeadset (Headsets [indexPath.Row].HeadsetId);
			for (int i = 0; i < Headsets.Count; i++) {
				var ip = Foundation.NSIndexPath.FromRowSection(i, 0);
				var cell = tableView.CellAt (ip);
				if (i == indexPath.Row) {
					cell.Accessory = UITableViewCellAccessory.Checkmark;
				} else {
					cell.Accessory = UITableViewCellAccessory.None;
				}
			}
		}
	}
}


using System;
using Foundation;
using UIKit;
using System.Collections.Generic;

namespace fansoffury.mobile
{
	public partial class HeadsetTableSelectionController : UITableViewController
	{
		public HeadsetTableSelectionController (IntPtr handle) : base (handle)
		{
			View.LayoutMargins = new UIEdgeInsets (20, 0, 0, 0);
		}

		public void SetDataSource(HeaderTableViewSource dataSource, int selectedHeadsetId)
		{
			TableView.Source = dataSource;
			foreach (var item in dataSource.Headsets) {
				
			}
		}

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			NavigationItem.SetRightBarButtonItem (new UIBarButtonItem ("Save", UIBarButtonItemStyle.Plain, (o, e) => {
				NavigationController.PopViewController(true);
			}), true);

		}

	}
}

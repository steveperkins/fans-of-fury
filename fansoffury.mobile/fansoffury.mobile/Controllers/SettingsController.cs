
using System;

using Foundation;
using UIKit;

namespace fansoffury.mobile
{
	public partial class SettingsController : UITableViewController
	{
		public SettingsController (IntPtr handle) : base (handle)
		{
			
		}

		public override void DidReceiveMemoryWarning ()
		{
			// Releases the view if it doesn't have a superview.
			base.DidReceiveMemoryWarning ();

			// Release any cached data, images, etc that aren't in use.
		}

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			Console.WriteLine ("Here");
			NavigationItem.SetRightBarButtonItem (new UIBarButtonItem ("Save", UIBarButtonItemStyle.Plain, (o, e) => {
				AppSettings.BaseURL = BaseURLField.Text;
				var alert = new UIAlertView("Saved", "Settings saved", null, "Ok", null);
				alert.Show();
			}), true);
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			BaseURLField.Text = AppSettings.BaseURL;
		}
	}
}


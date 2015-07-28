
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

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			KeyboardUtil.RegisterKeyboardDismissalHandler (this.View);
			KeyboardUtil.RegisterKeyboardDoneHandler (this.BaseURLField);
			NavigationItem.SetRightBarButtonItem (new UIBarButtonItem ("Save", UIBarButtonItemStyle.Plain, (o, e) => {
				SetModelData();
				var alert = new UIAlertView("Saved", "Settings saved", null, "Ok", null);
				alert.Show();
			}), true);
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			SetFormData ();
		}

		private void SetFormData()
		{
			BaseURLField.Text = AppSettings.BaseURL;
		}

		private void SetModelData()
		{
			AppSettings.BaseURL = BaseURLField.Text;
		}
	}
}


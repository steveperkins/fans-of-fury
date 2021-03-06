// WARNING
//
// This file has been generated automatically by Xamarin Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace fansoffury.mobile
{
	[Register ("PlayerViewController")]
	partial class PlayerViewController
	{
		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UISegmentedControl FanTeam { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UITextField HeadsetId { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UITableViewCell HeadsetIdTableCell { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UISegmentedControl HeadsetType { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UISwitch InGameToggle { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UITextField PlayerId { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UITableViewCell PlayerIdCell { get; set; }

		[Outlet]
		[GeneratedCode ("iOS Designer", "1.0")]
		UITextField PlayerName { get; set; }

		void ReleaseDesignerOutlets ()
		{
			if (FanTeam != null) {
				FanTeam.Dispose ();
				FanTeam = null;
			}
			if (HeadsetId != null) {
				HeadsetId.Dispose ();
				HeadsetId = null;
			}
			if (HeadsetIdTableCell != null) {
				HeadsetIdTableCell.Dispose ();
				HeadsetIdTableCell = null;
			}
			if (HeadsetType != null) {
				HeadsetType.Dispose ();
				HeadsetType = null;
			}
			if (InGameToggle != null) {
				InGameToggle.Dispose ();
				InGameToggle = null;
			}
			if (PlayerId != null) {
				PlayerId.Dispose ();
				PlayerId = null;
			}
			if (PlayerIdCell != null) {
				PlayerIdCell.Dispose ();
				PlayerIdCell = null;
			}
			if (PlayerName != null) {
				PlayerName.Dispose ();
				PlayerName = null;
			}
		}
	}
}

using System;
using UIKit;

namespace fansoffury.mobile
{
	public static class KeyboardUtil
	{
		/**
		 * Locates all textfields in the given view and registers a handler to dismiss the keyboard when the user clicks 'Done'
		 */
		public static void RegisterKeyboardDismissalHandler(UIView view) {
			UITapGestureRecognizer gesture = new UITapGestureRecognizer(() => view.EndEditing(true));
			gesture.CancelsTouchesInView = false; //for iOS5. Otherwise events will not be fired on other controls.
			view.AddGestureRecognizer(gesture);
			RegisterKeyboardDoneHandlers (view);
		}

		public static void RegisterKeyboardDoneHandler(UITextField text) {
			text.ShouldReturn += (t) => {
				t.ResignFirstResponder ();
				return true;
			};
		}

		private static void RegisterKeyboardDoneHandlers(UIView view) {
			foreach(UIView subview in view.Subviews) {
				RegisterKeyboardDoneHandlers (subview);
				UITextField textField = view as UITextField;
				if (null != textField) {
					textField.ShouldReturn += DismissOnDoneKey;
				}
			}
		}

		private static Boolean DismissOnDoneKey(UITextField textField) {
			// iOS: Putting the 'ss' in 'mess'
			textField.ResignFirstResponder ();
			// According to the interwebs, returning 'true' causes a line break to be entered in the current text field. Returning 'false' indicates a line break should not be entered.
			return false;
		}
	}
}


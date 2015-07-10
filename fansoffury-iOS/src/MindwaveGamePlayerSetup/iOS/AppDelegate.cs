using System;
using System.Collections.Generic;
using System.Linq;

using Foundation;
using UIKit;
using Ninject;
using XLabs.Ioc;
using XLabs.Forms;
using XLabs.Platform.Device;
using Xamarin.Forms;
using NinjectIoC;

namespace MindwaveGamePlayerSetup.iOS
{
	[Register ("AppDelegate")]
	public partial class AppDelegate : XFormsApplicationDelegate
	{
		public override bool FinishedLaunching (UIApplication app, NSDictionary options)
		{
			global::Xamarin.Forms.Forms.Init ();

			// Code for starting up the Xamarin Test Cloud Agent
			#if ENABLE_TEST_CLOUD
			Xamarin.Calabash.Start();
			#endif

			SetupDependencyResolver ();
			Forms.Init ();
			LoadApplication (new App ());

			return base.FinishedLaunching (app, options);
		}

		private void SetupDependencyResolver()
		{
			var kernel = new Ninject.StandardKernel ();
			var container = new NinjectContainerNew (kernel);

			kernel.Bind<IDevice> ().ToConstant (AppleDevice.CurrentDevice);
			kernel.Bind<IDependencyContainer> ().ToConstant (container);
			var app = new XFormsAppiOS ();
			app.Init (this);

			Resolver.SetResolver (container.GetResolver());
		}
	}
}


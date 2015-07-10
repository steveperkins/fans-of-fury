using System;

using Xamarin.Forms;
using XLabs.Forms.Mvvm;
using XLabs.Ioc;
using XLabs.Platform.Services;
using XLabs.Forms.Services;

namespace MindwaveGamePlayerSetup
{
	public class App : Application
	{
		public App ()
		{
			MainPage = GetMainPage ();
		}

		public Page GetMainPage()
		{
			var container = Resolver.Resolve<IDependencyContainer> ();
			container.Register<IDataService, DataService> ();
			RegisterViews ();
		
			var mainPage = new NavigationPage ((Page)ViewFactory.CreatePage<PlayerListViewModel, PlayerListPage> ());
			container.Register<INavigationService> (t => new MindwaveNavigationService (mainPage.Navigation));
			return mainPage;
		}

		private void RegisterViews()
		{
			ViewFactory.Register<PlayerListPage, PlayerListViewModel> ();
			ViewFactory.Register<PlayerEditPage, PlayerViewModel> ();
		}

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}


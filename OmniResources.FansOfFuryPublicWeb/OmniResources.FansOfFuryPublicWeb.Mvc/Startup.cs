using Microsoft.Owin;
using OmniResources.FansOfFuryPublicWeb.Mvc;
using Owin;

[assembly: OwinStartup(typeof(Startup))]
namespace OmniResources.FansOfFuryPublicWeb.Mvc
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}

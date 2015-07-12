using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(OmniResources.FansOfFuryPublicWeb.Mvc.Startup))]
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

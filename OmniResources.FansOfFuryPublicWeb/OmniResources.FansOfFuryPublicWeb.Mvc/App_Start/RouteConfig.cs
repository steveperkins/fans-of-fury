using System.Web.Mvc;
using System.Web.Routing;

namespace OmniResources.FansOfFuryPublicWeb.Mvc
{
    public static class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute("QrCode", "qrcode/{id}", new {controller = "QrCode", action = "Index"});
            routes.MapRoute("Default", "{controller}/{action}/{id}", new { controller = "Home", action = "Index", id = UrlParameter.Optional });
        }
    }
}

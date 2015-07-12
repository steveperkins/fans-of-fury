using System.Web;
using System.Web.Mvc;

namespace OmniResources.FansOfFuryPublicWeb.Mvc
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
    }
}

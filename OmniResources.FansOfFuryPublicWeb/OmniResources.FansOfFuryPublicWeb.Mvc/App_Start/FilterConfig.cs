using System.Web.Mvc;

namespace OmniResources.FansOfFuryPublicWeb.Mvc
{
    public static class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
    }
}

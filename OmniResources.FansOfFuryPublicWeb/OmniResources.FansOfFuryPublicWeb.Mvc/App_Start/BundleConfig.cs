using System.Web.Optimization;

namespace OmniResources.FansOfFuryPublicWeb.Mvc
{
    public static class BundleConfig
    {
        // For more information on bundling, visit http://go.microsoft.com/fwlink/?LinkId=301862
        public static void RegisterBundles(BundleCollection bundles)
        {
            bundles.Add(new ScriptBundle("~/bundles/libs").Include(
                "~/Scripts/jquery-{version}.js",
                "~/Scripts/jquery.validate*",
                "~/Scripts/mvcfoolproof.unobtrusive.min.js",
                "~/Scripts/bootstrap.js",
                "~/Scripts/respond.js"));

            bundles.Add(new ScriptBundle("~/bundles/app").Include(
                "~/Scripts/common.js"));

            bundles.Add(new StyleBundle("~/Content/css").Include(
                "~/Content/bootstrap.css",
                "~/Content/site.css"));
        }
    }
}

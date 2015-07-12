using System.Web;
using System.Web.Mvc;
using Microsoft.AspNet.Identity.Owin;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class BaseAuthenticatedController : Controller
    {
        protected ApplicationSignInManager SignInManager
        {
            get
            {
                return HttpContext.GetOwinContext().Get<ApplicationSignInManager>();
            }
        }

        protected ApplicationUserManager UserManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
        }
    }
}
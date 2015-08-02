using System.Web;
using System.Web.Mvc;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class BaseController : Controller
    {
        private const string UserIdCookie = "FansOfFuryUserId";

        public string UserId
        {
            get
            {
                var cookie = Request.Cookies[UserIdCookie];
                return cookie == null ? null : cookie.Value;
            }

            set
            {
                Response.Cookies.Add(new HttpCookie(UserIdCookie, value));
            }
        }
    }
}
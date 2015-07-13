using System;
using System.Text;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class BaseController : Controller
    {
        private const string AuthCookie = "FansOfFuryAuth";
        private string _authenticatedUserId;

        public string AuthenticatedUserId
        {
            get
            {
                if (_authenticatedUserId != null)
                    return _authenticatedUserId;

                var cookie = Request.Cookies[AuthCookie];

                if (cookie == null || string.IsNullOrWhiteSpace(cookie.Value))
                    return null;

                try
                {
                    var cypherBytes = Convert.FromBase64String(cookie.Value);
                    var clearBytes = MachineKey.Unprotect(cypherBytes);
                    
                    if (clearBytes != null)
                        _authenticatedUserId = Encoding.UTF8.GetString(clearBytes);

                    return _authenticatedUserId;
                }
                catch
                {
                    return null;
                }
            }

            set
            {
                var clearBytes = Encoding.UTF8.GetBytes(value);
                var cypherBytes = MachineKey.Protect(clearBytes);

                Response.Cookies.Add(new HttpCookie(AuthCookie)
                {
                    Path = "/",
                    Value = Convert.ToBase64String(cypherBytes)
                });

                _authenticatedUserId = value;
            }
        }
    }
}
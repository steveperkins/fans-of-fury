using System;
using System.Text;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using Newtonsoft.Json;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class BaseController : Controller
    {
        private const string AuthCookie = "FansOfFuryAuth";

        public string UserId
        {
            get { return UserState.UserId; }
            set { UserState.UserId = value; WriteUserStateCookie(UserState);  }
        }

        public bool Authenticated
        {
            get { return UserState.Authenticated; }
            set { UserState.Authenticated = value; WriteUserStateCookie(UserState); }
        }

        private UserStateData UserState
        {
            get
            {
                if (HttpContext.Items.Contains("UserState"))
                    return (UserStateData)HttpContext.Items["UserState"];

                var state = new UserStateData();
                var cookie = Request.Cookies[AuthCookie];

                if (cookie != null && !string.IsNullOrWhiteSpace(cookie.Value))
                {
                    try
                    {
                        var cypherBytes = Convert.FromBase64String(cookie.Value);
                        var clearBytes = MachineKey.Unprotect(cypherBytes);

                        if (clearBytes != null)
                            state = JsonConvert.DeserializeObject<UserStateData>(Encoding.UTF8.GetString(clearBytes));
                    }
                    catch
                    {
                        // On error, start with blank state
                    }
                }

                HttpContext.Items["UserState"] = state;

                return state;
            }
        }

        private void WriteUserStateCookie(UserStateData userState)
        {
            var clearText = JsonConvert.SerializeObject(userState);
            var clearBytes = Encoding.UTF8.GetBytes(clearText);
            var cypherBytes = MachineKey.Protect(clearBytes);

            Response.Cookies.Add(new HttpCookie(AuthCookie)
            {
                Path = "/",
                Value = Convert.ToBase64String(cypherBytes)
            });

            HttpContext.Items["UserState"] = userState;
        }

        private class UserStateData
        {
            public string UserId { get; set; }

            public bool Authenticated { get; set; }
        }
    }
}
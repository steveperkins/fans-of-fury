using System.Threading.Tasks;
using System.Web.Mvc;
using System.Web.Routing;
using Microsoft.AspNet.Identity;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class QrCodeController : BaseAuthenticatedController
    {
        private readonly IUserDataRepository _userDataRepo;

        public QrCodeController(IUserDataRepository userDataRepo)
        {
            _userDataRepo = userDataRepo;
        }

        public async Task<ActionResult> Index(string id)
        {
            var authenticatedUserId = User.Identity.GetUserId();

            // If the user is already authenticated, just take them to manage their account
            if (!string.IsNullOrEmpty(authenticatedUserId))
                return RedirectToAction("Index", "Manage");

            // If there's no id passed in, there's nothing we can do...
            if (string.IsNullOrEmpty(id))
                return RedirectToAction("Index", "Home");

            var user = await _userDataRepo.Get(id);

            // If there's no user yet for this ID, take them to registration
            if (user == null)
                return RedirectToAction("Register", "Account", new RouteValueDictionary {{"id", id}});

            // If there is a user for the ID, take them to login
            return RedirectToAction("Login", "Account");
        }
    }
}
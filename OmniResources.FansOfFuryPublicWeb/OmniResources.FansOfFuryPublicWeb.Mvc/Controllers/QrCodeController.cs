using System.Web.Mvc;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class QrCodeController : Controller
    {
        private readonly IUserDataRepository _userDataRepo;

        public QrCodeController(IUserDataRepository userDataRepo)
        {
            _userDataRepo = userDataRepo;
        }

        public ActionResult Index(string id)
        {
            return View();
        }
    }
}
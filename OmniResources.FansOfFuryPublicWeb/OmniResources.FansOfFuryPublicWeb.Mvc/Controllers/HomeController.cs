using System.Linq;
using System.Threading.Tasks;
using System.Web.Mvc;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class HomeController : Controller
    {
        private readonly IUserDataRepository _userDataRepo;

        public HomeController(IUserDataRepository userDataRepo)
        {
            _userDataRepo = userDataRepo;
        }

        public async Task<ActionResult> Index()
        {
            return View((await _userDataRepo.Get()).OrderByDescending(x => x.Score).ToList());
        }
    }
}
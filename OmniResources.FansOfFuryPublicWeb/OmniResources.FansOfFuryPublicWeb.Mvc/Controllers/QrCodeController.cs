using System.Threading.Tasks;
using System.Web.Mvc;
using AutoMapper;
using OmniResources.FansOfFuryPublicWeb.Data.Table;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;
using OmniResources.FansOfFuryPublicWeb.Mvc.Models.QrCode;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class QrCodeController : BaseController
    {
        private readonly IUserDataRepository _userDataRepo;
        private readonly ISurveyResponseRepository _surveyResponseRepo;

        public QrCodeController(IUserDataRepository userDataRepo, ISurveyResponseRepository surveyResponseRepo)
        {
            _userDataRepo = userDataRepo;
            _surveyResponseRepo = surveyResponseRepo;
        }

        [HttpGet]
        public async Task<ActionResult> Index(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
                return RedirectToAction("Index", "Home");

            UserId = id;

            var existingUser = await _userDataRepo.Get(id);

            var model = new SurveyResponseViewModel();

            if (existingUser != null)
                model.ScoreboardName = existingUser.ScoreboardName;

            return View(model);
        }

        [HttpPost]
        public async Task<ActionResult> Index(string id, SurveyResponseViewModel model)
        {
            if (!ModelState.IsValid)
                return View(model);

            var existingUser = await _userDataRepo.Get(id);

            if (!string.IsNullOrEmpty(model.ScoreboardName))
            {
                existingUser = existingUser ?? new UserData();

                if (!string.IsNullOrWhiteSpace(existingUser.HashedPassword) && !BCrypt.Net.BCrypt.Verify(model.Password, existingUser.HashedPassword))
                {
                    ModelState.AddModelError("Password", "A username has already been set for this user, and the password you entered does not match.  Please try again.");
                    return View(model);
                }

                existingUser.ScoreboardName = model.ScoreboardName;
                existingUser.UserId = id;
                existingUser.HashedPassword = BCrypt.Net.BCrypt.HashPassword(model.Password);

                await _userDataRepo.Save(existingUser);
            }

            var surveyResponse = Mapper.Map<SurveyResponse>(model);
            surveyResponse.UserId = id;
            await _surveyResponseRepo.Save(surveyResponse);

            ViewData["Success"] = true;

            return View(model);
        }
    }
}
using System.Linq;
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

            if (!Authenticated)
                UserId = id;

            var existingUser = await _userDataRepo.Get(id);

            var model = new SurveyResponseViewModel
            {
                State = CalculateUserState(existingUser)
            };

            if (model.State == SurveyResponseViewModel.UserState.Authenticated)
            {
                var currentSurvey = await _surveyResponseRepo.GetCurrent(id);
                Mapper.Map(existingUser, model);
                Mapper.Map(currentSurvey, model);
            }

            return View(model);
        }

        [HttpPost]
        public async Task<ActionResult> Index(string id, SurveyResponseViewModel model)
        {
            var existingUser = await _userDataRepo.Get(id);
            model.State = CalculateUserState(existingUser);

            if (!ModelState.IsValid)
            {
                if (model.State != SurveyResponseViewModel.UserState.Authenticated || !ModelState.All(x => x.Value.Errors.Count == 0 || x.Key.Contains("Password")))
                    return View(model);

                ModelState.Clear();
            }   

            if (model.State != SurveyResponseViewModel.UserState.UserDataExists && !string.IsNullOrEmpty(model.ScoreboardName))
            {
                existingUser = existingUser ?? new UserData();
                existingUser.ScoreboardName = model.ScoreboardName;
                existingUser.UserId = id;

                if (model.State == SurveyResponseViewModel.UserState.New && !string.IsNullOrEmpty(model.Password))
                {
                    existingUser.HashedPassword = BCrypt.Net.BCrypt.HashPassword(model.Password);
                    Authenticated = true;
                    model.State = SurveyResponseViewModel.UserState.Authenticated;
                }

                await _userDataRepo.Save(existingUser);
            }

            var surveyResponse = Mapper.Map<SurveyResponse>(model);
            surveyResponse.UserId = id;
            surveyResponse.Authenticated = Authenticated;
            await _surveyResponseRepo.Save(surveyResponse);

            model.SuccessfulPost = true;

            return View(model);
        }

        private SurveyResponseViewModel.UserState CalculateUserState(UserData existingUser)
        {
            if (existingUser != null)
            {
                if (UserId == existingUser.UserId && Authenticated)
                    return SurveyResponseViewModel.UserState.Authenticated;

                return SurveyResponseViewModel.UserState.UserDataExists;
            }

            return SurveyResponseViewModel.UserState.New;
        }
    }
}